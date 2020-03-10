package com.tink.pfmui.insights.viewproviders

import android.view.View
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.annotation.DrawableRes
import com.tink.pfmui.R
import com.tink.pfmui.insights.actionhandling.ActionHandler
import kotlinx.android.synthetic.main.item_insight_icon_text.view.*
import se.tink.android.annotations.ContributesInsightViewProvider
import se.tink.commons.extensions.backgroundTint
import se.tink.commons.extensions.getColorFromAttr
import se.tink.commons.extensions.inflate
import se.tink.commons.extensions.tint
import se.tink.core.models.insights.Insight
import se.tink.core.models.insights.InsightAction
import se.tink.core.models.insights.InsightState
import se.tink.core.models.insights.InsightType
import se.tink.insights.InsightViewType
import se.tink.insights.getViewType
import javax.inject.Inject

@ContributesInsightViewProvider
class IconTextViewProvider @Inject constructor() : InsightViewProvider {

    data class IconTextViewDataHolder(
        val title: String,
        val description: String,
        @DrawableRes val icon: Int,
        @AttrRes val colorAttr: Int,
        val state: InsightState,
        val actions: List<InsightAction>,
        val isCritical: Boolean = false
    ) : InsightDataHolder

    override val viewType: InsightViewType = getViewType()
    override val supportedInsightTypes: List<InsightType> = listOf(
        InsightType.LEFT_TO_SPEND_LOW,
        InsightType.ACCOUNT_BALANCE_LOW,
        InsightType.GENERIC_FRAUD,
        InsightType.EINVOICE,
        InsightType.RESIDENCE_DO_YOU_OWN_IT,
        InsightType.LARGE_EXPENSE,
        InsightType.DOUBLE_CHARGE
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
    ) : InsightViewHolder(parent.inflate(R.layout.item_insight_icon_text), actionHandler),
        InsightCommonBottomPart {
        override val view: View = itemView

        override fun bind(data: InsightDataHolder, insight: Insight) {
            require(data is IconTextViewDataHolder)

            setupCommonBottomPart(insight)

            view.apply {
                icon.setImageResource(data.icon)
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
        InsightType.DOUBLE_CHARGE -> R.drawable.ic_doubletransaction
        InsightType.LARGE_EXPENSE -> R.drawable.ic_all_expenses
        InsightType.ACCOUNT_BALANCE_LOW -> R.drawable.ic_alert
        else -> R.drawable.ic_uncategorized
    }

    private fun Insight.getColorAttr() = when (type) {
        InsightType.DOUBLE_CHARGE,
        InsightType.LARGE_EXPENSE,
        InsightType.ACCOUNT_BALANCE_LOW -> R.attr.tink_criticalColor
        else -> R.attr.tink_textColorSecondary
    }

    private fun Insight.isCritical() = when (type) {
        InsightType.DOUBLE_CHARGE,
        InsightType.LARGE_EXPENSE -> true
        else -> false
    }
}