package com.tink.pfmui.mapper

import com.google.common.collect.Lists
import com.google.common.collect.Maps
import com.tink.model.category.Category
import com.tink.model.misc.Amount
import com.tink.model.statistic.Statistic
import com.tink.model.time.Period
import com.tink.pfmui.TimezoneManager
import com.tink.pfmui.charts.models.PeriodBalance
import com.tink.pfmui.collections.Currencies
import com.tink.pfmui.configuration.SuitableLocaleFinder
import org.threeten.bp.Instant
import org.threeten.bp.temporal.ChronoUnit
import se.tink.commons.extensions.doubleValue
import se.tink.commons.extensions.toExactNumber
import se.tink.commons.extensions.toMonthString
import se.tink.commons.extensions.whenNonNull
import se.tink.utils.DateUtils
import java.math.BigDecimal
import java.util.ArrayList
import kotlin.math.abs

internal object ModelMapperManager {

    @JvmStatic
    fun mapLeftToSpendToPeriodBalanceForCurrentMonth(
        statistics: Map<String, Statistic>,
        period: Period,
        periodMap: Map<String, Period>,
        dateUtils: DateUtils
    ): List<PeriodBalance> {
        val mappedItems =
            convertToPeriodBalances(
                StatisticsToMap(
                    statistics,
                    period,
                    periodMap,
                    true,
                    true
                ),
                dateUtils,
                paddToYear = true
            )
        mappedItems.sortWith(Comparator { t1: PeriodBalance, t2: PeriodBalance ->
            whenNonNull(
                t1.period,
                t2.period
            ) { period1, period2 ->
                period1.start.toEpochMilli().compareTo(period2.start.toEpochMilli())
            } ?: 0
        })
        return mappedItems
    }

    @JvmStatic
    fun mapLeftToSpendStatisticsToPeriodBalanceFor1Year(
        statistics: Map<String, Statistic>,
        endPeriod: Period,
        periodMap: Map<String, Period>,
        dateUtils: DateUtils
    ): List<PeriodBalance> {
        return convertToPeriodBalances(
            StatisticsToMap(
                statistics,
                endPeriod,
                periodMap,
                true
            ),
            dateUtils,
            paddToYear = true
        )
    }

    private fun mapStatisticsToPeriodBalances(
        statistics: Map<String, Statistic>,
        endPeriod: Period?,
        periodMap: Map<String, Period>,
        dateUtils: DateUtils,
        paddToYear: Boolean
    ): List<PeriodBalance> {
        val items =
            convertToPeriodBalances(
                StatisticsToMap(
                    statistics,
                    endPeriod,
                    periodMap
                ),
                dateUtils,
                paddToYear
            )
        for (item in items) {
            item.amount = abs(item.amount)
        }
        return items
    }

    private fun mapStatisticsToPeriodBalancesByCategoryCode(
        statistics: Map<String, Statistic>,
        endPeriod: Period?,
        periodMap: Map<String, Period>,
        categoryCode: String?,
        dateUtils: DateUtils,
        paddToYear: Boolean
    ): List<PeriodBalance> {
        val codesToRemove: MutableList<String> =
            Lists.newArrayList()
        for (key in statistics.keys) {
            if (!key.contains(categoryCode!!)) {
                codesToRemove.add(key)
            }
        }
        val statisticsCopy: MutableMap<String, Statistic> =
            Maps.newHashMap(
                statistics
            )
        for (key in codesToRemove) {
            statisticsCopy.remove(key)
        }
        return mapStatisticsToPeriodBalances(
            statisticsCopy,
            endPeriod,
            periodMap,
            dateUtils,
            paddToYear = paddToYear
        )
    }

    @JvmStatic
    fun mapStatisticsToPeriodBalanceFor1YearByCategoryCode(
        statistics: Map<String, Statistic>,
        endPeriod: Period?,
        periodMap: Map<String, Period>,
        categoryCode: String?,
        dateUtils: DateUtils
    ): List<PeriodBalance> {
        return mapStatisticsToPeriodBalancesByCategoryCode(
            statistics,
            endPeriod,
            periodMap,
            categoryCode,
            dateUtils,
            true
        )
    }

    @JvmStatic
    fun mapStatisticsToPeriodBalanceForAllTimeByCategoryCode(
        statistics: Map<String, Statistic>,
        endPeriod: Period?,
        periodMap: Map<String, Period>,
        categoryCode: String?,
        dateUtils: DateUtils
    ): List<PeriodBalance> {
        return mapStatisticsToPeriodBalancesByCategoryCode(
            statistics,
            endPeriod,
            periodMap,
            categoryCode,
            dateUtils,
            false
        )
    }

    @JvmStatic
    fun getLatest6MonthsFrom12MonthsItems(
        itemsFor1Year: List<PeriodBalance>
    ): List<PeriodBalance> {
        val items: MutableList<PeriodBalance> =
            Lists.newArrayList()
        val lastIndexOfItems = itemsFor1Year.size - 1
        for (i in 0 until itemsFor1Year.size / 2) {
            val index = lastIndexOfItems - i
            items.add(0, itemsFor1Year[index])
        }
        return items
    }

    fun mapAveragePerCategoryFromStatistics(
        statisticMap: Map<String, Statistic?>
    ): Map<String, Amount> {
        val average: MutableMap<String, Amount> =
            Maps.newHashMap()
        for (categoryCode in statisticMap.keys) {
            val statistic = statisticMap[categoryCode]
            val children =
                statistic!!.children
            val oneYearAgo = Instant.now().minus(1, ChronoUnit.YEARS)
            val currentYearChildren: MutableMap<String, Statistic> =
                Maps.newHashMap()
            for ((key, value) in children) {
                val periodEnd = value.period.end
                // We're interested in statistics from one year back
                if (oneYearAgo.isBefore(periodEnd)) {
                    currentYearChildren[key] = value
                }
            }
            if (currentYearChildren.isEmpty()) {
                continue
            }
            var totalForAllPeriods = 0.0
            for (period in currentYearChildren.keys) {
                val statisticPeriod =
                    currentYearChildren[period]
                totalForAllPeriods += statisticPeriod!!.value.value.doubleValue()
            }
            val nrOfPeriods = currentYearChildren.size.toDouble()
            val averageForCategory = totalForAllPeriods / nrOfPeriods
            val afc = BigDecimal(abs(averageForCategory)).toExactNumber()
            average[categoryCode] = Amount(afc, Currencies.getSharedInstance().defaultCurrencyCode)
        }
        return average
    }

    private fun convertToPeriodBalances(
        source: StatisticsToMap,
        dateUtils: DateUtils,
        paddToYear: Boolean
    ): ArrayList<PeriodBalance> {
        var items =
            Lists.newArrayList<PeriodBalance>()
        if (source.isLeftToSpendData && source.isCurrentMonth) { // Daily for a period
            val monthly =
                source.statistics[source.period.toString()]
            if (monthly != null && monthly.children.isNotEmpty()) {
                for (key in monthly.children.keys) {
                    val daily =
                        monthly.children[key]
                    val total = daily!!.value.value.doubleValue()
                    val p = daily.period
                    val pb = PeriodBalance(p, total)
                    items.add(pb)
                }
            }
        } else {
            val periods =
                dateUtils
                    .getYearMonthStringFor1YearByEndYearMonth(
                        source.period, source.periods, paddToYear
                    )
            items = addPeriodsToItems(
                periods
            )
            for (item in items) {
                if (source.isLeftToSpendData) {
                    val monthly = source.statistics[item.period?.toMonthString()]
                    handleLeftToSpendMonthly(
                        item,
                        monthly
                    )
                } else {
                    for (key in source.statistics.keys) {
                        val statistic =
                            source.statistics[key]
                        val monthly =
                            statistic!!.children[item.period.toString()]
                                ?: continue
                        val total = monthly.value.value.doubleValue()
                        val value = item.amount + total
                        item.amount = value
                    }
                }
            }
        }
        return items
    }

    private fun handleLeftToSpendMonthly(item: PeriodBalance, monthly: Statistic?) {
        if (monthly == null) {
            return
        }
        item.amount = monthly.value.value.doubleValue()
    }

    private fun addPeriodsToItems(periods: ArrayList<Period>): ArrayList<PeriodBalance> {
        val items = Lists.newArrayList<PeriodBalance>()
        for (period in periods) {
            val pb = PeriodBalance(period, 0.0)
            items.add(pb)
        }
        return items
    }
}

data class StatisticsToMap(
    val statistics: Map<String, Statistic>,
    val period: Period?,
    val periods: Map<String, Period>,
    val isLeftToSpendData: Boolean = false,
    val isCurrentMonth: Boolean = false,
    val categories: List<Category>? = null
)