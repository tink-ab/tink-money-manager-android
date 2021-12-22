package com.tink.moneymanagerui.configuration

import android.content.Context
import com.tink.annotations.PfmScope
import se.tink.android.di.application.ApplicationScoped
import javax.inject.Inject

@PfmScope
internal class MarketProvider @Inject constructor(
    @ApplicationScoped private val context: Context
) {
    val market: String
        get() = TelephonyUtils.getTelephonySimMarket(context)
}
