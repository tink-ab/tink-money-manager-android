package com.tink.pfmui.util;

import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.FontRes;
import androidx.core.content.res.ResourcesCompat;
import com.tink.pfmui.R;

public class FontUtils {

	public static Typeface getTypeface(@FontRes int fontResId, Context context) {
		return ResourcesCompat.getFont(context, fontResId);
	}

	public static final int BOLD_FONT = R.font.tink_font_bold;
	public static final int REGULAR_FONT = R.font.tink_font_regular;
}

