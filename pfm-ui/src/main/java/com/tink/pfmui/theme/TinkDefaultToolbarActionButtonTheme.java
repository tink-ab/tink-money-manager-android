package com.tink.pfmui.theme;

import android.content.Context;
import android.graphics.Typeface;
import com.tink.pfmui.view.Deci;
import com.tink.pfmui.util.FontUtils;
import com.tink.pfmui.R;
import com.tink.pfmui.view.TinkToolbar;
import se.tink.commons.extensions.ContextUtils;

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
		return ContextUtils.getColorFromAttr(context, R.attr.tink_colorOnPrimary);
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
