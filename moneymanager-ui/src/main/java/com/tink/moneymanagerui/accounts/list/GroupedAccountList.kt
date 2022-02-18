package com.tink.moneymanagerui.accounts.list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tink.model.account.Account
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.util.CurrencyUtils
import kotlinx.android.synthetic.main.tink_grouped_item_account.view.*
import se.tink.commons.extensions.inflate

class GroupedAccountList(val accountClicked: (Account) -> Unit):
    ListAdapter<GroupedAccountsItem, GroupedAccountList.GroupedAccountViewHolder>(AccountGroupDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : GroupedAccountViewHolder {
        return GroupedAccountViewHolder(parent.inflate(R.layout.tink_grouped_item_account))
    }

    override fun onBindViewHolder(holder: GroupedAccountViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class GroupedAccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(groupedAccounts: GroupedAccountsItem) {

            itemView.accountKindText.text = itemView.context.getString(groupedAccounts.accountGroup.description)
            itemView.accountSumText.text = CurrencyUtils.formatCurrencyExact(groupedAccounts.totalBalance)

            val accountsAdapter = NotGroupedAccountList { account ->
                accountClicked(account)
            }

            accountsAdapter.submitList(groupedAccounts.accounts)

            itemView.childList.apply {
                layoutManager = LinearLayoutManager(itemView.context)
                adapter = accountsAdapter
                isNestedScrollingEnabled = true
            }
        }
    }
}
