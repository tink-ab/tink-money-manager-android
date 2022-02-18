package com.tink.moneymanagerui.accounts.list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tink.model.account.Account
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.overview.accounts.AccountWithImage
import kotlinx.android.synthetic.main.tink_grouped_item_account.view.*
import se.tink.commons.extensions.inflate
import se.tink.commons.extensions.sum

class GroupedAccountList(val accountClicked: (Account) -> Unit):
    ListAdapter<AccountWithImage, GroupedAccountList.GroupedAccountViewHolder>(AccountDiffUtil) {

    val accountGroupingKinds = AccountGroupByKind.values().toList()

    var kindToAccountsMap: Map<AccountGroupByKind, List<AccountWithImage>> = emptyMap()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : GroupedAccountViewHolder {
        return GroupedAccountViewHolder(parent.inflate(R.layout.tink_grouped_item_account))
    }

    override fun onBindViewHolder(holder: GroupedAccountViewHolder, position: Int) {
        val key = kindToAccountsMap.keys.sorted()[position]
        holder.bind(kindToAccountsMap[key]!!)
    }

    override fun submitList(list: MutableList<AccountWithImage>?) {
        super.submitList(list)

        kindToAccountsMap = list!!.groupBy {
            when (it.account.type) {
                Account.Type.CHECKING,
                Account.Type.CREDIT_CARD -> AccountGroupByKind.EVERYDAY

                Account.Type.SAVINGS,
                Account.Type.INVESTMENT,
                Account.Type.PENSION -> AccountGroupByKind.SAVINGS

                Account.Type.MORTGAGE,
                Account.Type.LOAN -> AccountGroupByKind.LOANS

                Account.Type.OTHER,
                Account.Type.EXTERNAL,
                Account.Type.UNKNOWN -> AccountGroupByKind.OTHER
            }
        }
    }

    override fun getItemCount(): Int {
        return kindToAccountsMap.keys.size
    }

    inner class GroupedAccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(accountWithImageList: List<AccountWithImage>) {
            val balanceSum = accountWithImageList.map { it.account.balance }.sum()

            itemView.accountSumText.text = accountWithImageList.size.toString()

            val accountsAdapter = NotGroupedAccountList { account ->
                accountClicked(account)
            }

            accountsAdapter.submitList(accountWithImageList)

            itemView.childList.apply {
                layoutManager = LinearLayoutManager(itemView.context)
                adapter = accountsAdapter
                isNestedScrollingEnabled = true
            }
        }
    }

    enum class AccountGroupByKind {
        EVERYDAY,
        SAVINGS,
        LOANS,
        OTHER
    }
}
