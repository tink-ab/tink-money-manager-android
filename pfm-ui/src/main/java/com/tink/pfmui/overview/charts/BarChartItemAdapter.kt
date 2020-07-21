package com.tink.pfmui.overview.charts

import android.content.res.ColorStateList
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.tink.pfmui.R
import kotlinx.android.synthetic.main.view_bar_chart_item.view.*
import se.tink.commons.extensions.getColorFromAttr
import se.tink.commons.extensions.inflate

class BarChartItemAdapter : RecyclerView.Adapter<BarChartItemViewHolder>() {

    @ColorInt
    var barColor: Int = 0

    var items: List<BarChartItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarChartItemViewHolder =
        BarChartItemViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BarChartItemViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

class BarChartItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    parent.inflate(R.layout.view_bar_chart_item)
) {
    fun bind(item: BarChartItem) {

        with(itemView) {
            (bar.layoutParams as? ConstraintLayout.LayoutParams)?.let {
                it.verticalWeight = item.barHeightFactor
                bar.layoutParams = it
            }

            (spacer.layoutParams as? ConstraintLayout.LayoutParams)?.let {
                it.verticalWeight = 1 - item.barHeightFactor
                spacer.layoutParams = it
            }

            bar.backgroundTintList = ColorStateList.valueOf(
                context.getColorFromAttr(item.barColor)
            )

            periodLabel.text = item.periodLabel ?: ""
            amountLabel.text = item.amountLabel ?: ""
        }
    }
}