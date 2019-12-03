package com.tink.pfmsdk.configuration

import android.content.Context
import se.tink.android.di.application.ApplicationScoped
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarketProvider @Inject constructor(
    @ApplicationScoped private val context: Context
) {
    val market: String?
        get() = TelephonyUtils.getTelephonySimMarket(context)
}
