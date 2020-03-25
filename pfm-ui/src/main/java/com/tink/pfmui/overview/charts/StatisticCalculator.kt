package com.tink.pfmui.overview.charts

import android.content.Context
import com.tink.pfmui.R
import org.joda.time.DateTime
import com.tink.pfmui.charts.extensions.mergeValue
import se.tink.commons.extensions.floatValue
import com.tink.model.category.Category
import com.tink.model.time.Period
import se.tink.commons.extensions.isInPeriod
import se.tink.commons.extensions.toDateTime
import com.tink.model.statistic.Statistic
import se.tink.utils.DateUtils

internal sealed class ChartItem {
    abstract val amount: Float
    abstract val name: String
}

internal data class StatisticItem(val category: Category, override val amount: Float) : ChartItem() {
    override val name: String get() = category.name
}

internal data class TransactionsItem(override val name: String, override val amount: Float, val ids: List<String>) : ChartItem()

internal sealed class ChartList {
    // List with structural equals
    abstract val items: ArrayList<out ChartItem>
}

internal data class StatisticItemsList(override val items: ArrayList<StatisticItem>) : ChartList()
internal data class TransactionsItemsList(override val items: ArrayList<TransactionsItem>) : ChartList()

//TODO: Core setup
internal fun getPeriodString(dateUtils: DateUtils, period: Period, context: Context, toToday: Boolean = true): String {
    return if (toToday && period.isInPeriod(DateTime.now())) {
        context.getString(
            R.string.tink_date_span_string,
            dateUtils.formatDateHumanShort(period.start.toDateTime()),
            context.getString(R.string.tink_date_format_human_today)
        )
    } else {
        context.getString(
            R.string.tink_until_next_date,
            dateUtils.formatDateHumanShort(period.end.toDateTime())
        )
    }
}

internal fun calculateStatistic(
    stats: Map<String, Statistic>,
    categories: List<Category>,
    period: Period
): StatisticItemsList {
    val dataMap = mutableMapOf<Category, Float>()
    for ((key, st) in stats) {
        getCategory(categories, key)?.let { category ->
            st.children.values
                .filter { it.period == period }
                .forEach {
                    it.value.value.let {
                        dataMap.mergeValue(category, Math.abs(it.floatValue()), Float::plus)
                    }
                }
        }
    }
    return StatisticItemsList(
        ArrayList(
            dataMap
                .filter { (_, value) -> value > 0 }
                .map { (key, value) -> StatisticItem(key, value) }
                .sortedWith(compareByDescending { it.amount.toDouble() })
        )
    )
}

private fun getCategory(categories: List<Category>, key: String): Category? =
    categories.firstOrNull { it.code == key || key.startsWith("${it.code}.") }