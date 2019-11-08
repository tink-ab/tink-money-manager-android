package com.tink.pfmsdk.theme;

import android.content.Context;
import android.graphics.Typeface;
import androidx.core.content.ContextCompat;
import com.tink.pfmsdk.view.Deci;
import com.tink.pfmsdk.util.FontUtils;
import com.tink.pfmsdk.R;
import com.tink.pfmsdk.view.TinkToolbar;

public class TinkDefaultToolbarActionButtonTheme implements TinkToolbar.Theme.ToolbarTextTheme {

	private final Context context;
	private Deci deci;

	public TinkDefaultToolbarActionButtonTheme(Context context) {
		this.context = context;

		deci = new Deci(context);
	}

	@Override
	public boolean shouldChangeTextSize() {
		return false;
	}

	@Override
	public float getTextSizeInPx() {
		return 0;
	}

	@Override
	public int getTextColor() {
		return ContextCompat.getColor(context, R.color.default_button_text_color);
	}

	@Override
	public Typeface getFont() {
		return FontUtils.getTypeface(FontUtils.BOLD_FONT, context);
	}

	@Override
	public float getTextSize() {
		return deci.getTextSize();
	}

	@Override
	public float getLineHeight() {
		return deci.getLineHeight();
	}

	@Override
	public float getSpacing() {
		return deci.getSpacing();
	}

	@Override
	public boolean toUpperCase() {
		return deci.toUpperCase();
	}

	@Override
	public float getLetterSpacing() {
		return deci.getSpacing();
	}

}
