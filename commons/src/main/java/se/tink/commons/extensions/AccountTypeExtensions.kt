package se.tink.commons.extensions

import android.content.Context
import se.tink.commons.R
import se.tink.core.models.account.Account

fun Account.Type.convertToString(context: Context): String =
    when (this) {
        Account.Type.TYPE_CHECKING -> context.getString(R.string.account_type_checking)
        Account.Type.TYPE_INVESTMENT -> context.getString(R.string.account_type_investment)
        Account.Type.TYPE_SAVINGS -> context.getString(R.string.account_type_saving)
        Account.Type.TYPE_LOAN -> context.getString(R.string.account_type_loan)
        Account.Type.TYPE_MORTGAGE -> context.getString(R.string.account_type_mortgage)
        Account.Type.TYPE_CREDIT_CARD -> context.getString(R.string.account_type_card)
        Account.Type.TYPE_PENSION -> context.getString(R.string.account_type_pension)
        else -> context.getString(R.string.account_type_other)
    }