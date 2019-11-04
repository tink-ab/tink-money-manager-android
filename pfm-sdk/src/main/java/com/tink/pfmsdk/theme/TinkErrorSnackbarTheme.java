package com.tink.pfmsdk.theme;

import android.content.Context;
import androidx.core.content.ContextCompat;
import com.tink.pfmsdk.R;

public class TinkErrorSnackbarTheme extends TinkDefaultSnackbarTheme {

	Context context;

	public TinkErrorSnackbarTheme(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public int getBackgroundColor() {
		return ContextCompat.getColor(context, R.color.colorWarning);
	}
}
