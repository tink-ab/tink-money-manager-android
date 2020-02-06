package com.tink.pfmsdk.util

import com.google.common.collect.Lists
import com.google.common.collect.Maps
import com.tink.pfmsdk.TimezoneManager
import com.tink.pfmsdk.charts.models.PeriodBalance
import com.tink.pfmsdk.collections.Currencies
import com.tink.pfmsdk.configuration.SuitableLocaleFinder
import com.tink.pfmsdk.mapper.ModelConverterImplementation
import org.joda.time.DateTime
import se.tink.converter.ModelConverter
import se.tink.core.extensions.whenNonNull
import se.tink.core.models.Category
import se.tink.core.models.misc.Amount
import se.tink.core.models.misc.ExactNumber
import se.tink.core.models.misc.Period
import se.tink.core.models.statistic.Statistic
import se.tink.utils.DateUtils
import java.math.BigDecimal
import java.util.ArrayList
import kotlin.math.abs

internal object ModelMapperManager : ModelConverter {
    private val mapper = ModelConverterImplementation()

    override fun <KR, VR, KI, VI> map(
        source: Map<KI, VI>, destinationKeyType: Class<KR>,
        destinationValueType: Class<VR>
    ): Map<KR, VR> {
        val returnMap: MutableMap<KR, VR> =
            Maps.newHashMap()
        val keySet = source.keys
        for (key in keySet) {
            val value = source[key]
            returnMap[map(key, destinationKeyType)] = map(value, destinationValueType)
        }
        return returnMap
    }

    override fun <S, D> map(
        source: Collection<S>,
        destinationType: Class<D>
    ): List<D> {
        val destinationList = ArrayList<D>()
        for (sourceObject in source) {
            destinationList.add(map(sourceObject, destinationType))
        }
        return destinationList
    }

    override fun <S, D> map(source: S, destinationType: Class<D>): D {
        return mapper.map(source, destinationType)
    }

    @JvmStatic
    fun mapLeftToSpendToPeriodBalanceForCurrentMonth(
        statistics: Map<String, Statistic>,
        period: Period,
        periodMap: Map<String, Period>
    ): List<PeriodBalance> {
        val mappedItems =
            convertToPeriodBalances(
                StatisticsToMap(
                    statistics,
                    period,
                    periodMap,
                    true,
                    true
                )
            )
        mappedItems.sortWith(Comparator { t1: PeriodBalance, t2: PeriodBalance ->
            whenNonNull(t1.period, t2.period) { period1, period2 ->
                period1.start.millis.compareTo(period2.start.millis)
            } ?: 0
        })
        return mappedItems
    }

    @JvmStatic
    fun mapLeftToSpendStatisticsToPeriodBalanceFor1Year(
        statistics: Map<String, Statistic>,
        endPeriod: Period,
        periodMap: Map<String, Period>
    ): List<PeriodBalance> {
        return convertToPeriodBalances(
            StatisticsToMap(
                statistics,
                endPeriod,
                periodMap,
                true
            )
        )
    }

    private fun mapStatisticsToPeriodBalanceFor1Year(
        statistics: Map<String, Statistic>,
        endPeriod: Period?,
        periodMap: Map<String, Period>
    ): List<PeriodBalance> {
        val items = convertToPeriodBalances(
            StatisticsToMap(
                statistics,
                endPeriod,
                periodMap
            )
        )
        for (item in items) {
            item.amount = abs(item.amount)
        }
        return items
    }

    @JvmStatic
    fun mapStatisticsToPeriodBalanceFor1YearByCategoryCode(
        statistics: Map<String, Statistic>,
        endPeriod: Period?,
        periodMap: Map<String, Period>,
        categoryCode: String?
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
        return mapStatisticsToPeriodBalanceFor1Year(statisticsCopy, endPeriod, periodMap)
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
            val oneYearAgo = DateTime.now().minusYears(1)
            val currentYearChildren: MutableMap<String, Statistic> =
                Maps.newHashMap()
            for ((key, value) in children) {
                val periodEnd = value.period.stop
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
                totalForAllPeriods += statisticPeriod!!.amount.value.doubleValue()
            }
            val nrOfPeriods = currentYearChildren.size.toDouble()
            val averageForCategory = totalForAllPeriods / nrOfPeriods
            val afc = ExactNumber(
                BigDecimal(
                    abs(averageForCategory)
                )
            )
            average[categoryCode] = Amount(afc, Currencies.getSharedInstance().defaultCurrencyCode)
        }
        return average
    }

    private fun convertToPeriodBalances(source: StatisticsToMap): ArrayList<PeriodBalance> {
        var items =
            Lists.newArrayList<PeriodBalance>()
        if (source.isLeftToSpendData && source.isCurrentMonth) { // Daily for a period
            val monthly =
                source.statistics[source.period.toString()]
            if (monthly != null && monthly.hasChildren()) {
                for (key in monthly.children.keys) {
                    val daily =
                        monthly.children[key]
                    val total = mapper.map(
                        daily!!.amount,
                        Double::class.java
                    )
                    val p = daily.period
                    val pb = PeriodBalance(p, total)
                    items.add(pb)
                }
            }
        } else {
            val periods =
                DateUtils
                    .getInstance(
                        SuitableLocaleFinder().findLocale(),
                        TimezoneManager.defaultTimezone
                    )
                    .getYearMonthStringFor1YearByEndYearMonth(
                        source.period, source.periods, true
                    )
            items = addPeriodsToItems(periods)
            for (item in items) {
                if (source.isLeftToSpendData) {
                    val monthly = source.statistics[item.period?.toMonthString()]
                    handleLeftToSpendMonthly(item, monthly)
                } else {
                    for (key in source.statistics.keys) {
                        val statistic =
                            source.statistics[key]
                        val monthly =
                            statistic!!.children[item.period.toString()]
                                ?: continue
                        val total = monthly.amount.value.doubleValue()
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
        item.amount = monthly.amount.value.doubleValue()
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