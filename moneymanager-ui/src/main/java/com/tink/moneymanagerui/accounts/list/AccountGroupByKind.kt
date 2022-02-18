package com.tink.moneymanagerui.accounts.list

import androidx.annotation.StringRes
import com.tink.moneymanagerui.R

enum class AccountGroupByKind(@StringRes val description: Int, val sortOrder: Int) {
    EVERYDAY(R.string.tink_everyday_account, 0),
    SAVINGS(R.string.tink_savings_account, 1),
    LOANS(R.string.tink_loans_account, 2),
    OTHER(R.string.tink_other_account, 3)
}