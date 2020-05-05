package se.tink.commons.transactions

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.similar_transaction_row.view.*
import se.tink.android.viewholders.ClickableViewHolder
import se.tink.android.viewholders.OnViewHolderClickedListener
import se.tink.commons.R
import se.tink.commons.extensions.backgroundTint
import se.tink.commons.extensions.getDrawableResIdFromAttr
import se.tink.commons.extensions.inflate
import se.tink.commons.extensions.setImageResFromAttr
import se.tink.commons.extensions.tint

typealias Marked = SimilarTransactionsAdapter.Marked

class SimilarTransactionsAdapter :
    RecyclerView.Adapter<SimilarTransactionsAdapter.SimilarTransactionViewHolder>(),
    OnViewHolderClickedListener {

    private var items: List<SimilarTransactionItem> = emptyList()

    val selectedTransactions: List<String>
        get() = items.filter { it.selected }.map { it.id }

    var onMarkedStateChangeListener: ((Marked) -> Unit)? = null

    var markedTransactionsChangedListener: ((List<String>) -> Unit)? = null

    enum class Marked {
        ALL,
        PARTIALLY,
        NONE
    }

    private var marked: Marked = Marked.ALL
        set(value) {
            field = value
            onMarkedStateChangeListener?.invoke(marked)
        }

    private fun updatedMarkedState(): Marked =
        when (marked) {
            Marked.PARTIALLY,
            Marked.ALL -> Marked.NONE
            Marked.NONE -> Marked.ALL
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SimilarTransactionViewHolder =
        SimilarTransactionViewHolder(parent, this)

    override fun onBindViewHolder(holder: SimilarTransactionViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onBindViewHolder(
        holder: SimilarTransactionViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            // Partially update the view according to payloads to avoid flickering
            for (payload in payloads) {
                (payload as? DiffCallback.DiffPayload)?.let {
                    it.isSelected?.let { value -> holder.setIsSelected(value) }
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun toggleMarked() {
        marked = updatedMarkedState()
        val oldItems = items.map { it.copy() }
        items.onEach { it.selected = (marked == Marked.ALL || marked == Marked.PARTIALLY) }
        calculateDiff(oldItems, items).dispatchUpdatesTo(this)
        markedTransactionsChangedListener?.invoke(selectedTransactions)
    }

    fun setData(data: List<SimilarTransactionItem>) {
        val oldItems = items.map { it.copy() }
        items = data
        calculateDiff(oldItems, items).dispatchUpdatesTo(this)
    }

    private fun calculateDiff(
        old: List<SimilarTransactionItem>,
        new: List<SimilarTransactionItem>
    ) =
        DiffUtil.calculateDiff(DiffCallback(old, new))

    private class DiffCallback(
        val old: List<SimilarTransactionItem>,
        val new: List<SimilarTransactionItem>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = old.size
        override fun getNewListSize(): Int = new.size

        override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean =
            old[oldPos].isSameItem(new[newPos])

        override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean =
            old[oldPos].isSameContents(new[newPos])

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            val oldItem = old[oldItemPosition]
            val newItem = new[newItemPosition]

            val diff = DiffPayload()
            if (oldItem.selected != newItem.selected) {
                diff.isSelected = newItem.selected
            }
            return diff.takeIf { it.hasPayload() }
        }

        class DiffPayload {
            var isSelected: Boolean? = null

            fun hasPayload(): Boolean = isSelected != null
        }
    }

    override fun onItemClicked(position: Int) {
        val clickedItem = items[position]
        clickedItem.selected = !clickedItem.selected

        marked = items
            .find { it.selected == !clickedItem.selected }
            ?.let { Marked.PARTIALLY }
            ?: if (clickedItem.selected) {
                Marked.ALL
            } else {
                Marked.NONE
            }
        notifyItemChanged(
            position,
            DiffCallback.DiffPayload().apply { isSelected = clickedItem.selected }
        )
        markedTransactionsChangedListener?.invoke(selectedTransactions)
    }

    data class SimilarTransactionItem(
        val id: String,
        val icon: ListItem.TransactionItem.Icon,
        val label: String,
        val amount: String,
        val description: String,
        val date: String,
        val merchantLogoAllowed: Boolean,
        val recurring: Boolean = false,
        var selected: Boolean = true
    ) {

        fun isSameItem(other: SimilarTransactionItem): Boolean = id == other.id

        fun isSameContents(other: SimilarTransactionItem): Boolean = this == other
    }

    class SimilarTransactionViewHolder(
        parent: ViewGroup,
        onViewHolderClickedListener: OnViewHolderClickedListener
    ) : ClickableViewHolder(
        parent.inflate(R.layout.similar_transaction_row),
        onViewHolderClickedListener
    ) {

        fun bind(item: SimilarTransactionItem) {
            with(itemView) {

                val (iconRes, iconColor, iconBackgroundColor) = item.icon
                icon.setImageResFromAttr(iconRes)
                icon.tint(iconColor)
                iconBackground.setImageResource(0)
                iconBackground.backgroundTint(iconBackgroundColor)

                label.text = item.label
                description.text = item.description
                amount.text = item.amount
                date.text = item.date
                setIsSelected(item.selected)
            }
        }

        fun setIsSelected(checked: Boolean) {
            itemView.checkbox.isChecked = checked
        }
    }
}