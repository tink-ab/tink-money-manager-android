package com.tink.pfmui.theme;

import android.content.Context;
import com.tink.pfmui.R;
import se.tink.commons.extensions.ContextUtils;

public class TinkDefaultStatusBarTheme implements StatusBarTheme {

	protected final Context context;

	public TinkDefaultStatusBarTheme(Context context) {
		this.context = context;
	}

	@Override
	public int getStatusBarColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_colorPrimaryDark);
	}

	@Override
	public boolean isStatusBarLight() {
		return false;
	}
}
