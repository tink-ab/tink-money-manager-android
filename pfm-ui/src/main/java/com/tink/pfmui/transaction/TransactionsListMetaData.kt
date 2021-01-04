package com.tink.pfmui.transaction

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.tink.model.time.Period

@Parcelize
internal data class TransactionsListMetaData(
    val title: String,
    val isLeftToSpend: Boolean = false,
    val period: Period? = null,
    val categoryId: String? = null,
    val isShowAll: Boolean = false,
    val statusSubtitleMode: StatusSubtitleMode = StatusSubtitleMode.SHOW_REDUCED_AMOUNT,
    val transactionIds: List<String>? = null,
    val accountId: String? = null
) : Parcelable

internal sealed class TransactionListMode {
    object All : TransactionListMode()
    class LeftToSpend(val period: Period?) : TransactionListMode()
    class Category(val categoryId: String, val period: Period?) : TransactionListMode()
    class PresetIds(val ids: List<String>) : TransactionListMode()
    class Account(val accountId: String) : TransactionListMode()
    object Invalid : TransactionListMode()
}

internal fun TransactionsListMetaData.toListMode(): TransactionListMode =
    when {
        transactionIds?.isNotEmpty() == true -> TransactionListMode.PresetIds(transactionIds)
        isLeftToSpend -> TransactionListMode.LeftToSpend(period)
        isShowAll -> TransactionListMode.All
        categoryId?.isNotEmpty() == true -> TransactionListMode.Category(categoryId, period)
        accountId?.isNotEmpty() == true -> TransactionListMode.Account(accountId)
        else -> TransactionListMode.Invalid
    }

internal enum class StatusSubtitleMode {
    HIDE, SHOW_DATE, SHOW_REDUCED_AMOUNT
}
