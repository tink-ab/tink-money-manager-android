package com.tink.pfmui.insights.viewproviders

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.ColorUtils
import com.tink.pfmui.R
import com.tink.pfmui.insights.actionhandling.ActionHandler
import kotlinx.android.synthetic.main.tink_item_insight_image_text.view.*
import se.tink.android.annotations.ContributesInsightViewProvider
import se.tink.commons.extensions.getColorFromAttr
import se.tink.commons.extensions.inflate
import se.tink.core.models.insights.Insight
import se.tink.core.models.insights.InsightType
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
        view.image.setImageResource(insight.image)
        view.image.setBackgroundColor(insight.backgroundColor(view.context))
        setupCommonBottomPart(insight)
    }
}

private val Insight.image
    get() =
        when (type) {
            InsightType.WEEKLY_UNCATEGORIZED_TRANSACTIONS -> R.drawable.tink_items_on_shelf_illustration
            else -> 0
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
