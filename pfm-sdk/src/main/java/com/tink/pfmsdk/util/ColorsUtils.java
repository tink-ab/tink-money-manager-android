package com.tink.pfmsdk.util;


import androidx.core.graphics.ColorUtils;

public class ColorsUtils {

	public static int adjustAlpha(int baseColor, float factor) {
		int alpha = Math.round(255 * factor);
		return ColorUtils.setAlphaComponent(baseColor, alpha);
	}

}
