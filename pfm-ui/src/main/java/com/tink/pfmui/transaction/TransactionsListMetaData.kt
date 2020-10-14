package com.tink.pfmui.transaction

import android.os.Parcelable
import androidx.annotation.ColorInt
import kotlinx.android.parcel.Parcelize
import se.tink.core.models.misc.Period

@Parcelize
internal data class TransactionsListMetaData(
    @ColorInt val statusBarColor: Int,
    @ColorInt val backgroundColor: Int,
    @ColorInt val titleColor: Int,
    val title: String,
    val isLeftToSpend: Boolean = false,
    val period: Period? = null,
    val categoryCode: String? = null,
    val isShowAll: Boolean = false,
    val statusSubtitleMode: StatusSubtitleMode = StatusSubtitleMode.SHOW_REDUCED_AMOUNT,
    val transactionIds: List<String>? = null,
    val accountId: String? = null
) : Parcelable

internal sealed class TransactionListMode {
    object All : TransactionListMode()
    class LeftToSpend(val period: Period?) : TransactionListMode()
    class Category(val categoryCode: String, val period: Period?) : TransactionListMode()
    class PresetIds(val ids: List<String>) : TransactionListMode()
    class Account(val accountId: String) : TransactionListMode()
    object Invalid : TransactionListMode()
}

internal fun TransactionsListMetaData.toListMode(): TransactionListMode =
    when {
        transactionIds?.isNotEmpty() == true -> TransactionListMode.PresetIds(transactionIds)
        isLeftToSpend -> TransactionListMode.LeftToSpend(period)
        isShowAll -> TransactionListMode.All
        categoryCode?.isNotEmpty() == true -> TransactionListMode.Category(categoryCode, period)
        accountId?.isNotEmpty() == true -> TransactionListMode.Account(accountId)
        else -> TransactionListMode.Invalid
    }

internal enum class StatusSubtitleMode {
    HIDE, SHOW_DATE, SHOW_REDUCED_AMOUNT
}
