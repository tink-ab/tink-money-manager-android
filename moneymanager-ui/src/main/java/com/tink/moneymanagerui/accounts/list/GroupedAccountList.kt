package com.tink.moneymanagerui.accounts.list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tink.model.account.Account
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.overview.accounts.AccountWithImage
import kotlinx.android.synthetic.main.tink_grouped_item_account.view.*
import se.tink.commons.extensions.inflate

class GroupedAccountList(val accountClicked: (Account) -> Unit):
    ListAdapter<AccountWithImage, GroupedAccountList.GroupedAccountViewHolder>(AccountDiffUtil) {

    private val accountsAdapter = NotGroupedAccountList { account ->
        accountClicked(account)
    }

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
            itemView.testText.text = accountWithImageList.size.toString()

            accountsAdapter.submitList(accountWithImageList)

            itemView.childList.layoutManager = LinearLayoutManager(itemView.context)
            itemView.childList.adapter = accountsAdapter
            itemView.childList.addItemDecoration(DividerItemDecoration(itemView.context, DividerItemDecoration.VERTICAL))
        }
    }

    enum class AccountGroupByKind {
        EVERYDAY,
        SAVINGS,
        LOANS,
        OTHER
    }
}
