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
import com.tink.moneymanagerui.extensions.getEndOfMonth
import com.tink.moneymanagerui.extensions.getStartOfMonth
import com.tink.moneymanagerui.extensions.toInstant
import com.tink.moneymanagerui.extensions.toPeriodIdentifier
import com.tink.service.network.ErrorState
import com.tink.service.network.LoadingState
import com.tink.service.network.ResponseState
import com.tink.service.network.SuccessState
import com.tink.service.statistics.StatisticsQueryDescriptor
import com.tink.service.statistics.StatisticsService
import com.tink.service.util.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import se.tink.android.livedata.map
import se.tink.android.repository.service.DataRefreshHandler
import se.tink.android.repository.service.Refreshable
import se.tink.android.repository.transaction.TransactionUpdateEventBus
import se.tink.android.repository.user.UserRepository
import se.tink.commons.extensions.isInPeriod
import se.tink.commons.extensions.toDateTime
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject

private const val STATISTICS_FETCH_TIMEOUT = 15_000L
private const val POLLING_INTERVAL = 2_000L

@ExperimentalCoroutinesApi
@PfmScope
internal class StatisticsRepository @Inject constructor(
    private val statisticsService: StatisticsService,
    dataRefreshHandler: DataRefreshHandler,
    private val userRepository: UserRepository,
    transactionUpdateEventBus: TransactionUpdateEventBus,
    private val dispatcher: DispatcherProvider
) : Refreshable {

    init {
        dataRefreshHandler.registerRefreshable(this)
        transactionUpdateEventBus.subscribe { refresh() }
    }

    private val refreshTrigger = MutableLiveData<Unit>()

    // TODO: Don't expose LiveData directly from a repository. They belong in ViewModels.
    private val statisticsLiveData = MediatorLiveData<List<Statistics>>().apply {
        fun update() {
            CoroutineScope(dispatcher.io()).launch {
                try {
                    withTimeout(STATISTICS_FETCH_TIMEOUT) {
                        while (isActive) {
                            val userProfile = userRepository.userProfile.value
                            if (userProfile != null) {
                                val result = queryForStatistics(userProfile)
                                if (result != null) {
                                    postValue(result)
                                    cancel()
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

    private val statisticsLiveDataState = MediatorLiveData<ResponseState<List<Statistics>>>().apply {
        fun update() {
            CoroutineScope(dispatcher.io()).launch {
                try {
                    withTimeout(STATISTICS_FETCH_TIMEOUT) {
                        while (isActive) {
                            val userProfile = userRepository.userProfile.value
                            if (userProfile != null) {
                                val result = queryForStatistics(userProfile)
                                if (result != null) {
                                    postValue(SuccessState(result))
                                    cancel()
                                }
                            } else {
                                postValue(LoadingState)
                            }
                            delay(POLLING_INTERVAL)
                        }
                    }
                } catch (timeoutException: TimeoutCancellationException) {
                    postValue(ErrorState("Request timed out"))
                }
            }
        }
        addSource(userRepository.userProfile) { update() }
        addSource(refreshTrigger) {
            postValue(LoadingState)
            update()
        }
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
            ?.toDateTime() ?: LocalDateTime.now()

        val endDate = LocalDateTime.now()
            .withDayOfMonth(backfillStartTime.dayOfMonth)
            .withHour(23)
            .withMinute(59)
            .withSecond(59)

        val existingPeriodIdentifiers = distinctBy { it.period.identifier }.map { it.period.identifier }
        val monthsMissingTransactions = generateSequence(backfillStartTime) { date ->
            date.plusMonths(1)
        }.takeWhile { iteratedDate ->
            iteratedDate.isBefore(endDate)
        }.filter { iteratedDate ->
            !existingPeriodIdentifiers.contains(iteratedDate.toPeriodIdentifier())
        }.map { iteratedDate ->
            getZeroDataStatisticsForType(currencyCode, iteratedDate)
        }
        return this.plus(monthsMissingTransactions)
    }

    private fun getZeroDataStatisticsForType(currencyCode: String, dateTime: LocalDateTime): Statistics {
        val startOfCurrentMonth = dateTime.getStartOfMonth()
        val endOfCurrentMonth = dateTime.getEndOfMonth()

        val period =
            MonthPeriod(
                monthOfYear = dateTime.monthValue,
                year = dateTime.year,
                identifier = dateTime.toPeriodIdentifier(),
                start = startOfCurrentMonth.toInstant(),
                end = endOfCurrentMonth.toInstant()
            )
        return Statistics(
            identifier = period.toString(),
            type = Statistics.Type.EXPENSES_BY_CATEGORY,
            period = period,
            value = Amount(ExactNumber(0), currencyCode)
        )
    }

    val statistics: LiveData<List<Statistics>> = statisticsLiveData

    val statisticsState: LiveData<ResponseState<List<Statistics>>> = statisticsLiveDataState

    val fetchedStatistics: LiveData<List<Statistics>> = MediatorLiveData<List<Statistics>>().apply {
        addSource(statisticsState) {
            if (it is SuccessState<List<Statistics>>) {
                value = it.data
            }
        }
    }

    private val periodMap: LiveData<Map<String, Period>> = statistics.map { statistics ->
        statistics.associate { it.period.identifier to it.period }
    }

    private val periodMapState: LiveData<ResponseState<Map<String, Period>>> = statisticsState.map { statistics ->
        when (statistics) {
            is LoadingState -> LoadingState
            is ErrorState -> ErrorState(statistics.errorMessage)
            is SuccessState -> {
                SuccessState(
                    statistics.data.associate { it.period.identifier to it.period }
                )
            }
        }
    }

    val periods: LiveData<List<Period>> = Transformations.map(periodMap) {
        it?.values?.toList()
    }

    val periodsState: LiveData<ResponseState<List<Period>>> = Transformations.map(periodMapState) { periodMapState ->
        when (periodMapState) {
            is LoadingState -> LoadingState
            is ErrorState -> ErrorState("")
            is SuccessState -> {
                SuccessState(
                    periodMapState.data.values.toList()
                )
            }
        }
    }

    val currentPeriod: LiveData<Period> = Transformations.map(periodMap) {
        it?.values?.firstOrNull { first -> first.isInPeriod(LocalDateTime.now()) }
    }

    val currentPeriodState: LiveData<ResponseState<Period>> = Transformations.map(periodMapState) { periodMapState ->
        when (periodMapState) {
            is LoadingState -> LoadingState
            is ErrorState -> ErrorState(periodMapState.errorMessage)
            is SuccessState -> {
                val period = periodMapState.data.values.firstOrNull { it.isInPeriod(LocalDateTime.now()) }
                    ?: periodMapState.data.values.maxByOrNull { it.identifier }
                if (period == null) {
                    ErrorState("Did not have data for the current period.")
                } else {
                    SuccessState(period)
                }
            }
        }
    }

    override fun refresh() {
        refreshTrigger.postValue(Unit)
    }
}
