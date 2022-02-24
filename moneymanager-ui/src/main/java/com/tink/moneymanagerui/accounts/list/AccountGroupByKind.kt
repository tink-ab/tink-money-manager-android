package com.tink.moneymanagerui.accounts.list

import androidx.annotation.StringRes
import com.tink.moneymanagerui.R

data class AccountGroup(@StringRes val description: Int, val sortOrder: Int)

internal val EVERYDAY_ACCOUNTS = AccountGroup(R.string.tink_everyday_account, 0)
internal val SAVINGS_ACCOUNTS = AccountGroup(R.string.tink_savings_account, 1)
internal val LOANS_ACCOUNTS = AccountGroup(R.string.tink_loans_account, 2)
internal val OTHER_ACCOUNTS = AccountGroup(R.string.tink_other_account, 3)