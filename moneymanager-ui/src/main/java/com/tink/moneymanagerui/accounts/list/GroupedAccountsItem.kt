package com.tink.moneymanagerui.accounts.list

import com.tink.model.misc.Amount
import com.tink.moneymanagerui.overview.accounts.AccountWithImage
import se.tink.commons.extensions.sum

data class GroupedAccountsItem(val accountGroup: AccountGroupByKind, val accounts: List<AccountWithImage>) {
    val totalBalance: Amount = accounts.map { it.account.balance }.sum()
}