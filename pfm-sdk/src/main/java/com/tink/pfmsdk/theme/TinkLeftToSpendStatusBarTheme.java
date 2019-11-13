package com.tink.pfmsdk.theme;

import android.content.Context;
import com.tink.pfmsdk.R;
import se.tink.commons.extensions.ContextUtils;

public class TinkLeftToSpendStatusBarTheme extends TinkDefaultStatusBarTheme {

	public TinkLeftToSpendStatusBarTheme(Context context) {
		super(context);
	}

	@Override
	public int getStatusBarColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_colorPrimaryDark);
	}
}
