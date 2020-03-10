package com.tink.pfmui.configuration

import android.content.Context
import se.tink.android.di.application.ApplicationScoped
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class MarketProvider @Inject constructor(
    @ApplicationScoped private val context: Context
) {
    val market: String?
        get() = TelephonyUtils.getTelephonySimMarket(context)
}
