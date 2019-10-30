package se.tink.commons.transactions

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.transaction_item.view.*
import kotlinx.android.synthetic.main.transaction_item_date_group.view.*
import kotlinx.android.synthetic.main.transaction_item_date_group_upcoming.view.*
import org.joda.time.DateTime
import se.tink.android.viewholders.ClickableViewHolder
import se.tink.android.viewholders.OnViewHolderClickedListener
import se.tink.commons.R
import se.tink.commons.extensions.backgroundTint
import se.tink.commons.extensions.inflate
import se.tink.commons.extensions.tint
import se.tink.commons.extensions.visible
import se.tink.utils.DateUtils
import kotlin.properties.Delegates

class TransactionItemListAdapter(
    private val dateUtils: DateUtils? = null,
    private val groupByDates: Boolean = true
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    OnViewHolderClickedListener {

    private var flatData: List<ListItem> = emptyList()

    var onTransactionItemClickedListener: ((String) -> Unit)? = null

    var onUpcomingTransactionClickedListener: ((ListItem.TransactionItem.UpcomingTransactionData) -> Unit)? =
        null

    var onToggleUpcomingTransactions: (() -> Unit)? = null

    private var transactionItems: List<ListItem.TransactionItem> = emptyList()
    private var upcomingTransactionItems: List<ListItem.TransactionItem>? = null
    private var upcomingTransactionsHeader: ListItem.UpcomingGroupHeaderItem? = null

    var expanded: Boolean by Delegates.observable(false) { _, _, _ -> update() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (ListItem.ViewType.fromIntVal(viewType)) {
            ListItem.ViewType.DATE_GROUP -> DateGroupViewHolder(parent)

            ListItem.ViewType.UPCOMING_DATE_GROUP ->
                UpcomingHeaderViewHolder(parent, onToggleUpcomingTransactions)

            ListItem.ViewType.TRANSACTION_ITEM -> TransactionItemViewHolder(parent, this)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = flatData[position]) {
            is ListItem.DateGroupItem -> (holder as DateGroupViewHolder).bind(item)
            is ListItem.UpcomingGroupHeaderItem -> (holder as UpcomingHeaderViewHolder).bind(item)
            is ListItem.TransactionItem -> (holder as TransactionItemViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int = flatData.size

    override fun getItemViewType(position: Int): Int = flatData[position].getViewType().intVal

    override fun onItemClicked(position: Int) {
        val item = flatData[position]
        if (item is ListItem.TransactionItem) {
            item.upcomingTransactionData?.also {
                onUpcomingTransactionClickedListener?.invoke(it)
            } ?: onTransactionItemClickedListener?.invoke(item.id)
        }
    }

    fun setTransactionItems(
        transactionItems: List<ListItem.TransactionItem>,
        upcomingTransactionItems: List<ListItem.TransactionItem>? = null,
        upcomingTransactionsHeader: ListItem.UpcomingGroupHeaderItem? = null
    ) {
        this.transactionItems = transactionItems
        this.upcomingTransactionItems = upcomingTransactionItems
        this.upcomingTransactionsHeader = upcomingTransactionsHeader
        update()
    }

    private fun update() {
        val oldData = flatData.toList()
        val list = mutableListOf<ListItem>()

        upcomingTransactionsHeader?.let { list += it.copy(expanded = expanded) }

        upcomingTransactionItems
            ?.takeIf { expanded }
            ?.sortedByDescending { it.date }
            ?.let { list += it }

        transactionItems
            .sortedByDescending { it.date }
            .groupBy { it.date }
            .forEach { entry ->
                if (groupByDates && dateUtils != null) {
                    list += ListItem.DateGroupItem(dateUtils.formatDateHuman(entry.key))
                }
                list += entry.value
            }

        flatData = list
        calculateDiff(oldData, flatData).dispatchUpdatesTo(this)
    }


    private fun calculateDiff(old: List<ListItem>, new: List<ListItem>) =
        DiffUtil.calculateDiff(TransactionItemDiffCallback(old, new))

    private class TransactionItemDiffCallback(
        val old: List<ListItem>,
        val new: List<ListItem>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = old.size
        override fun getNewListSize(): Int = new.size

        override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean =
            old[oldPos].isSameItem(new[newPos])

        override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean =
            old[oldPos].isContentTheSame(new[newPos])
    }
}

sealed class ListItem {
    abstract fun isSameItem(other: ListItem): Boolean

    open fun isContentTheSame(other: ListItem): Boolean = this == other

    abstract fun getViewType(): ViewType

    enum class ViewType(val intVal: Int) {
        DATE_GROUP(0), TRANSACTION_ITEM(1), UPCOMING_DATE_GROUP(2);

        companion object {
            fun fromIntVal(intVal: Int) = when (intVal) {
                DATE_GROUP.intVal -> DATE_GROUP
                TRANSACTION_ITEM.intVal -> TRANSACTION_ITEM
                UPCOMING_DATE_GROUP.intVal -> UPCOMING_DATE_GROUP
                else -> throw java.lang.IllegalStateException("Invalid view type: $intVal")
            }
        }
    }

    data class TransactionItem(
        val id: String,
        val icon: Icon,
        val label: String,
        val description: String,
        val amount: String,
        val dispensableAmount: String,
        val date: DateTime,
        val merchantLogoAllowed: Boolean,
        val recurring: Boolean = false,
        val upcomingTransactionData: UpcomingTransactionData? = null
    ) : ListItem() {
        override fun isSameItem(other: ListItem): Boolean {
            return id == (other as? TransactionItem)?.id
        }

        override fun getViewType(): ViewType = ViewType.TRANSACTION_ITEM

        data class Icon(val resource: Int, val color: Int, val backgroundColor: Int)
        data class UpcomingTransactionData(
            val pending: Boolean,
            val editable: Boolean,
            val transferId: String
        )
    }

    data class DateGroupItem(
        val date: String
    ) : ListItem() {
        override fun isSameItem(other: ListItem): Boolean {
            return date == (other as? DateGroupItem)?.date
        }

        override fun getViewType(): ViewType = ViewType.DATE_GROUP
    }

    data class UpcomingGroupHeaderItem(
        val title: String,
        val expanded: Boolean = false
    ) : ListItem() {

        override fun isSameItem(other: ListItem): Boolean = other is UpcomingGroupHeaderItem

        override fun isContentTheSame(other: ListItem): Boolean = this == other

        override fun getViewType(): ViewType = ViewType.UPCOMING_DATE_GROUP
    }
}

class DateGroupViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    parent.inflate(R.layout.transaction_item_date_group)
) {
    fun bind(item: ListItem.DateGroupItem) {
        itemView.date.text = item.date
    }
}

class UpcomingHeaderViewHolder(
    val parent: ViewGroup,
    private val onToggleUpcomingTransactions: (() -> Unit)?
) : RecyclerView.ViewHolder(
    parent.inflate(R.layout.transaction_item_date_group_upcoming)
) {
    fun bind(item: ListItem.UpcomingGroupHeaderItem) {
        itemView.upcomingGroupTitle.text = item.title
        itemView.showTransactionsButton.setOnClickListener {
            onToggleUpcomingTransactions?.invoke()
        }
        itemView.showTransactionsButton.text =
            if (item.expanded) {
                R.string.upcoming_transaction_hide
            } else {
                R.string.upcoming_transaction_show
            }.let {
                parent.context?.getString(it) ?: ""
            }
    }
}

class TransactionItemViewHolder(
    parent: ViewGroup,
    onViewHolderClickedListener: OnViewHolderClickedListener
) : ClickableViewHolder(
    parent.inflate(R.layout.transaction_item),
    onViewHolderClickedListener
) {
    fun bind(item: ListItem.TransactionItem) {
        with(itemView) {

            val (iconRes, iconColor, iconBackgroundColor) = item.icon
            icon.setImageResource(iconRes)
            icon.tint(iconColor)
            iconBackground.setImageResource(0)
            iconBackground.backgroundTint(iconBackgroundColor)

            label.text = item.label
            description.text = item.description
            amount.text = item.amount
            dispensableAmount.text = item.dispensableAmount
            dispensableAmount.visible = item.dispensableAmount != item.amount
        }
    }
}
