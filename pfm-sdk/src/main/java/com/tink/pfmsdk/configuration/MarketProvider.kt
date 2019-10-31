package com.tink.pfmsdk.configuration

import android.content.Context
import com.tink.pfmsdk.ClientDataStorage
import se.tink.android.di.application.ApplicationScoped
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarketProvider @Inject constructor(
    @ApplicationScoped private val context: Context
) {

    private val storage = ClientDataStorage.sharedInstance(context)

    val market: String?
        get() = storage.icecreamMarket
            ?.takeIf { it.length == 2 }
            ?: TelephonyUtils.getTelephonySimMarket(context)
}
