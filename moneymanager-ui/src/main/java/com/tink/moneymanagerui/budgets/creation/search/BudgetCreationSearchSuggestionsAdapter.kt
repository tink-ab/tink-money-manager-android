package com.tink.moneymanagerui.budgets.creation.search

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tink.moneymanagerui.R
import se.tink.commons.extensions.inflate
import se.tink.android.viewholders.ClickableViewHolder
import se.tink.android.viewholders.OnViewHolderClickedListener

internal class BudgetCreationSearchSuggestionsAdapter : RecyclerView.Adapter<ClickableViewHolder>(),
    OnViewHolderClickedListener {

    private var suggestions: MutableList<String> = mutableListOf()

    var onItemClickedListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClickableViewHolder =
        SuggestionViewHolder(parent, this)

    override fun onBindViewHolder(holder: ClickableViewHolder, position: Int) {
        (holder as SuggestionViewHolder).bind(suggestions[position])
    }

    override fun getItemCount(): Int = suggestions.size

    override fun onItemClicked(position: Int) {
        onItemClickedListener?.invoke(suggestions[position])
    }

    fun setData(data: List<String>) {
        val oldData = suggestions.toMutableList()
        suggestions.clear()
        suggestions.addAll(data)
        DiffUtil.calculateDiff(
            object : DiffUtil.Callback() {
                override fun getOldListSize(): Int = oldData.size
                override fun getNewListSize(): Int = suggestions.size

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                    oldData[oldItemPosition] == suggestions[newItemPosition]

                override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean =
                    oldData[oldPos] == suggestions[newPos]

            }
        )
            .dispatchUpdatesTo(this)
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
    fun bind(suggestion: String) {
        label.text = suggestion
    }
}