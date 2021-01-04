package com.tink.pfmui.insights.viewproviders

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tink.model.insights.Insight
import com.tink.model.insights.InsightState
import com.tink.model.insights.InsightType
import com.tink.pfmui.insights.actionhandling.ActionHandler
import kotlinx.android.synthetic.main.insight_title_description_actions.view.*
import kotlinx.android.synthetic.main.tink_insight_title_description_actions.view.*
import se.tink.core.models.insights.Insight
import se.tink.core.models.insights.InsightState
import se.tink.core.models.insights.InsightType
import se.tink.insights.InsightViewType

private const val PRIMARY_ACTION_INDEX = 0
private const val SECONDARY_ACTION_INDEX = 1

interface InsightViewProvider {
    val supportedInsightTypes: List<InsightType>
    val viewType: InsightViewType
    fun viewHolder(parent: ViewGroup, actionHandler: ActionHandler): InsightViewHolder
    fun getDataHolder(insight: Insight): InsightDataHolder
}

abstract class InsightViewHolder(
    itemView: View,
    val actionHandler: ActionHandler
) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(data: InsightDataHolder, insight: Insight)
}

interface InsightCommonBottomPart {
    val view: View
    val actionHandler: ActionHandler

    val title: TextView get() = view.title
    val description: TextView get() = view.description
    val primaryButton: Button get() = view.primaryButton
    val secondaryButton: Button get() = view.secondaryButton

    fun setupCommonBottomPart(insight: Insight) {

        //TODO: Update when backend brings back the `type` on actions
        fun Button.setupFor(index: Int) {
            val insightAction = insight.actions.elementAtOrNull(index)
            if (insightAction != null) {
                setOnClickListener { actionHandler.handle(insightAction, insight) }
                text = insightAction.label
                visibility = View.VISIBLE
            } else {
                visibility = View.GONE
            }
        }

        title.text = insight.title
        description.text = insight.description

        if (insight.state is InsightState.Active) {
            primaryButton.setupFor(PRIMARY_ACTION_INDEX)
            secondaryButton.setupFor(SECONDARY_ACTION_INDEX)
        } else {
            // Archived insights will not have actions for now
            secondaryButton.visibility = View.GONE
            primaryButton.visibility = View.GONE
        }
    }
}

interface InsightDataHolder