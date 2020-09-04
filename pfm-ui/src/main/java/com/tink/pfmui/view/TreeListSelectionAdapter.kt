package com.tink.pfmui.view

import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.tink.pfmui.R
import se.tink.commons.extensions.inflate
import se.tink.commons.extensions.tint
import se.tink.commons.extensions.visible
import se.tink.android.viewholders.ClickableViewHolder
import se.tink.android.viewholders.OnViewHolderClickedListener
import se.tink.commons.extensions.getColorFromAttr
import se.tink.commons.extensions.setImageResFromAttr

internal class TreeListSelectionAdapter : RecyclerView.Adapter<TreeListSelectionViewHolder>(),
    OnViewHolderClickedListener {

    private var flatData: MutableList<TreeListSelectionItem> = mutableListOf()

    var selectedItem: TreeListSelectionItem? = null
        private set(value) {
            field?.isSelected = false
            value?.isSelected = true
            field = value
            onItemSelectedListener?.invoke(value)
        }

    var onItemSelectedListener: ((TreeListSelectionItem?) -> Unit)? = null

    private var expandedItem: TreeListSelectionItem.TopLevelItem? = null
        set(value) {
            field?.isExpanded = false // Update expanded flag before updating reference.

            flatData.removeAll { it is TreeListSelectionItem.ChildItem }
            if (value?.children?.isNotEmpty() == true) {
                value.isExpanded = true
                flatData.addAll(flatData.indexOf(value) + 1, value.children)
            }
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TreeListSelectionViewHolder =
        when (TreeListSelectionItem.ViewType.fromIntVal(viewType)) {
            TreeListSelectionItem.ViewType.TOP_LEVEL -> TopLevelViewHolder(
                parent,
                this
            )
            TreeListSelectionItem.ViewType.CHILD_LEVEL -> ChildItemViewHolder(
                parent,
                this
            )
            TreeListSelectionItem.ViewType.ACTION_ITEM -> ActionItemViewHolder(
                parent,
                this
            )
        }

    override fun onBindViewHolder(holder: TreeListSelectionViewHolder, position: Int) {
        holder.bind(flatData[position])
    }

    override fun onBindViewHolder(
        holder: TreeListSelectionViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            // Partially update the view according to payloads to avoid flickering
            for (payload in payloads) {
                (payload as? TreeListDiffCallback.DiffPayload)?.let {
                    it.isExpanded?.let { value ->
                        (holder as? TopLevelViewHolder)?.setIsExpanded(value)
                    }
                    it.isSelected?.let { value -> holder.setIsSelected(value) }
                }
            }
        }
    }

    override fun getItemCount(): Int = flatData.size
    override fun getItemViewType(position: Int): Int = flatData[position].getViewType().intVal

    override fun onItemClicked(position: Int) {
        val oldFlatData = flatData.map { it.deepCopy() }
        val clickedItem = flatData[position]

        if (clickedItem is TreeListSelectionItem.ActionItem) {
            clickedItem.action.invoke()
            expandedItem = null
            selectedItem = null
        } else {
            if (clickedItem is TreeListSelectionItem.TopLevelItem) {
                expandedItem = clickedItem.takeIf { it != expandedItem }
            }
            selectedItem = clickedItem
        }
        calculateDiff(oldFlatData, flatData).dispatchUpdatesTo(this)
    }

    fun setData(data: List<TreeListSelectionItem>, selectedFilter: TreeListSelectionItem? = null) {
        val oldFlatData = flatData.map { it.deepCopy() }
        flatData = data.toMutableList()

        if (selectedFilter != null) {
            // We have a pre-selection to make together with the new data. Discard our old selection.
            expandedItem =
                    if (selectedFilter is TreeListSelectionItem.ChildItem) {
                        // We need to make sure that the correct top level item is expanded first.
                        flatData.find {
                            (it is TreeListSelectionItem.TopLevelItem)
                                    && it.children.any { child -> child.isSameItem(selectedFilter) }
                        }
                    } else {
                        findItem(selectedFilter, flatData)
                    } as TreeListSelectionItem.TopLevelItem
            selectedItem = findItem(selectedFilter, flatData)
        } else {
            // Expand the item in the new list, if any exists.
            // Important that this is done before the selectedItem update, or that item won't be in flatData.
            expandedItem = findItem(expandedItem, flatData)
            // Select the item in the new list, if any exists.
            selectedItem = findItem(selectedItem, flatData)
        }

        calculateDiff(oldFlatData, flatData).dispatchUpdatesTo(this)
    }

    private inline fun <reified T : TreeListSelectionItem> findItem(
        item: T?,
        list: List<TreeListSelectionItem>
    ): T? = item?.let { list.find { other -> other.isSameItem(it) } } as? T

    private fun calculateDiff(old: List<TreeListSelectionItem>, new: List<TreeListSelectionItem>) =
        DiffUtil.calculateDiff(
            TreeListDiffCallback(
                old,
                new
            )
        )

    private class TreeListDiffCallback(
        val old: List<TreeListSelectionItem>,
        val new: List<TreeListSelectionItem>
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

            val diff =
                DiffPayload()
            if (oldItem.isSelected != newItem.isSelected) {
                diff.isSelected = newItem.isSelected
            }
            if (oldItem is TreeListSelectionItem.TopLevelItem
                && newItem is TreeListSelectionItem.TopLevelItem
                && oldItem.isExpanded != newItem.isExpanded
            ) {
                diff.isExpanded = newItem.isExpanded
            }
            return diff.takeIf { it.hasPayload() }
        }

        class DiffPayload {
            var isSelected: Boolean? = null
            var isExpanded: Boolean? = null

            fun hasPayload(): Boolean = isSelected != null || isExpanded != null
        }
    }
}

internal sealed class TreeListSelectionItem {
    var isSelected = false
    abstract val id: String
    abstract val label: String
    open val amount: String? = null

    abstract fun deepCopy(): TreeListSelectionItem
    fun isSameItem(other: TreeListSelectionItem): Boolean = id == other.id

    open fun isSameContents(other: TreeListSelectionItem): Boolean {
        return id == other.id
                && isSelected == other.isSelected
                && label == other.label
                && amount == other.amount
    }

    fun getViewType(): ViewType =
        when (this) {
            is TopLevelItem -> ViewType.TOP_LEVEL
            is ChildItem -> ViewType.CHILD_LEVEL
            is ActionItem -> ViewType.ACTION_ITEM
        }

    enum class ViewType(val intVal: Int) {
        TOP_LEVEL(0), CHILD_LEVEL(1), ACTION_ITEM(2);

        companion object {
            fun fromIntVal(intVal: Int) = when (intVal) {
                TOP_LEVEL.intVal -> TOP_LEVEL
                CHILD_LEVEL.intVal -> CHILD_LEVEL
                ACTION_ITEM.intVal -> ACTION_ITEM
                else -> throw java.lang.IllegalStateException("Invalid view type: $intVal")
            }
        }
    }

    data class TopLevelItem(
        override val id: String,
        override val label: String,
        val iconRes: Int,
        val iconColor: Int,
        val iconBackgroundColor: Int,
        val children: List<TreeListSelectionItem>,
        override val amount: String? = null
    ) : TreeListSelectionItem() {
        var isExpanded = false
        override fun deepCopy(): TreeListSelectionItem {
            return copy().also {
                it.isSelected = isSelected
                it.isExpanded = isExpanded
            }
        }

        override fun isSameContents(other: TreeListSelectionItem): Boolean {
            return super.isSameContents(other) && isExpanded == (other as? TopLevelItem)?.isExpanded
        }
    }

    data class ChildItem(
        override val id: String,
        override val label: String,
        override val amount: String? = null
    ) : TreeListSelectionItem() {
        override fun deepCopy(): TreeListSelectionItem {
            return copy().also {
                it.isSelected = isSelected
            }
        }
    }

    data class ActionItem(
        override val id: String,
        override val label: String,
        val iconRes: Int,
        val action: () -> Unit
    ) : TreeListSelectionItem() {
        override fun deepCopy(): TreeListSelectionItem {
            return copy()
        }

    }
}

internal abstract class TreeListSelectionViewHolder(
    itemView: View,
    onViewHolderClickedListener: OnViewHolderClickedListener
) : ClickableViewHolder(itemView, onViewHolderClickedListener) {
    val label: TextView = itemView.findViewById(R.id.label)
    val checkbox: CheckBox = itemView.findViewById(R.id.checkbox)
    val amount: TextView = itemView.findViewById(R.id.amount)

    @CallSuper
    open fun bind(item: TreeListSelectionItem) {
        label.text = item.label
        setIsSelected(item.isSelected)
    }

    fun setIsSelected(checked: Boolean) {
        checkbox.isChecked = checked
    }
}

private class TopLevelViewHolder(
    parent: ViewGroup,
    onViewHolderClickedListener: OnViewHolderClickedListener
) : TreeListSelectionViewHolder(
    parent.inflate(R.layout.item_tree_list_selection_top_level),
    onViewHolderClickedListener
) {
    val icon: ShapeableImageView = itemView.findViewById(R.id.icon)
    val divider: View = itemView.findViewById(R.id.divider)

    override fun bind(item: TreeListSelectionItem) {
        super.bind(item)
        val categoryItem = (item as? TreeListSelectionItem.TopLevelItem)
            ?: throw IllegalStateException("Attempted to use TopLevelItemViewHolder for another item.")

        icon.setImageResFromAttr(categoryItem.iconRes)
        icon.tint(categoryItem.iconColor)
        icon.setBackgroundColor(itemView.context.getColorFromAttr(categoryItem.iconBackgroundColor))
        setIsExpanded(categoryItem.isExpanded)
    }

    fun setIsExpanded(isExpanded: Boolean) {
        divider.visible = !isExpanded
    }
}

private class ChildItemViewHolder(
    parent: ViewGroup,
    onViewHolderClickedListener: OnViewHolderClickedListener
) : TreeListSelectionViewHolder(
    parent.inflate(R.layout.item_tree_list_selection_child),
    onViewHolderClickedListener
)

private class ActionItemViewHolder(
    parent: ViewGroup,
    onViewHolderClickedListener: OnViewHolderClickedListener
) : TreeListSelectionViewHolder(
    parent.inflate(R.layout.item_tree_list_selection_top_level),
    onViewHolderClickedListener
) {
    val icon: ShapeableImageView = itemView.findViewById(R.id.icon)

    override fun bind(item: TreeListSelectionItem) {
        super.bind(item)
        val actionItem = (item as? TreeListSelectionItem.ActionItem)
            ?: throw IllegalStateException("Attempted to use ActionItemViewHolder for another item.")

        icon.setImageResFromAttr(actionItem.iconRes)
    }
}