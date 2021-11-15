package com.tink.moneymanagerui.insights.viewproviders

import android.view.View
import android.view.ViewGroup
import androidx.annotation.AttrRes
import com.tink.model.insights.Insight
import com.tink.model.insights.InsightAction
import com.tink.model.insights.InsightState
import com.tink.model.insights.InsightType
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.insights.actionhandling.ActionHandler
import kotlinx.android.synthetic.main.tink_item_insight_icon_text.view.*
import se.tink.android.annotations.ContributesInsightViewProvider
import se.tink.commons.categories.enums.CategoryType
import se.tink.commons.categories.iconFromCategoryCode
import se.tink.commons.extensions.backgroundTint
import se.tink.commons.extensions.getColorFromAttr
import se.tink.commons.extensions.inflate
import se.tink.commons.extensions.setIconRes
import se.tink.commons.extensions.tint
import se.tink.commons.icons.IconResource
import se.tink.insights.InsightViewType
import se.tink.insights.getViewType
import javax.inject.Inject

@ContributesInsightViewProvider
class IconTextViewProvider @Inject constructor() : InsightViewProvider {

    data class IconTextViewDataHolder(
        val title: String,
        val description: String,
        val icon: IconResource,
        @AttrRes val colorAttr: Int,
        val state: InsightState,
        val actions: List<InsightAction>,
        val isCritical: Boolean = false
    ) : InsightDataHolder

    override val viewType: InsightViewType = getViewType()
    override val supportedInsightTypes: List<InsightType> = listOf(
        InsightType.ACCOUNT_BALANCE_LOW,
        InsightType.LARGE_EXPENSE,
        InsightType.DOUBLE_CHARGE,
        InsightType.BUDGET_SUGGEST_CREATE_FIRST
    )

    override fun viewHolder(parent: ViewGroup, actionHandler: ActionHandler): InsightViewHolder =
        IconTextViewHolder(parent, actionHandler)

    override fun getDataHolder(insight: Insight): InsightDataHolder =
        IconTextViewDataHolder(
            title = insight.title,
            description = insight.description,
            icon = insight.getIcon(),
            colorAttr = insight.getColorAttr(),
            state = insight.state,
            actions = insight.actions,
            isCritical = insight.isCritical()
        )

    class IconTextViewHolder(
        parent: ViewGroup,
        actionHandler: ActionHandler
    ) : InsightViewHolder(parent.inflate(R.layout.tink_item_insight_icon_text), actionHandler),
        InsightCommonBottomPart {
        override val view: View = itemView

        override fun bind(data: InsightDataHolder, insight: Insight) {
            require(data is IconTextViewDataHolder)

            setupCommonBottomPart(insight)

            view.apply {
                icon.setIconRes(data.icon)
                icon.tint(data.colorAttr)
                iconBackground.backgroundTint(data.colorAttr)
                title.setTextColor(
                    context.getColorFromAttr(
                        if (data.isCritical) R.attr.tink_criticalColor else R.attr.tink_textColorPrimary
                    )
                )
            }
        }
    }

    private fun Insight.getIcon() = when (type) {
        InsightType.DOUBLE_CHARGE -> IconResource.DrawableId(R.drawable.tink_double_charge)
        InsightType.LARGE_EXPENSE -> IconResource.DrawableId(R.drawable.tink_category_all_expenses)
        InsightType.ACCOUNT_BALANCE_LOW -> IconResource.DrawableId(R.drawable.tink_alert)
        InsightType.BUDGET_SUGGEST_CREATE_FIRST ->
            IconResource.Attribute(iconFromCategoryCode(CategoryType.EXPENSES.stringCode))
        InsightType.WEEKLY_UNCATEGORIZED_TRANSACTIONS -> IconResource.DrawableId(R.drawable.tink_uncategorized)
        else -> IconResource.Attribute(R.attr.tink_icon_category_uncategorized)
    }

    private fun Insight.getColorAttr() = when (type) {
        InsightType.DOUBLE_CHARGE,
        InsightType.LARGE_EXPENSE,
        InsightType.ACCOUNT_BALANCE_LOW -> R.attr.tink_criticalColor
        InsightType.BUDGET_SUGGEST_CREATE_FIRST -> R.attr.tink_expensesColor
        InsightType.WEEKLY_UNCATEGORIZED_TRANSACTIONS -> R.attr.tink_uncategorizedColor
        else -> R.attr.tink_textColorSecondary
    }

    private fun Insight.isCritical() = when (type) {
        InsightType.DOUBLE_CHARGE,
        InsightType.LARGE_EXPENSE -> true
        else -> false
    }
}
