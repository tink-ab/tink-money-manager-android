package com.tink.pfmsdk.insights

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tink.pfmsdk.insights.actionhandling.ActionHandler
import com.tink.pfmsdk.insights.viewproviders.InsightViewHolder
import com.tink.pfmsdk.insights.viewproviders.InsightViewProvider
import com.tink.pfmsdk.insights.viewproviders.InsightViewProviderFactory
import se.tink.core.models.insights.Insight
import se.tink.insights.InsightViewType
import javax.inject.Inject

class InsightsAdapter @Inject constructor(
    private val actionHandler: ActionHandler,
    private val viewProviderFactory: InsightViewProviderFactory
) : RecyclerView.Adapter<InsightViewHolder>() {

    private data class ViewInfo(val insight: Insight, val provider: InsightViewProvider)

    private var insightsData: List<ViewInfo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InsightViewHolder {
        return requireNotNull(
            viewProviderFactory.provider(InsightViewType.fromInt(viewType))
        )
            .viewHolder(parent, actionHandler)
    }

    override fun getItemCount(): Int = insightsData.size
    override fun getItemViewType(position: Int): Int =
        insightsData[position].provider.viewType.toInt()

    override fun onBindViewHolder(holder: InsightViewHolder, position: Int) {
        val (insight, provider) = insightsData[position]
        holder.bind(provider.getDataHolder(insight), insight)
    }

    fun setData(insights: List<Insight>) {
        val newItems = insights.mapNotNull { insight ->
            viewProviderFactory.provider(insight.type)?.let { viewProvider ->
                ViewInfo(insight, viewProvider)
            }
        }
        val diffResult = DiffUtil.calculateDiff(
            InsightsDiffUtilCallback(newItems.map { it.insight }),
            true
        )
        insightsData = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    inner class InsightsDiffUtilCallback(private val newItems: List<Insight>) :
        DiffUtil.Callback() {

        private val oldItems = insightsData.map { it.insight }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition].id == newItems[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition] == newItems[newItemPosition]

        override fun getOldListSize(): Int = oldItems.size
        override fun getNewListSize(): Int = newItems.size
    }
}
