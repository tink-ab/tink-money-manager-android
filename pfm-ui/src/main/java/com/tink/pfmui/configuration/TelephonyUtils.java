package com.tink.pfmui.configuration;

import android.content.Context;
import android.telephony.TelephonyManager;
import androidx.annotation.Nullable;

final class TelephonyUtils {

	@Nullable
	public static String getTelephonySimMarket(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
			.getSystemService(Context.TELEPHONY_SERVICE);

		if (telephonyManager != null) {
			return telephonyManager.getSimCountryIso().toUpperCase();
		} else {
			return null;
		}
	}
}
