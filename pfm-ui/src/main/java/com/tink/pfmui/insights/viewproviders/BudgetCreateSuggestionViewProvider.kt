package com.tink.pfmui.insights.viewproviders

import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import com.tink.pfmui.R
import com.tink.pfmui.insights.actionhandling.ActionHandler
import kotlinx.android.synthetic.main.item_insight_budget_create_suggestion.view.*
import se.tink.android.annotations.ContributesInsightViewProvider
import se.tink.commons.extensions.inflate
import se.tink.core.models.insights.Insight
import se.tink.core.models.insights.InsightAction
import se.tink.core.models.insights.InsightState
import se.tink.core.models.insights.InsightType
import se.tink.insights.InsightViewType
import se.tink.insights.getViewType
import javax.inject.Inject

@ContributesInsightViewProvider
class BudgetCreateSuggestionViewProvider @Inject constructor() : InsightViewProvider {

    data class BudgetCreateSuggestionViewDataHolder(
        val title: String,
        val description: String,
        @DrawableRes val categoryIcon: Int,
        val currentSpendingAmount: String,
        val currentSpendingLabel: String,
        val newReducedAmount: String,
        val newBudgetLabel: String,
        val savePercentage: String,
        val state: InsightState,
        val actions: List<InsightAction>
    ) : InsightDataHolder

    override val viewType: InsightViewType = getViewType()
    override val supportedInsightTypes: List<InsightType> = listOf(
        // TODO: Add the actual Budget insight type when it is added
    )

    override fun viewHolder(parent: ViewGroup, actionHandler: ActionHandler): InsightViewHolder =
        BudgetCreateSuggestionViewHolder(parent, actionHandler)

    // TODO: Replace mock data with real data from backend
    override fun getDataHolder(insight: Insight): InsightDataHolder =
        BudgetCreateSuggestionViewProvider.BudgetCreateSuggestionViewDataHolder(
            title = "Spendera 10% mindre på restaurang, spara 3 840 kr per år!",
            description = "Vi hjälper dig hela vägen med en budget. Du får notiser om du börjar spendera för mycket.",
            categoryIcon = insight.getIcon(),
            currentSpendingAmount = "3 200 kr",
            currentSpendingLabel = "Ditt snitt per månad",
            newReducedAmount = "2 880 kr",
            newBudgetLabel = "Ny budget",
            savePercentage = "-10%",
            state = insight.state,
            actions = insight.actions
        )

    class BudgetCreateSuggestionViewHolder(
        parent: ViewGroup,
        actionHandler: ActionHandler
    ) : InsightViewHolder(
        parent.inflate(R.layout.item_insight_budget_create_suggestion),
        actionHandler
    ), InsightCommonBottomPart {
        override val view: View = itemView

        override fun bind(data: InsightDataHolder, insight: Insight) {
            require(data is BudgetCreateSuggestionViewDataHolder)

            setupCommonBottomPart(insight)

            view.apply {
                insightBubbleCurrentSpending.setPrimaryText(data.currentSpendingAmount)
                insightBubbleCurrentSpending.setSecondaryText(data.currentSpendingLabel)
                insightBubbleCurrentSpending.setIcon(data.categoryIcon)

                insightBubbleNewCategory.setPrimaryText(data.newReducedAmount)
                insightBubbleNewCategory.setSecondaryText(data.newBudgetLabel)
                insightBubbleNewCategory.setIcon(data.categoryIcon)

                insightBubbleSavePercentage.setPrimaryText(data.savePercentage)
            }
        }
    }

    // TODO: Update once the data from backend
    private fun Insight.getIcon() = R.drawable.ic_foodanddrinks
}