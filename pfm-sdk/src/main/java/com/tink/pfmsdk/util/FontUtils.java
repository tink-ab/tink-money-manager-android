package com.tink.pfmsdk.util;

import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.FontRes;
import androidx.core.content.res.ResourcesCompat;
import com.tink.pfmsdk.R;

public class FontUtils {

	public static Typeface getTypeface(@FontRes int fontResId, Context context) {
		return ResourcesCompat.getFont(context, fontResId);
	}

	public static final int BOLD_FONT = R.font.font_bold;
	public static final int REGULAR_FONT = R.font.font_regular;
}

