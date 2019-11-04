package com.tink.pfmsdk.theme;

import android.content.Context;
import android.graphics.Typeface;
import androidx.core.content.ContextCompat;
import com.tink.pfmsdk.util.FontUtils;
import com.tink.pfmsdk.view.Hecto;
import com.tink.pfmsdk.R;
import com.tink.pfmsdk.view.TinkSnackbar;
import com.tink.pfmsdk.view.TinkTextView.Theme;

public class TinkDefaultSnackbarTheme implements TinkSnackbar.Theme {

	private Context context;
	private Theme textTheme;
	private Theme buttonTheme;

	public TinkDefaultSnackbarTheme(final Context context) {
		this.context = context;

		textTheme = new Hecto(context) {
			@Override
			public int getTextColor() {
				return ContextCompat.getColor(context, R.color.color_on_snackbar_background);
			}
		};

		buttonTheme = new Hecto(context) {
			@Override
			public int getTextColor() {
				return ContextCompat.getColor(context, R.color.color_on_snackbar_background);
			}

			@Override
			public Typeface getFont() {
				return FontUtils.getTypeface(FontUtils.LOTA_BOLD_FONT, context);
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
		return ContextCompat.getColor(context, R.color.snackbar_background);
	}

	@Override
	public int getLoadingIndicatorColor() {
		return ContextCompat.getColor(context, R.color.color_on_snackbar_background);
	}
}
