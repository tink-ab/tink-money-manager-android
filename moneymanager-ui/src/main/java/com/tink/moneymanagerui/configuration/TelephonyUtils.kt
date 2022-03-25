package com.tink.moneymanagerui.configuration

import android.content.Context
import android.telephony.TelephonyManager
import java.util.Locale

internal object TelephonyUtils {
    fun getTelephonySimMarket(context: Context): String {
        val telephonyManager = context
            .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.simCountryIso.uppercase(Locale.getDefault())
    }
}
