package com.tink.moneymanagerui.accounts

import android.os.Parcelable
import com.tink.moneymanagerui.accounts.list.GroupedAccountsItem
import com.tink.moneymanagerui.overview.accounts.AccountWithImage
import com.tink.moneymanagerui.overview.accounts.toAccountGroup
import kotlinx.android.parcel.Parcelize

sealed class AccountGroupType(val areAccountsGrouped: Boolean) : Parcelable

@Parcelize
object NoAccountGroup : AccountGroupType(false)

@Parcelize
object AccountGroupByKind : AccountGroupType(true), AccountGroupable {
    override fun groupAccounts(accounts: List<AccountWithImage>): List<GroupedAccountsItem> {
        return accounts.groupBy { account ->
            account.account.type.toAccountGroup()
        }.map { mapEntry ->
            GroupedAccountsItem(mapEntry.key, mapEntry.value)
        }.filter {
            it.accounts.isNotEmpty()
        }.sortedBy {
            it.accountGroup.sortOrder
        }
    }
}

@Parcelize
class CustomAccountGroup(val grouping: (List<AccountWithImage>) -> List<GroupedAccountsItem>) : AccountGroupType(true), AccountGroupable {
    override fun groupAccounts(accounts: List<AccountWithImage>): List<GroupedAccountsItem> {
        return grouping(accounts)
    }
}
