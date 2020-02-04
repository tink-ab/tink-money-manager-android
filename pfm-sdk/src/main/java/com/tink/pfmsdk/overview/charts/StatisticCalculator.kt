package com.tink.pfmsdk.overview.charts

import android.content.Context
import com.tink.pfmsdk.R
import org.joda.time.DateTime
import com.tink.pfmsdk.charts.extensions.mergeValue
import com.tink.pfmsdk.charts.extensions.sumByFloat
import se.tink.core.models.Category
import se.tink.core.models.misc.Period
import se.tink.core.models.statistic.Statistic
import se.tink.core.models.transaction.Transaction
import se.tink.utils.DateUtils
import kotlin.math.absoluteValue

sealed class ChartItem {
    abstract val amount: Float
    abstract val name: String
}

data class StatisticItem(val category: Category, override val amount: Float) : ChartItem() {
    override val name: String get() = category.name
}

data class TransactionsItem(override val name: String, override val amount: Float, val ids: List<String>) : ChartItem()

sealed class ChartList {
    // List with structural equals
    abstract val items: ArrayList<out ChartItem>
}

data class StatisticItemsList(override val items: ArrayList<StatisticItem>) : ChartList()
data class TransactionsItemsList(override val items: ArrayList<TransactionsItem>) : ChartList()

private const val MAX_TRANSACTIONS_TO_SHOW = 6

fun getPeriodString(dateUtils: DateUtils, period: Period, context: Context, toToday: Boolean = true): String {
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

fun calculateStatistic(
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

fun calculateTransactionsStatistic(
    other: String,
    transactions: List<Transaction>
): TransactionsItemsList {
    val items = transactions
        .groupBy { it.description }
        .entries
        .map { (name, transactions) ->
            TransactionsItem(
                name,
                transactions.sumByFloat { transaction ->
                    transaction
                        .dispensableAmount.value
                        .floatValue()
                        .absoluteValue
                },
                transactions.map { it.id }
            )
        }
        .sortedByDescending { it.amount }

    if (items.size < MAX_TRANSACTIONS_TO_SHOW) {
        return TransactionsItemsList(ArrayList(items))
    }
    return TransactionsItemsList(
        ArrayList(items.take(MAX_TRANSACTIONS_TO_SHOW - 1))
            .apply {
                val toOther =
                    items.drop(MAX_TRANSACTIONS_TO_SHOW - 1)
                add(
                    TransactionsItem(
                        other,
                        toOther.sumByFloat { it.amount },
                        toOther.flatMap { it.ids })
                )
            }
    )
}