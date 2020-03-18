package com.tink.pfmui.insights.viewproviders

import android.view.View
import android.view.ViewGroup
import com.tink.pfmui.R
import com.tink.pfmui.insights.actionhandling.ActionHandler
import kotlinx.android.synthetic.main.item_insight_weekly_expenses_by_day.view.*
import se.tink.android.annotations.ContributesInsightViewProvider
import se.tink.commons.currency.AmountFormatter
import se.tink.commons.extensions.inflate
import com.tink.model.insights.Insight
import com.tink.model.insights.InsightData
import com.tink.model.insights.InsightType
import com.tink.model.relations.ExpensesByDay
import se.tink.commons.extensions.floatValue
import se.tink.commons.extensions.toDateTime
import se.tink.insights.getViewType
import se.tink.utils.DateUtils
import javax.inject.Inject

@ContributesInsightViewProvider
class WeeklyExpensesByDayViewProvider @Inject constructor(
    private val dateUtils: DateUtils,
    private val amountFormatter: AmountFormatter
) : InsightViewProvider {
    override fun viewHolder(parent: ViewGroup, actionHandler: ActionHandler): InsightViewHolder =
        WeeklyExpensesByDayInsightViewHolder(parent, actionHandler)

    override fun getDataHolder(insight: Insight): InsightDataHolder {
        val chartData = (insight.data as InsightData.WeeklyExpensesByDayData)
            .expensesByDay
            .sortedBy { it.date } // This will ensure we show data oldest to newest, from left to right in the chart
            .toChartData(dateUtils, amountFormatter)
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
) : InsightViewHolder(parent.inflate(R.layout.item_insight_weekly_expenses_by_day), actionHandler),
    InsightCommonBottomPart {
    override val view: View = itemView

    override fun bind(data: InsightDataHolder, insight: Insight) {
        require(data is WeeklyExpensesByDayDataHolder)

        setupCommonBottomPart(insight)

        view.apply {
            totalExpensesChart.data = data.chartData.totalAmountData
            totalExpensesChart.amountLabels = data.chartData.totalAmountLabels
            totalExpensesChart.labels = data.chartData.dayLabels

            averageExpensesChart.data = data.chartData.averageAmountData
            averageExpensesChart.amountLabels = listOf()
            averageExpensesChart.labels = listOf()
        }
    }
}

private fun List<ExpensesByDay>.toChartData(
    dateUtils: DateUtils,
    amountFormatter: AmountFormatter
) = ExpensesByDayChartData(
    map { dateUtils.getDayOfWeek(it.date.toDateTime()) },
    map { it.totalAmount.value.floatValue() },
    map {
        amountFormatter.format(
            amount = it.totalAmount,
            useSymbol = false,
            useSign = false
        )
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
)
