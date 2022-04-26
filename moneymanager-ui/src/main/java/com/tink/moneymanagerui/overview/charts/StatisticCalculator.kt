package com.tink.moneymanagerui.overview.charts

import com.tink.model.category.Category
import com.tink.model.statistics.Statistics
import com.tink.model.time.Period
import com.tink.moneymanagerui.charts.extensions.sumByFloat
import se.tink.commons.extensions.floatValue
import se.tink.commons.extensions.isInPeriod
import se.tink.commons.extensions.recursiveIdList
import se.tink.commons.extensions.toDateTime
import se.tink.utils.DateUtils
import java.time.LocalDateTime
import kotlin.math.abs

internal sealed class ChartItem {
    abstract val amount: Float
    abstract val name: String
}

internal data class StatisticItem(val category: Category, override val amount: Float) :
    ChartItem() {
    override val name: String get() = category.name
}

internal data class TransactionsItem(
    override val name: String,
    override val amount: Float,
    val ids: List<String>
) : ChartItem()

internal sealed class ChartList {
    // List with structural equals
    abstract val items: ArrayList<out ChartItem>
}

internal data class StatisticItemsList(override val items: ArrayList<StatisticItem>) : ChartList()
internal data class TransactionsItemsList(override val items: ArrayList<TransactionsItem>) :
    ChartList()

internal fun getPeriodString(
    dateUtils: DateUtils,
    period: Period,
    toToday: Boolean = true
): String {
    val endOrToday = if (toToday && period.isInPeriod(LocalDateTime.now(dateUtils.timeZoneId))) {
        LocalDateTime.now()
    } else {
        period.end.toDateTime()
    }

    return dateUtils.formatDateRange(period.start.toDateTime(), endOrToday, true)
}

internal fun calculateStatistic(
    stats: List<Statistics>,
    categories: List<Category>,
    period: Period
): StatisticItemsList {
    val dataMap = stats
        .filter { it.period == period }
        .groupBy { categories.findParentOfCategoryWithId(it.identifier) }
        .mapNotNull { (category, valueList) ->
            if (category == null) return@mapNotNull null
            val amount = valueList.sumByFloat { abs(it.value.value.floatValue()) }
            category to amount
        }
        .toMap()

    return StatisticItemsList(
        ArrayList(
            dataMap
                .filter { (_, value) -> value > 0 }
                .map { (key, value) -> StatisticItem(key, value) }
                .sortedWith(compareByDescending { it.amount.toDouble() })
        )
    )
}

private fun List<Category>.findParentOfCategoryWithId(id: String): Category? =
    find { it.recursiveIdList.contains(id) }
