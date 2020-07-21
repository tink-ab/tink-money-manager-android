package com.tink.pfmui.overview.charts

import android.content.res.ColorStateList
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tink.pfmui.R
import com.tink.pfmui.charts.models.PeriodBalance
import com.tink.pfmui.util.CurrencyUtils
import kotlinx.android.synthetic.main.view_period_balance_bar_chart_item.view.*
import org.joda.time.DateTime
import se.tink.commons.extensions.inflate
import se.tink.core.models.misc.Period
import kotlin.random.Random

class PeriodBalanceBarChartAdapter : RecyclerView.Adapter<BarChartItemViewHolder>() {

    //TODO: Fill with real data
    var items: List<PeriodBalance> = listOf()

    private val max: Double
        get() = items.map { it.amount }.max() ?: 0.0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarChartItemViewHolder =
        BarChartItemViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BarChartItemViewHolder, position: Int) {
        holder.bind(items[position], max)
    }
}

class BarChartItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    parent.inflate(R.layout.view_period_balance_bar_chart_item)
) {
    fun bind(periodBalance: PeriodBalance, maxAmount: Double) {

        val factor = (periodBalance.amount / maxAmount).toFloat()

        with(itemView) {
            (bar.layoutParams as? ConstraintLayout.LayoutParams)?.let {
                it.verticalWeight = factor
                bar.layoutParams = it
            }

            (spacer.layoutParams as? ConstraintLayout.LayoutParams)?.let {
                it.verticalWeight = 1 - factor
                spacer.layoutParams = it
            }

            bar.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(context, R.color.expensive_blue) // TODO: Color configuration
            )

            periodLabel.text = periodBalance.period?.toMonthString() //TODO: Period formatting
            amountLabel.text =
                CurrencyUtils.formatAmountExactWithCurrencySymbol(periodBalance.amount)
        }
    }
}