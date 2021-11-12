package com.tink.moneymanagerui.insights.viewproviders

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.ColorUtils
import com.tink.model.insights.Insight
import com.tink.model.insights.InsightType
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.insights.actionhandling.ActionHandler
import kotlinx.android.synthetic.main.tink_item_insight_image_text.view.*
import se.tink.android.annotations.ContributesInsightViewProvider
import se.tink.commons.extensions.getColorFromAttr
import se.tink.commons.extensions.inflate
import se.tink.insights.getViewType
import javax.inject.Inject

@ContributesInsightViewProvider
class ImageTextViewProvider @Inject constructor() : InsightViewProvider {

    override val supportedInsightTypes = listOf(InsightType.WEEKLY_UNCATEGORIZED_TRANSACTIONS)
    override val viewType = getViewType()
    override fun getDataHolder(insight: Insight) = object : InsightDataHolder {}

    override fun viewHolder(parent: ViewGroup, actionHandler: ActionHandler) =
        ImageTextViewHolder(parent, actionHandler)
}


class ImageTextViewHolder(
    parent: ViewGroup,
    actionHandler: ActionHandler
) : InsightViewHolder(parent.inflate(R.layout.tink_item_insight_image_text), actionHandler),
    InsightCommonBottomPart {
    override val view: View = itemView

    override fun bind(data: InsightDataHolder, insight: Insight) {
        setupCommonBottomPart(insight)
    }
}

private const val ALPHA_10 = 26

private fun Insight.backgroundColor(context: Context) =
    when (type) {
        InsightType.WEEKLY_UNCATEGORIZED_TRANSACTIONS -> {
            ColorUtils.setAlphaComponent(
                context.getColorFromAttr(R.attr.tink_colorPrimary),
                ALPHA_10
            )
        }
        else -> 0
    }
