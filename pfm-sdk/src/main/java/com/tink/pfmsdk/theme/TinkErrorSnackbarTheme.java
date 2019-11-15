package com.tink.pfmsdk.theme;

import android.content.Context;
import com.tink.pfmsdk.R;
import se.tink.commons.extensions.ContextUtils;

public class TinkErrorSnackbarTheme extends TinkDefaultSnackbarTheme {

	Context context;

	public TinkErrorSnackbarTheme(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public int getBackgroundColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_warningColor);
	}
}
