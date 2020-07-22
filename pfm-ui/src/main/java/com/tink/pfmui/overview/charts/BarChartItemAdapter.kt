package com.tink.pfmui.overview.charts

import android.content.res.ColorStateList
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.tink.pfmui.R
import kotlinx.android.synthetic.main.view_bar_chart_item.view.*
import se.tink.commons.extensions.getColorFromAttr
import se.tink.commons.extensions.inflate

class BarChartItemAdapter : RecyclerView.Adapter<BarChartItemViewHolder>() {

    var items: List<BarChartItem> = listOf()

    var averageHeightFactor: Float = 0f

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarChartItemViewHolder =
        BarChartItemViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BarChartItemViewHolder, position: Int) {
        holder.bind(items[position], averageHeightFactor)
    }
}

class BarChartItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    parent.inflate(R.layout.view_bar_chart_item)
) {
    fun bind(item: BarChartItem, averageHeightFactor: Float) {
        with(itemView) {

            bar.setWeightFactor(item.barHeightFactor)
            spacer.setWeightFactor(1 - item.barHeightFactor)
            averageLine.setWeightFactor(averageHeightFactor)
            averageSpacer.setWeightFactor(1 - averageHeightFactor)

            bar.backgroundTintList = ColorStateList.valueOf(
                context.getColorFromAttr(item.barColor)
            )

            periodLabel.text = item.periodLabel ?: ""
            amountLabel.text = item.amountLabel ?: ""
        }
    }

    private fun View.setWeightFactor(factor: Float) {
        (layoutParams as? ConstraintLayout.LayoutParams)?.let {
            it.verticalWeight = factor
            layoutParams = it
        }
    }
}
