package com.tink.moneymanagerui.overview.accounts

import com.tink.model.account.Account

data class AccountWithImage(val account: Account, val iconUrl: String? = null) {
    val hasIcon = !iconUrl.isNullOrBlank()
}
