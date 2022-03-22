package com.tink.moneymanagerui.overview.accounts

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tink.moneymanagerui.R
import kotlinx.android.synthetic.main.tink_item_account_header.view.*
import se.tink.commons.extensions.inflate

internal class AccountHeaderAdapter : ListAdapter<AccountInformation, AccountHeaderViewHolder>(AccountInformationDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountHeaderViewHolder {
        return AccountHeaderViewHolder(parent)
    }

    override fun onBindViewHolder(holder: AccountHeaderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

internal class AccountHeaderViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.tink_item_account_header)) {
    fun bind(account: AccountInformation) {
        with(itemView) {
            accountNumber.text = account.accountNumber
            accountNumber.contentDescription = account.accountNumberContentDescription
            accountBalance.text = account.formattedBalance
        }
    }
}

internal object AccountInformationDiffUtil : DiffUtil.ItemCallback<AccountInformation>() {
    override fun areItemsTheSame(oldItem: AccountInformation, newItem: AccountInformation): Boolean {
        return oldItem.accountNumber == newItem.accountNumber
    }

    override fun areContentsTheSame(oldItem: AccountInformation, newItem: AccountInformation): Boolean {
        return oldItem == newItem
    }
}
