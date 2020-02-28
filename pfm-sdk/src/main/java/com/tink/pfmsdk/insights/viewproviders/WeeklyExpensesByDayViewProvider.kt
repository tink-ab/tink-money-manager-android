package com.tink.pfmsdk.insights.viewproviders

import android.view.View
import android.view.ViewGroup
import com.tink.pfmsdk.R
import com.tink.pfmsdk.insights.actionhandling.ActionHandler
import se.tink.android.annotations.ContributesInsightViewProvider
import se.tink.commons.currency.AmountFormatter
import se.tink.commons.extensions.inflate
import se.tink.core.models.insights.Insight
import se.tink.core.models.insights.InsightData
import se.tink.core.models.insights.InsightType
import se.tink.core.models.relations.StatisticsByDay
import se.tink.insights.getViewType
import javax.inject.Inject

@ContributesInsightViewProvider
class WeeklyExpensesByDayViewProvider @Inject constructor(
    private val amountFormatter: AmountFormatter
) : InsightViewProvider {
    override fun viewHolder(parent: ViewGroup, actionHandler: ActionHandler): InsightViewHolder =
        WeeklyExpensesByDayInsightViewHolder(parent, actionHandler)

    override fun getDataHolder(insight: Insight): InsightDataHolder =
        WeeklyExpensesByDayDataHolder((insight.data as InsightData.WeeklyExpensesByDayData).statistics)

    override val viewType = getViewType()

    override val supportedInsightTypes = listOf(InsightType.WEEKLY_SUMMARY_EXPENSES_BY_DAY)
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
            // TODO: PFMSDK: Set data
        }
    }
}

data class WeeklyExpensesByDayDataHolder(
    val statistics: List<StatisticsByDay>
) : InsightDataHolder