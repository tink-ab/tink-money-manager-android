package com.tink.moneymanagerui.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.tink.annotations.PfmScope
import com.tink.model.misc.Amount
import com.tink.model.misc.ExactNumber
import com.tink.model.statistics.Statistics
import com.tink.model.time.MonthPeriod
import com.tink.model.time.Period
import com.tink.model.user.UserProfile
import com.tink.moneymanagerui.extensions.toPeriodIdentifier
import com.tink.service.statistics.StatisticsQueryDescriptor
import com.tink.service.statistics.StatisticsService
import kotlinx.coroutines.*
import org.joda.time.DateTime
import org.threeten.bp.Instant
import se.tink.android.livedata.map
import se.tink.android.repository.service.DataRefreshHandler
import se.tink.android.repository.service.Refreshable
import se.tink.android.repository.transaction.TransactionUpdateEventBus
import se.tink.android.repository.user.UserRepository
import se.tink.commons.extensions.isInPeriod
import se.tink.commons.extensions.toDateTime
import timber.log.Timber
import javax.inject.Inject

private const val STATISTICS_FETCH_TIMEOUT = 15_000L
private const val POLLING_INTERVAL = 2_000L

@ExperimentalCoroutinesApi
@PfmScope
internal class StatisticsRepository @Inject constructor(
    private val statisticsService: StatisticsService,
    dataRefreshHandler: DataRefreshHandler,
    private val userRepository: UserRepository,
    transactionUpdateEventBus: TransactionUpdateEventBus
) : Refreshable {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        dataRefreshHandler.registerRefreshable(this)
        transactionUpdateEventBus.subscribe { refresh() }
    }

    private val refreshTrigger = MutableLiveData<Unit>()

    // TODO: Don't expose LiveData directly from a repository. They belong in ViewModels.
    private val statisticsLiveData = MediatorLiveData<List<Statistics>>().apply {
        fun update() {
            scope.launch {
                try {
                    withTimeout(STATISTICS_FETCH_TIMEOUT) {
                        while (isActive) {
                            val userProfile = userRepository.userProfile.value
                            if (userProfile != null) {
                                val result = queryForStatistics(userProfile)
                                if (result != null) {
                                    postValue(result)
                                    scope.cancel()
                                }
                            }
                            delay(POLLING_INTERVAL)
                        }
                    }
                } catch (timeoutException: TimeoutCancellationException) {
                    Timber.e(timeoutException)
                    postValue(
                        userRepository.userProfile.value
                            ?.let {
                                emptyList<Statistics>().handleMonthsMissingData(it.currency)
                            }
                            ?: emptyList()
                    )
                }
            }
        }
        addSource(userRepository.userProfile) { update() }
        addSource(refreshTrigger) { update() }
    }

    private suspend fun queryForStatistics(userProfile: UserProfile): List<Statistics>? {
        return try {
            statisticsService.query(
                StatisticsQueryDescriptor(
                    userProfile.periodMode,
                    userProfile.currency
                )
            ).handleMonthsMissingData(userProfile.currency)
        } catch (error: Throwable) {
            Timber.e(error)
            null
        }
    }

    private fun List<Statistics>.handleMonthsMissingData(currencyCode: String): List<Statistics> {

        val backfillStartTime = minByOrNull { it.period.start }
            ?.period
            ?.start
            ?.toDateTime() ?: DateTime.now()

        val existingPeriodIdentifiers = distinctBy { it.period.identifier }.map { it.period.identifier }
        val monthsMissingTransactions = generateSequence(backfillStartTime) { date ->
            date.plusMonths(1)
        }.takeWhile { iteratedDate ->
            iteratedDate.isBeforeNow
        }.filter { iteratedDate ->
            !existingPeriodIdentifiers.contains(iteratedDate.toPeriodIdentifier())
        }.map { iteratedDate ->
            getZeroDataStatisticsForType(currencyCode, iteratedDate)
        }
        return this.plus(monthsMissingTransactions)
    }

    private fun getZeroDataStatisticsForType(currencyCode: String, dateTime: DateTime): Statistics {
        val startOfCurrentMonth = dateTime.dayOfMonth().withMinimumValue()
        val endOfCurrentMonth = dateTime.dayOfMonth().withMaximumValue()
        val period =
            MonthPeriod(
                monthOfYear = dateTime.monthOfYear,
                year = dateTime.year,
                identifier = dateTime.toPeriodIdentifier(),
                start = Instant.ofEpochMilli(startOfCurrentMonth.millis),
                end = Instant.ofEpochMilli(endOfCurrentMonth.millis)
            )
        return Statistics(
            identifier = period.toString(),
            type = Statistics.Type.EXPENSES_BY_CATEGORY,
            period = period,
            value = Amount(ExactNumber(0), currencyCode)
        )
    }

    val statistics: LiveData<List<Statistics>> = statisticsLiveData

    private val periodMap: LiveData<Map<String, Period>> = statistics.map { statistics ->
        statistics.associate { it.period.identifier to it.period  }
    }

    val periods: LiveData<List<Period>> = Transformations.map(periodMap) {
        it?.values?.toList()
    }

    val currentPeriod: LiveData<Period> = Transformations.map(periodMap) {
        DateTime().let { now ->
            it?.values?.firstOrNull { it.isInPeriod(now) }
        }
    }

    override fun refresh() {
        refreshTrigger.postValue(Unit)
    }
}
