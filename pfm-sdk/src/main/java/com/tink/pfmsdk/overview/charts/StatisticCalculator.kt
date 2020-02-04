package com.tink.pfmsdk.overview.charts

import android.content.Context
import com.tink.pfmsdk.R
import org.joda.time.DateTime
import com.tink.pfmsdk.charts.extensions.mergeValue
import se.tink.core.models.Category
import se.tink.core.models.misc.Period
import se.tink.core.models.statistic.Statistic
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

internal fun getPeriodString(dateUtils: DateUtils, period: Period, context: Context, toToday: Boolean = true): String {
    return if (toToday && period.isInPeriod(DateTime.now())) {
        context.getString(
            R.string.date_span_string,
            dateUtils.formatDateHumanShort(period.start),
            context.getString(R.string.date_format_human_today)
        )
    } else {
        context.getString(
            R.string.until_next_date,
            dateUtils.formatDateHumanShort(period.stop)
        )
    }
}

internal fun calculateStatistic(
    stats: MutableMap<String, Statistic>,
    categories: List<Category>,
    period: Period
): StatisticItemsList {
    val dataMap = mutableMapOf<Category, Float>()
    for ((key, st) in stats) {
        getCategory(categories, key)?.let { category ->
            st.children?.values
                ?.filter { it.period == period }
                ?.forEach {
                    it?.amount?.value?.let {
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