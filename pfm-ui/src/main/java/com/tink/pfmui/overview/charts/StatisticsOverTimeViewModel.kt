package com.tink.pfmui.overview.charts

//import se.tink.extensions.averageExcludingCurrent
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tink.pfmui.charts.models.PeriodBalance
import com.tink.pfmui.mapper.ModelMapperManager
import com.tink.pfmui.repository.StatisticsRepository
import se.tink.android.di.application.ApplicationScoped
import se.tink.android.livedata.map
import se.tink.commons.currency.AmountFormatter
import se.tink.core.extensions.whenNonNull
import se.tink.core.models.misc.Amount
import se.tink.utils.DateUtils
import javax.inject.Inject

internal class StatisticsOverTimeViewModel @Inject constructor(
    private val statisticsRepository: StatisticsRepository,
    private val dateUtils: DateUtils,
    private val amountFormatter: AmountFormatter,
    @ApplicationScoped context: Context
) : ViewModel() {

    private val periodSelection = MutableLiveData<PeriodSelection>().apply {
        value = PeriodSelection.SixMonths()
    }

    private val statistics = statisticsRepository.getStatistics()

    private val allPeriodBalances = MediatorLiveData<List<PeriodBalance>>().apply {

        fun update() {
            whenNonNull(
                statistics.value,
                statisticsRepository.periodMap.value,
                statisticsRepository.currentPeriod.value
            ) { statisticsTree, periodMap, currentPeriod ->

                val balances =
                    //TODO: More than one year & respect period selection
                    ModelMapperManager.mapStatisticsToPeriodBalanceFor1YearByCategoryCode(
                        statisticsTree.expensesByCategoryCode,
                        currentPeriod,
                        periodMap,
                        "expenses"
                    ).sortedByDescending { it.period?.stop }
                postValue(balances)
            }
        }

        addSource(statistics) { update() }
        addSource(statisticsRepository.periodMap) { update() }
        addSource(statisticsRepository.currentPeriod) { update() }
    }

    val periodBalances = MediatorLiveData<List<PeriodBalance>>().apply {

        fun update() {

            val allBalances = allPeriodBalances.value ?: return
            val periodSelection = periodSelection.value ?: return

            val filteredBalances = allBalances.filter {
                it.period?.start?.isBefore(periodSelection.end) == true
                        && it.period?.start?.isAfter(periodSelection.start) == true
            }

            postValue(filteredBalances)
        }
        addSource(allPeriodBalances) { update() }
        addSource(periodSelection) { update() }
    }

//    val loading: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
//        value = true
//        addSource(periodSummaries) { value = false }
//    }

    val periodSelectionButtonText: LiveData<String> =
        periodSelection.map { context.getString(it.labelResource) }

    private fun Amount.formatWithoutSignAndSymbol(): String =
        amountFormatter.format(
            amount = this,
            useSymbol = false,
            useSign = false
        )

    private fun Amount.format(): String = amountFormatter.format(this)

    fun selectPeriod(periodSelection: PeriodSelection) {
        this.periodSelection.value = periodSelection
    }
}
