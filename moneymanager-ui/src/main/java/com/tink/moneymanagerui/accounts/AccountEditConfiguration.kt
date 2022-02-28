package com.tink.moneymanagerui.accounts

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AccountEditConfiguration(
    val name: Boolean,
    val kind: Boolean,
    val isIncluded: Boolean,
    val isShared: Boolean,
    val isFavorite: Boolean,
): Parcelable {
    companion object {
        val AllAccountFieldsEditable = AccountEditConfiguration(
            name = true,
            kind = true,
            isIncluded = true,
            isShared = true,
            isFavorite = true
        )
    }
}


