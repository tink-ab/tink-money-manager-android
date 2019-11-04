package com.tink.pfmsdk.theme;

import android.content.Context;
import androidx.core.content.ContextCompat;
import com.tink.pfmsdk.R;

public class TinkDefaultStatusBarTheme implements StatusBarTheme {

	protected final Context context;

	public TinkDefaultStatusBarTheme(Context context) {
		this.context = context;
	}

	@Override
	public int getStatusBarColor() {
		return ContextCompat.getColor(context, R.color.colorPrimaryDark);
	}

	@Override
	public boolean isStatusBarLight() {
		return false;
	}
}
