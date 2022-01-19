package com.tink.moneymanagerui.overview.accounts

import com.tink.model.misc.Amount
import com.tink.moneymanagerui.util.extensions.formatCurrencyExact

data class AccountInformation(val accountNumber: String, val balance: Amount) {

    // Pronounce account number letter by letter in talkback instead of as a number
    val accountNumberContentDescription = accountNumber.replace(".".toRegex(), "$0 ")

    val formattedBalance = balance.formatCurrencyExact()
}