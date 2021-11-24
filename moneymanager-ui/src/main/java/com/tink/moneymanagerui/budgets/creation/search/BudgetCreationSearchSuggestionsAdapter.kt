package com.tink.moneymanagerui.budgets.creation.search

import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.tink.moneymanagerui.R
import se.tink.commons.extensions.inflate
import se.tink.android.viewholders.ClickableViewHolder
import se.tink.android.viewholders.OnViewHolderClickedListener
import se.tink.commons.extensions.tint

internal class BudgetCreationSearchSuggestionsAdapter : ListAdapter<String, ClickableViewHolder>(SuggestionDiffUtil), OnViewHolderClickedListener {

    var onItemClickedListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClickableViewHolder =
        SuggestionViewHolder(parent, this)

    override fun onBindViewHolder(holder: ClickableViewHolder, position: Int) {
        (holder as SuggestionViewHolder).bind(getItem(position))
    }

    override fun onItemClicked(position: Int) {
        onItemClickedListener?.invoke(getItem(position))
    }
}

class SuggestionViewHolder(
    parent: ViewGroup,
    onViewHolderClickedListener: OnViewHolderClickedListener
) : ClickableViewHolder(
    parent.inflate(R.layout.tink_item_budget_creation_search_suggestion),
    onViewHolderClickedListener
) {
    val label: TextView = itemView.findViewById(R.id.label)
    val icon: AppCompatImageView = itemView.findViewById(R.id.icon)
    fun bind(suggestion: String) {
        label.text = suggestion

        icon.tint(R.attr.tink_expensesIconColor)
    }
}

internal object SuggestionDiffUtil : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}