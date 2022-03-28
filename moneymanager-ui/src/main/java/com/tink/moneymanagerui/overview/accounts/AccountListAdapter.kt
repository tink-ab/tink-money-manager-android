package com.tink.moneymanagerui.overview.accounts

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.util.ImageUtils
import com.tink.moneymanagerui.util.extensions.formatCurrencyExact
import kotlinx.android.synthetic.main.tink_item_overview_account.view.*
import se.tink.android.viewholders.ClickableViewHolder
import se.tink.android.viewholders.OnViewHolderClickedListener
import se.tink.commons.extensions.getDrawableResIdFromAttr
import se.tink.commons.extensions.inflate

internal class AccountListAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    OnViewHolderClickedListener {

    private var accountItems: List<AccountWithImage> = emptyList()

    var onAccountClickedListener: ((AccountWithImage) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        AccountItemViewHolder(parent, this)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AccountItemViewHolder).bind(accountItems[position])
    }

    override fun onItemClicked(position: Int) {
        onAccountClickedListener?.invoke(accountItems[position])
    }

    override fun getItemCount(): Int = accountItems.size

    fun setAccounts(accounts: List<AccountWithImage>) {
        val oldData = accountItems.toList()
        this.accountItems = accounts
        calculateDiff(oldData, accountItems).dispatchUpdatesTo(this)
    }


    private fun calculateDiff(old: List<AccountWithImage>, new: List<AccountWithImage>) =
        DiffUtil.calculateDiff(AccountItemDiffCallback(old, new))

    private class AccountItemDiffCallback(
        val old: List<AccountWithImage>,
        val new: List<AccountWithImage>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = old.size
        override fun getNewListSize(): Int = new.size

        override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean =
            old[oldPos].account.id == new[newPos].account.id

        override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean =
            old[oldPos] == (new[newPos])
    }
}

internal class AccountItemViewHolder(
    parent: ViewGroup,
    onViewHolderClickedListener: OnViewHolderClickedListener
) : ClickableViewHolder(
    parent.inflate(R.layout.tink_item_overview_account),
    onViewHolderClickedListener
) {
    fun bind(account: AccountWithImage) {
        with(itemView) {
            if (account.hasIcon) {
                ImageUtils.loadImageFromUrl(bankLogo, account.iconUrl!!, R.attr.tink_icon_ingested_account)
            } else {
                bankLogo.setImageResFromAttr(R.attr.tink_icon_ingested_account)
            }
            accountName.text = account.account.name
            accountBalance.text = account.account.balance.formatCurrencyExact()
        }
    }
}

fun ImageView.setImageResFromAttr(attrResId: Int) {
    context.getDrawableResIdFromAttr(attrResId).also(::setImageResource)
}
