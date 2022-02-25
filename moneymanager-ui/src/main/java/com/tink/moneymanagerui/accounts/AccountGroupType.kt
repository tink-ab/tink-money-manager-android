package com.tink.moneymanagerui.accounts

import android.os.Parcelable
import com.tink.model.account.Account
import com.tink.moneymanagerui.accounts.list.GroupedAccountsItem
import com.tink.moneymanagerui.overview.accounts.AccountWithImage
import com.tink.moneymanagerui.overview.accounts.toAccountGroup
import kotlinx.android.parcel.Parcelize

sealed class AccountGroupType(val areAccountsGrouped: Boolean) : Parcelable

@Parcelize
object NoAccountGroup : AccountGroupType(false)

@Parcelize
object AccountGroupByKind : AccountGroupType(true), AccountGroupable {
    override fun groupAccounts(accounts: List<Account>): List<GroupedAccountsItem> {
        return accounts.groupBy { account ->
            account.type.toAccountGroup()
        }.map { mapEntry ->
            // TODO: Load provider images correctly
            GroupedAccountsItem(mapEntry.key, mapEntry.value.map { AccountWithImage(it, null) })
        }.filter {
            it.accounts.isNotEmpty()
        }.sortedBy {
            it.accountGroup.sortOrder
        }
    }
}

@Parcelize
class CustomAccountGroup(val grouping: (List<Account>) -> List<GroupedAccountsItem>) : AccountGroupType(true), AccountGroupable {
    override fun groupAccounts(accounts: List<Account>): List<GroupedAccountsItem> {
        return grouping(accounts)
    }
}
