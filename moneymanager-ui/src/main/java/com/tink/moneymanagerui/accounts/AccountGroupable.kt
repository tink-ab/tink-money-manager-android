package com.tink.moneymanagerui.accounts

import com.tink.moneymanagerui.accounts.list.GroupedAccountsItem

interface AccountGroupable {
    fun groupAccounts(accounts: List<AccountWithImage>): List<GroupedAccountsItem>
}
