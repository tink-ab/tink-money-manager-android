package com.tink.moneymanagerui.buildConfig

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NetworkConfiguration(
    val serverAddress: String,
    val clientKey: String,
    val sslKey: String,
    val port: Int,
    val useSsl: Boolean = true
) : Parcelable
