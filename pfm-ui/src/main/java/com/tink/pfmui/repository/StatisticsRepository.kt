package com.tink.pfmui.repository

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
import com.tink.service.statistics.StatisticsQueryDescriptor
import com.tink.service.statistics.StatisticsService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.threeten.bp.Instant
import se.tink.android.livedata.map
import se.tink.android.repository.service.DataRefreshHandler
import se.tink.android.repository.service.Refreshable
import se.tink.android.repository.transaction.TransactionUpdateEventBus
import se.tink.android.repository.user.UserRepository
import se.tink.commons.extensions.isInPeriod
import javax.inject.Inject

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
            val userProfile = userRepository.userProfile.value ?: return
            scope.launch {
                val result: List<Statistics> = try {
                    statisticsService.query(
                        StatisticsQueryDescriptor(
                            userProfile.periodMode,
                            userProfile.currency
                        )
                    )
                     // If statistics is missing data for the current month,
                     // add statistics with amount set to zero for that month as fallback for the UI.
                     // This can happen at the start of a new monthly period.
                        .handleNoDataForCurrentPeriod()
                } catch (error: Throwable) {
                    emptyList()
                }
                postValue(result)
            }
        }
        addSource(userRepository.userProfile) { update() }
        addSource(refreshTrigger) { update() }
    }

    private fun List<Statistics>.handleNoDataForCurrentPeriod(): List<Statistics> {
        val missingExpensesForCurrentPeriod =
            none { it.period.isInPeriod(DateTime.now()) && it.type == Statistics.Type.EXPENSES_BY_CATEGORY }
        val missingIncomeForCurrentPeriod =
            none { it.period.isInPeriod(DateTime.now()) && it.type == Statistics.Type.INCOME_BY_CATEGORY }
        val currencyCode = first().value.currencyCode
        val data = toMutableList()
        if (missingExpensesForCurrentPeriod) {
            data.add(
                getZeroDataStatisticsForType(
                    Statistics.Type.EXPENSES_BY_CATEGORY,
                    currencyCode
                )
            )
        }
        if (missingIncomeForCurrentPeriod) {
            data.add(
                getZeroDataStatisticsForType(
                    Statistics.Type.INCOME_BY_CATEGORY,
                    currencyCode
                )
            )
        }
        return data
    }

    private fun getZeroDataStatisticsForType(
        type: Statistics.Type,
        currencyCode: String
    ): Statistics {
        val startOfCurrentMonth = DateTime.now().dayOfMonth().withMinimumValue()
        val endOfCurrentMonth = DateTime.now().dayOfMonth().withMaximumValue()
        val period =
            MonthPeriod(
                monthOfYear = DateTime.now().monthOfYear,
                year = DateTime.now().year,
                identifier = "",
                start = Instant.ofEpochMilli(startOfCurrentMonth.millis),
                end = Instant.ofEpochMilli(endOfCurrentMonth.millis)
            )
        return Statistics(
            identifier = period.toString(),
            type = type,
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

