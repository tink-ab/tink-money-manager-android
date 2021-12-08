package com.tink.moneymanagerui.insights.viewproviders

import android.view.View
import android.view.ViewGroup
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.insights.actionhandling.ActionHandler
import kotlinx.android.synthetic.main.tink_item_insight_weekly_expenses_by_day.view.*
import se.tink.android.annotations.ContributesInsightViewProvider
import se.tink.commons.extensions.inflate
import com.tink.model.insights.Insight
import com.tink.model.insights.InsightData
import com.tink.model.insights.InsightType
import com.tink.model.relations.ExpensesByDay
import com.tink.moneymanagerui.util.CurrencyUtils
import se.tink.commons.extensions.doubleValue
import se.tink.commons.extensions.floatValue
import se.tink.insights.getViewType
import se.tink.utils.DateUtils
import java.text.DecimalFormatSymbols
import javax.inject.Inject

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

        view.apply {
            totalExpensesChart.data = data.chartData.totalAmountData
            totalExpensesChart.amountLabels = data.chartData.formattedTotalAmountLabels
            totalExpensesChart.labels = data.chartData.dayLabels

            averageExpensesChart.data = data.chartData.averageAmountData
            averageExpensesChart.amountLabels = listOf()
            averageExpensesChart.labels = listOf()
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
            if (it.isEmpty()) {
                "0"
            } else {
                it
            }
        }
}
