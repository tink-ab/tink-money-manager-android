package com.tink.moneymanagerui.util;

import android.content.Context;

public class DimensionUtils {

	public static float getPixelsFromDP(float dp, Context context) {
		return (float) Math.ceil(dp * context.getResources().getDisplayMetrics().density);
	}

	public static float getEmFromDp(float dp) {
		return 0.0624f * dp;
	}
}
