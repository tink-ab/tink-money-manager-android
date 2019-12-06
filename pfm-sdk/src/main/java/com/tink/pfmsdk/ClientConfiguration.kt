package com.tink.pfmsdk

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ClientConfiguration(
    val endpoint: String,
    val certificate: String = "",
    val port: Int
) : Parcelable