package com.tink.pfmsdk.theme;

import android.content.Context;
import com.tink.pfmsdk.BaseFragment;
import com.tink.pfmsdk.view.TinkToolbar.Theme;

public class NoToolbarFragmentTheme implements BaseFragment.Theme {

	private TinkDefaultStatusBarTheme statusBarTheme;

	public NoToolbarFragmentTheme(Context context) {
		statusBarTheme = new TinkNoToolbarStatusBarTheme(context);
	}

	@Override
	public Theme getToolbarTheme() {
		return null;
	}

	@Override
	public StatusBarTheme getStatusBarTheme() {
		return statusBarTheme;
	}
}
