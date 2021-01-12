package com.tink.pfmui.theme;

import android.content.Context;
import android.graphics.Typeface;
import com.tink.pfmui.util.FontUtils;
import com.tink.pfmui.view.Hecto;
import com.tink.pfmui.R;
import com.tink.pfmui.view.TinkSnackbar;
import com.tink.pfmui.view.TinkTextView.Theme;
import se.tink.commons.extensions.ContextUtils;

public class TinkDefaultSnackbarTheme implements TinkSnackbar.Theme {

	private Context context;
	private Theme textTheme;
	private Theme buttonTheme;

	public TinkDefaultSnackbarTheme(final Context context) {
		this.context = context;

		textTheme = new Hecto(context) {
			@Override
			public int getTextColor() {
				return ContextUtils.getColorFromAttr(context, R.attr.tink_colorOnSnackBar);
			}
		};

		buttonTheme = new Hecto(context) {
			@Override
			public int getTextColor() {
				return ContextUtils.getColorFromAttr(context, R.attr.tink_colorOnSnackBar);
			}

			@Override
			public Typeface getFont() {
				return FontUtils.getTypeface(FontUtils.BOLD_FONT, context);
			}
		};
	}


	@Override
	public Theme getTextTheme() {
		return textTheme;
	}

	@Override
	public Theme getButtonTheme() {
		return buttonTheme;
	}

	@Override
	public int getBackgroundColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_snackbarColor);
	}

	@Override
	public int getLoadingIndicatorColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_colorOnSnackBar);
	}
}