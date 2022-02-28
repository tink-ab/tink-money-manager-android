package com.tink.moneymanagerui.accounts

import com.tink.model.account.Account
import com.tink.moneymanagerui.accounts.list.GroupedAccountsItem

interface AccountGroupable {
    fun groupAccounts(accounts: List<Account>) : List<GroupedAccountsItem>
}