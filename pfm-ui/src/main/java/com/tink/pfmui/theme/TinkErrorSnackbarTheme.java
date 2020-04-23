package com.tink.pfmui.theme;

import android.content.Context;
import com.tink.pfmui.R;
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
