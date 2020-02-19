package com.tink.pfmsdk.overview.accounts

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tink.pfmsdk.R
import com.tink.pfmsdk.util.CurrencyUtils
import kotlinx.android.synthetic.main.item_overview_account.view.*
import se.tink.android.viewholders.ClickableViewHolder
import se.tink.android.viewholders.OnViewHolderClickedListener
import se.tink.commons.extensions.inflate
import se.tink.core.models.account.Account

internal class AccountListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    OnViewHolderClickedListener {

    private var accountItems: List<Account> = emptyList()

    var onAccountClickedListener: ((Account) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        AccountItemViewHolder(parent, this)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AccountItemViewHolder).bind(accountItems[position])
    }

    override fun onItemClicked(position: Int) {
        onAccountClickedListener?.invoke(accountItems[position])
    }

    override fun getItemCount(): Int = accountItems.size

    fun setAccounts(accounts: List<Account>) {
        val oldData = accountItems.toList()
        this.accountItems = accounts
        calculateDiff(oldData, accountItems).dispatchUpdatesTo(this)
    }


    private fun calculateDiff(old: List<Account>, new: List<Account>) =
        DiffUtil.calculateDiff(AccountItemDiffCallback(old, new))

    private class AccountItemDiffCallback(
        val old: List<Account>,
        val new: List<Account>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = old.size
        override fun getNewListSize(): Int = new.size

        override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean =
            old[oldPos].id == new[newPos].id

        override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean =
            old[oldPos] == (new[newPos])
    }
}

internal class AccountItemViewHolder(
    parent: ViewGroup,
    onViewHolderClickedListener: OnViewHolderClickedListener
) : ClickableViewHolder(
    parent.inflate(R.layout.item_overview_account),
    onViewHolderClickedListener
) {
    fun bind(account: Account) {
        with(itemView) {
            // Hiding the bank logo for now to align with iOS
//            bankLogo.setImageURI(account.images.icon)
            accountName.text = account.name
            accountBalance.text = CurrencyUtils.formatCurrency(account.balance)
        }
    }
}