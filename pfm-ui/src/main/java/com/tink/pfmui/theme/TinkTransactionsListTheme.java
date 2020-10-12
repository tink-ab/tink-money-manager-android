package com.tink.pfmui.theme;

import android.content.Context;
import com.tink.pfmui.transaction.TransactionsListFragment;
import com.tink.pfmui.view.TinkToolbar;

public class TinkTransactionsListTheme implements TransactionsListFragment.Theme {

	private Context context;
	private int statusbarColor;
	private int backgroundColor;
	private int titleColor;

	public TinkTransactionsListTheme(Context context) {
		this.context = context;
	}

	@Override
	public TinkToolbar.Theme getToolbarTheme() {
		return new TinkDefaultToolbarTheme(context) {

			@Override
			public int getBackgroundColor() {
				return backgroundColor;
			}

			@Override
			public int getTitleColor() {
				return titleColor;
			}
		};
	}

	public StatusBarTheme getStatusBarTheme() {
		return new TinkDefaultStatusBarTheme(context) {
			@Override
			public int getStatusBarColor() {
				return statusbarColor;
			}
		};
	}

	@Override
	public void setStatusbarColor(int color) {
		statusbarColor = color;
	}

	public void setToolbarBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setTitleColor(int titleColor) {
		this.titleColor = titleColor;
	}
}
