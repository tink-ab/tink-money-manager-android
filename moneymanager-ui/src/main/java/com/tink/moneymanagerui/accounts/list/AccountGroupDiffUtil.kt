package com.tink.moneymanagerui.accounts.list

import androidx.recyclerview.widget.DiffUtil

internal object AccountGroupDiffUtil : DiffUtil.ItemCallback<GroupedAccountsItem>() {
    override fun areItemsTheSame(oldItem: GroupedAccountsItem, newItem: GroupedAccountsItem): Boolean {
        return oldItem.accountGroup == newItem.accountGroup
    }

    override fun areContentsTheSame(oldItem: GroupedAccountsItem, newItem: GroupedAccountsItem): Boolean {
        return oldItem.accountGroup == newItem.accountGroup && oldItem.accounts == newItem.accounts
    }
}
