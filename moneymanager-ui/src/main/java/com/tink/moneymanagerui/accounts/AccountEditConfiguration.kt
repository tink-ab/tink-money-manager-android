package com.tink.moneymanagerui.accounts

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

enum class EditAccountField {
    NAME,
    KIND,
    IS_INCLUDED,
    IS_SHARED,
    IS_FAVORITE
}

@Parcelize
data class AccountEditConfiguration( val fields: List<EditAccountField>): Parcelable {
    companion object {
        val AllAccountFieldsEditable = AccountEditConfiguration(
            EditAccountField.values().toList()
        )
    }
}


