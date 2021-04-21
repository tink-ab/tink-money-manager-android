package com.tink.moneymanagerui.overview.budgets

import android.content.Context
import android.content.res.ColorStateList
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tink.model.misc.Amount
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.budgets.creation.specification.EXACT_NUMBER_ZERO
import com.tink.moneymanagerui.util.extensions.formatCurrencyExactIfNotIntegerWithSign
import com.tink.moneymanagerui.util.extensions.formatCurrencyExactIfNotIntegerWithoutSign
import com.tink.moneymanagerui.util.extensions.formatCurrencyRound
import kotlinx.android.synthetic.main.tink_budget_overview_item.view.*
import se.tink.android.viewholders.ClickableViewHolder
import se.tink.android.viewholders.OnViewHolderClickedListener
import se.tink.commons.extensions.*

internal class BudgetOverviewListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    OnViewHolderClickedListener {

    var onItemClickedListener: ((String) -> Unit)? = null

    private var items: List<BudgetOverviewItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        BudgetOverviewItemViewHolder(parent, this)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BudgetOverviewItemViewHolder).bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun onItemClicked(position: Int) {
        onItemClickedListener?.invoke(items[position].budgetId)
    }

    fun setItems(budgetOverviewItems: List<BudgetOverviewItem>) {
        val oldData = items.toList()
        items = budgetOverviewItems
        calculateDiff(oldData, items).dispatchUpdatesTo(this)
    }

    private fun calculateDiff(old: List<BudgetOverviewItem>, new: List<BudgetOverviewItem>) =
        DiffUtil.calculateDiff(BudgetOverviewItemDiffCallback(old, new))

    private class BudgetOverviewItemDiffCallback(
        val old: List<BudgetOverviewItem>,
        val new: List<BudgetOverviewItem>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = old.size
        override fun getNewListSize(): Int = new.size

        override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean =
            old[oldPos].isSameItem(new[newPos])

        override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean =
            old[oldPos].isContentTheSame(new[newPos])
    }
}

data class BudgetOverviewItem(
    val budgetId: String,
    val icon: Icon,
    val name: String,
    val periodLabel: String,
    val progress: Int,
    val progressMax: Int,
    val budgetAmount: Amount,
    val spentAmount: Amount
) {
    fun isSameItem(other: BudgetOverviewItem): Boolean {
        return budgetId == (other as? BudgetOverviewItem)?.budgetId
    }

    fun isContentTheSame(other: BudgetOverviewItem): Boolean = this == other

    data class Icon(@AttrRes val resource: Int, @AttrRes val color: Int, @AttrRes val backgroundColor: Int)
}

private fun BudgetOverviewItem.isOverSpent(): Boolean = spentAmount > budgetAmount

class BudgetOverviewItemViewHolder(
    parent: ViewGroup,
    onViewHolderClickedListener: OnViewHolderClickedListener
) : ClickableViewHolder(
    parent.inflate(R.layout.tink_budget_overview_item),
    onViewHolderClickedListener
) {
    fun bind(item: BudgetOverviewItem) {
        with(itemView) {
            val (iconRes, iconColor, iconBackgroundColor) = item.icon
            icon.setImageResFromAttr(iconRes)
            icon.tint(iconColor)
            icon.setBackgroundColor(itemView.context.getColorFromAttr(iconBackgroundColor))

            name.text = item.name
            budgetDateInterval.text = item.periodLabel
            budgetProgress.apply {
                max = item.progressMax
                progress = if (item.isOverSpent()) {
                    item.progressMax
                } else {
                    item.progress
                }
                progressTintList = if (item.isOverSpent()) {
                    ColorStateList.valueOf(context.getColorFromAttr(R.attr.tink_warningColor))
                } else {
                    ColorStateList.valueOf(context.getColorFromAttr(R.attr.tink_expensesColor))
                }
            }
            budgetProgressText.apply {
                text = composeSpentString(context, item.spentAmount, item.budgetAmount)
                setTextColor(
                    if (item.isOverSpent()) {
                        context.getColorFromAttr(R.attr.tink_warningColor)
                    } else {
                        context.getColorFromAttr(R.attr.tink_expensesColor)
                    }
                )
            }
        }
    }

    private fun composeSpentString(context: Context, spentAmount: Amount, budgetAmount: Amount): String {
        val delta = budgetAmount - spentAmount
        return if (delta.value.isSmallerThan(EXACT_NUMBER_ZERO)) {
            context.getString(R.string.tink_overview_budget_over)
                .format(delta.formatCurrencyExactIfNotIntegerWithoutSign(), budgetAmount.formatCurrencyRound())
        } else {
            context.getString(R.string.tink_overview_budget_left_of)
                .format(delta.formatCurrencyExactIfNotIntegerWithSign(), budgetAmount.formatCurrencyRound())
        }
    }
}
