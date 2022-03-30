package com.tink.moneymanagerui.insights.viewproviders

import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnNextLayout
import com.tink.model.insights.Insight
import com.tink.model.insights.InsightData
import com.tink.model.insights.InsightType
import com.tink.model.relations.ExpensesByDay
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.charts.BarChartWithAmountLabels
import com.tink.moneymanagerui.insights.actionhandling.ActionHandler
import com.tink.moneymanagerui.util.CurrencyUtils
import kotlinx.android.synthetic.main.tink_item_insight_weekly_expenses_by_day.view.*
import se.tink.android.annotations.ContributesInsightViewProvider
import se.tink.commons.extensions.doubleValue
import se.tink.commons.extensions.floatValue
import se.tink.commons.extensions.inflate
import se.tink.insights.getViewType
import se.tink.utils.DateUtils
import java.text.DecimalFormatSymbols
import javax.inject.Inject
import kotlin.math.roundToInt

@ContributesInsightViewProvider
class WeeklyExpensesByDayViewProvider @Inject constructor(
    private val dateUtils: DateUtils
) : InsightViewProvider {
    override fun viewHolder(parent: ViewGroup, actionHandler: ActionHandler): InsightViewHolder =
        WeeklyExpensesByDayInsightViewHolder(parent, actionHandler)

    override fun getDataHolder(insight: Insight): InsightDataHolder {
        val chartData = (insight.data as InsightData.WeeklyExpensesByDayData)
            .expensesByDay
            .sortedBy { it.date } // This will ensure we show data oldest to newest, from left to right in the chart
            .toChartData(dateUtils)
        return WeeklyExpensesByDayDataHolder(chartData)
    }

    override val viewType = getViewType()

    override val supportedInsightTypes = listOf(
        InsightType.WEEKLY_SUMMARY_EXPENSES_BY_DAY
    )
}

class WeeklyExpensesByDayInsightViewHolder(
    parent: ViewGroup,
    actionHandler: ActionHandler
) : InsightViewHolder(parent.inflate(R.layout.tink_item_insight_weekly_expenses_by_day), actionHandler),
    InsightCommonBottomPart {
    override val view: View = itemView

    override fun bind(data: InsightDataHolder, insight: Insight) {
        require(data is WeeklyExpensesByDayDataHolder)

        setupCommonBottomPart(insight)

        if (data.chartData.totalAmountData.size == data.chartData.dayLabels.size &&
            data.chartData.totalAmountData.size == data.chartData.formattedTotalAmountLabels.size &&
            data.chartData.totalAmountData.size == data.chartData.averageAmountData.size
        ) {
            setupBarChart(data)
        }
    }

    private fun setupBarChart(data: WeeklyExpensesByDayDataHolder) {
        view.apply {
            totalExpensesChart.data = data.chartData.totalAmountData.withIndex().map { item ->
                BarChartWithAmountLabels.AmountWithLabel(
                    item.value,
                    data.chartData.dayLabels[item.index],
                    data.chartData.formattedTotalAmountLabels[item.index]
                )
            }

            averageExpensesChart.data = data.chartData.averageAmountData.map {
                BarChartWithAmountLabels.AmountWithLabel(it, "", "0")
            }
            averageExpensesChart.showAmountLabels = false

            barChartContainer.doOnNextLayout {
                val expensesMax = data.chartData.totalAmountData.maxOrNull() ?: 0.000001f
                val averageMax = data.chartData.averageAmountData.maxOrNull() ?: 0.000001f
                val labelHeight = averageExpensesChart.getLabelHeight().toInt()
                val bottomMargin = averageExpensesChart.barChartMarginBottom
                val containerHeight = it.height

                if (expensesMax > averageMax) {
                    val expenseBarHeight = containerHeight - bottomMargin - labelHeight
                    val scale = averageMax / expensesMax
                    val averageBarHeight = (scale * expenseBarHeight).roundToInt()
                    val averageHeight = averageBarHeight + bottomMargin + labelHeight

                    setBarChartHeights(averageHeight, containerHeight)
                } else {
                    val averageHeight = containerHeight - labelHeight
                    val averageBarHeight = averageHeight - bottomMargin - labelHeight
                    val scale = expensesMax / averageMax
                    val expenseBarHeight = (scale * averageBarHeight).roundToInt()
                    val expenseHeight = expenseBarHeight + labelHeight + bottomMargin

                    setBarChartHeights(averageHeight, expenseHeight)
                }
            }
        }
    }

    private fun View.setBarChartHeights(averageChartHeight: Int, expenseChartHeight: Int) {
        averageExpensesChart.layoutParams = averageExpensesChart.layoutParams.apply {
            height = averageChartHeight
        }
        totalExpensesChart.layoutParams = totalExpensesChart.layoutParams.apply {
            height = expenseChartHeight
        }
    }
}

private fun List<ExpensesByDay>.toChartData(
    dateUtils: DateUtils
) = ExpensesByDayChartData(
    map { dateUtils.getDayOfWeek(it.date) },
    map { it.totalAmount.value.floatValue() },
    map {
        CurrencyUtils.formatAmountRoundWithoutCurrencySymbol(it.totalAmount.value.doubleValue(), null)
            .trimEnd('0')
            .trimEnd(DecimalFormatSymbols.getInstance().decimalSeparator)
    },
    map { it.averageAmount.value.floatValue() }
)

data class WeeklyExpensesByDayDataHolder(
    val chartData: ExpensesByDayChartData
) : InsightDataHolder

data class ExpensesByDayChartData(
    val dayLabels: List<String>,
    val totalAmountData: List<Float>,
    val totalAmountLabels: List<String>,
    val averageAmountData: List<Float>
) {
    val formattedTotalAmountLabels: List<String> =
        totalAmountLabels.map {
            it.ifEmpty {
                "0"
            }
        }
}
