package com.tink.pfmui.insights.viewproviders

import android.view.ViewGroup
import com.tink.pfmui.R
import com.tink.pfmui.insights.actionhandling.ActionHandler
import com.tink.pfmui.insights.enrichment.IconTypeViewDetails
import com.tink.pfmui.insights.extensions.getIcon
import se.tink.android.annotations.ContributesInsightViewProvider
import se.tink.core.models.insights.Insight
import se.tink.core.models.insights.InsightType
import se.tink.insights.InsightViewType
import se.tink.insights.getViewType
import javax.inject.Inject

@ContributesInsightViewProvider
class BudgetStateViewProvider @Inject constructor() : InsightViewProvider {
    override val supportedInsightTypes: List<InsightType> =
        listOf(
            InsightType.BUDGET_SUCCESS,
            InsightType.BUDGET_OVERSPENT,
            InsightType.BUDGET_CLOSE_NEGATIVE,
            InsightType.BUDGET_CLOSE_POSITIVE
        )

    override val viewType: InsightViewType = getViewType()

    override fun viewHolder(parent: ViewGroup, actionHandler: ActionHandler): InsightViewHolder =
        IconTextViewProvider.IconTextViewHolder(parent, actionHandler)

    override fun getDataHolder(insight: Insight): InsightDataHolder =
        IconTextViewProvider.IconTextViewDataHolder(
            title = insight.title,
            description = insight.description,
            icon = (insight.viewDetails as? IconTypeViewDetails).getIcon(),
            colorAttr = insight.getColorAttr(),
            state = insight.state,
            actions = insight.actions
        )

    private fun Insight.getColorAttr() =
        when (type) {
            InsightType.BUDGET_CLOSE_NEGATIVE,
            InsightType.BUDGET_OVERSPENT -> R.attr.tink_warningColor
            else -> R.attr.tink_expensesColor
        }
}
