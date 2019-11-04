package com.tink.pfmsdk.transaction

import android.os.Parcelable
import androidx.annotation.ColorInt
import kotlinx.android.parcel.Parcelize
import se.tink.core.models.misc.Period

@Parcelize
data class TransactionsListMetaData(
    @ColorInt val statusBarColor: Int,
    @ColorInt val backgroundColor: Int,
    val title: String,
    val isLeftToSpend: Boolean = false,
    val period: Period? = null,
    val categoryCode: String? = null,
    val isShowAll: Boolean = false,
    val statusSubtitleMode: StatusSubtitleMode = StatusSubtitleMode.SHOW_REDUCED_AMOUNT,
    val transactionIds: List<String>? = null
) : Parcelable

sealed class TransactionListMode {
    object All : TransactionListMode()
    class LeftToSpend(val period: Period?) : TransactionListMode()
    class Category(val categoryCode: String, val period: Period?) : TransactionListMode()
    class PresetIds(val ids: List<String>) : TransactionListMode()
    class Account(val accountId: String) : TransactionListMode()
    object Invalid : TransactionListMode()
}

fun TransactionsListMetaData.toListMode(): TransactionListMode =
    when {
        transactionIds?.isNotEmpty() == true -> TransactionListMode.PresetIds(transactionIds)
        isLeftToSpend -> TransactionListMode.LeftToSpend(period)
        isShowAll -> TransactionListMode.All
        categoryCode?.isNotEmpty() == true -> TransactionListMode.Category(categoryCode, period)
        else -> TransactionListMode.Invalid
    }

enum class StatusSubtitleMode {
    HIDE, SHOW_DATE, SHOW_REDUCED_AMOUNT
}
