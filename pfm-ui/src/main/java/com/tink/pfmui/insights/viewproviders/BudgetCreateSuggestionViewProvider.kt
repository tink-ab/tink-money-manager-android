package com.tink.pfmui.insights.viewproviders

import android.view.View
import android.view.ViewGroup
import androidx.annotation.AttrRes
import com.tink.model.insights.*
import com.tink.model.misc.Amount
import com.tink.pfmui.R
import com.tink.pfmui.insights.actionhandling.ActionHandler
import com.tink.pfmui.insights.enrichment.BudgetCreateSuggestionViewDetails
import com.tink.pfmui.util.extensions.formatCurrencyRound
import kotlinx.android.synthetic.main.tink_item_insight_budget_create_suggestion.view.*
import kotlinx.android.synthetic.main.tink_item_insight_budget_create_suggestion.view.icon
import kotlinx.android.synthetic.main.tink_item_insight_expenses_by_category.view.*
import kotlinx.android.synthetic.main.tink_item_insight_icon_text.view.*
import se.tink.android.annotations.ContributesInsightViewProvider
import se.tink.commons.categories.iconFromCategoryCode
import se.tink.commons.extensions.inflate
import se.tink.commons.extensions.setIconRes
import se.tink.commons.extensions.setImageResFromAttr
import se.tink.commons.extensions.tint
import se.tink.commons.icons.IconResource
import se.tink.insights.InsightViewType
import se.tink.insights.getViewType
import javax.inject.Inject

@ContributesInsightViewProvider
class BudgetCreateSuggestionViewProvider @Inject constructor() : InsightViewProvider {

    data class BudgetCreateSuggestionViewDataHolder(
        val title: String,
        val description: String,
        val icon: IconResource,
        @AttrRes val colorAttr: Int,
        val savePercentage: String,
        val savePerYearAmount: Amount,
        val state: InsightState,
        val actions: List<InsightAction>
    ) : InsightDataHolder

    override val viewType: InsightViewType = getViewType()
    override val supportedInsightTypes: List<InsightType> = listOf(
        InsightType.BUDGET_SUGGEST_CREATE_TOP_CATEGORY,
        InsightType.BUDGET_SUGGEST_CREATE_TOP_PRIMARY_CATEGORY
    )

    override fun viewHolder(parent: ViewGroup, actionHandler: ActionHandler): InsightViewHolder =
        BudgetCreateSuggestionViewHolder(parent, actionHandler)

    override fun getDataHolder(insight: Insight): InsightDataHolder {
        val viewDetails = insight.viewDetails as BudgetCreateSuggestionViewDetails
        return BudgetCreateSuggestionViewDataHolder(
            title = insight.title,
            description = insight.description,
            icon = insight.data.icon(),
            colorAttr = R.attr.tink_expensesColor,
            savePercentage = viewDetails.savePercentage,
            savePerYearAmount = viewDetails.savePerYearAmount,
            state = insight.state,
            actions = insight.actions
        )
    }

    class BudgetCreateSuggestionViewHolder(
        parent: ViewGroup,
        actionHandler: ActionHandler
    ) : InsightViewHolder(
        parent.inflate(R.layout.tink_item_insight_budget_create_suggestion),
        actionHandler
    ), InsightCommonBottomPart {
        override val view: View = itemView

        override fun bind(data: InsightDataHolder, insight: Insight) {
            require(data is BudgetCreateSuggestionViewDataHolder)

            setupCommonBottomPart(insight)

            view.apply {
                icon.setIconRes(data.icon)
                icon.tint(data.colorAttr)
                insightBubbleSavePercentage.setPrimaryText(data.savePercentage)
                savePerYearText.text =
                    context.getString(
                        R.string.tink_insights_budget_create_suggestion_save_per_year_text,
                        data.savePerYearAmount.formatCurrencyRound() ?: ""
                    )
            }
        }
    }

    private fun InsightData.icon(): IconResource =
        when (this) {
            is InsightData.BudgetSuggestCreateTopCategoryData -> IconResource.Attribute(iconFromCategoryCode(categorySpending.categoryCode))
            is InsightData.BudgetSuggestCreateTopPrimaryCategoryData -> IconResource.Attribute(iconFromCategoryCode(categorySpending.categoryCode))
            else -> IconResource.Attribute(R.attr.tink_icon_category_uncategorized)
        }
}
