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

	public static final int LOTA_BOLD_FONT = R.font.lota_grotesque_bold;
	public static final int LOTA_SEMI_BOLD_FONT = R.font.lota_grotesque_semi_bold;
	public static final int LOTA_REGULAR_FONT = R.font.lota_grotesque_regular;

	public static final int TINK_ICON_FONT = R.font.tink_pictogram;

	public static class Truetypes {

		public static final int LOTA_BOLD_FONT = R.font.lota_bold_ttf;
		public static final int LOTA_SEMI_BOLD_FONT = R.font.lota_semibold_ttf;
		public static final int LOTA_REGULAR_FONT = R.font.lota_regular_ttf;

		public static final String LOTA_BOLD_FONT_PATH = "lota_bold_ttf.ttf";
		public static final String LOTA_REGULAR_FONT_PATH = "lota_regular_ttf.ttf";
	}

}

