package com.tink.pfmsdk.theme;

import android.content.Context;
import com.tink.pfmsdk.BaseFragment;
import com.tink.pfmsdk.view.TinkToolbar.Theme;

public class DefaultFragmentTheme implements BaseFragment.Theme {

	private TinkDefaultToolbarTheme toolbarTheme;
	private TinkDefaultStatusBarTheme statusBarTheme;

	public DefaultFragmentTheme(Context context) {
		toolbarTheme = new TinkDefaultToolbarTheme(context);
		statusBarTheme = new TinkDefaultStatusBarTheme(context);
	}

	@Override
	public Theme getToolbarTheme() {
		return toolbarTheme;
	}

	@Override
	public StatusBarTheme getStatusBarTheme() {
		return statusBarTheme;
	}
}
