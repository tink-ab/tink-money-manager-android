package com.tink.moneymanagerui.accounts

import com.tink.moneymanagerui.accounts.list.GroupedAccountsItem
import com.tink.moneymanagerui.overview.accounts.AccountWithImage

interface AccountGroupable {
    fun groupAccounts(accounts: List<AccountWithImage>) : List<GroupedAccountsItem>
}