package com.tink.pfmui.overview.charts

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tink.pfmui.R
import com.tink.pfmui.charts.models.PeriodBalance
import com.tink.pfmui.mapper.ModelMapperManager
import com.tink.pfmui.repository.StatisticsRepository
import com.tink.pfmui.util.CurrencyUtils
import se.tink.android.di.application.ApplicationScoped
import se.tink.android.livedata.map
import se.tink.commons.categories.iconColor
import se.tink.core.extensions.whenNonNull
import se.tink.core.models.Category
import se.tink.utils.DateUtils
import javax.inject.Inject

internal class StatisticsOverTimeViewModel @Inject constructor(
    private val statisticsRepository: StatisticsRepository,
    private val dateUtils: DateUtils,
    @ApplicationScoped context: Context
) : ViewModel() {

    private val periodSelection = MutableLiveData<PeriodSelection>().apply {
        value = PeriodSelection.SixMonths()
    }

    private val category = MutableLiveData<Category>()

    private val statistics = statisticsRepository.getStatistics()

    private val allPeriodBalances = MediatorLiveData<List<PeriodBalance>>().apply {

        fun update() {
            whenNonNull(
                statistics.value,
                statisticsRepository.periodMap.value,
                statisticsRepository.currentPeriod.value,
                category.value
            ) { statisticsTree, periodMap, currentPeriod, category ->

                val statistics = when (category.type) {
                    Category.Type.TYPE_INCOME -> statisticsTree.incomeByCategoryCode
                    Category.Type.TYPE_EXPENSES -> statisticsTree.expensesByCategoryCode
                    else -> return
                }

                val balances =
                    ModelMapperManager.mapStatisticsToPeriodBalanceForAllTimeByCategoryCode(
                        statistics,
                        currentPeriod,
                        periodMap,
                        category.code,
                        dateUtils
                    ).sortedByDescending { it.period?.stop }
                postValue(balances)
            }
        }

        addSource(category) { update() }
        addSource(statistics) { update() }
        addSource(statisticsRepository.periodMap) { update() }
        addSource(statisticsRepository.currentPeriod) { update() }
    }

    private val periodBalances = MediatorLiveData<List<PeriodBalance>>().apply {

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

    val barChartItems = periodBalances.map { list ->

        val maxValue = run {
            val computedMax = list.map { it.amount }.max() ?: 0.0
            if (computedMax <= 0) 1.0 else computedMax
        }

        val average = list.map { it.amount }.average() / maxValue

        val items = list.map {
            val periodLabel = it.period?.let { period ->
                dateUtils.getMonthFromDateTime(period.stop, true)
            }

            val amountLabel = CurrencyUtils.formatAmountRoundWithCurrencySymbol(it.amount)
            val factor = it.amount / maxValue
            val color = category.value?.iconColor() ?: 0

            BarChartItem(amountLabel, periodLabel, factor.toFloat(), color)
        }

        BarChartItems(items, average.toFloat())
    }

    val average = periodBalances.map { balances ->
        val averageAmount = balances.map { it.amount }.average()
        val averageString = CurrencyUtils.formatAmountExactWithCurrencySymbol(averageAmount)
        context.resources.getString(
            R.string.tink_expenses_header_description_average,
            averageString
        )
    }

    val sum = periodBalances.map { balances ->
        val sumAmount = balances.map { it.amount }.sum()
        CurrencyUtils.formatAmountExactWithCurrencySymbol(sumAmount)
    }

    val periodSelectionButtonText: LiveData<String> =
        periodSelection.map { context.getString(it.labelResource) }

    fun selectPeriod(periodSelection: PeriodSelection) {
        this.periodSelection.value = periodSelection
    }

    fun selectCategory(category: Category) {
        this.category.value = category
    }
}
