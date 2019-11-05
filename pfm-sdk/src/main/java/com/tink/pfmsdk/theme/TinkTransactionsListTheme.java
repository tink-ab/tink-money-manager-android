package com.tink.pfmsdk.theme;

import android.content.Context;
import com.tink.pfmsdk.transaction.TransactionsListFragment;
import com.tink.pfmsdk.view.TinkToolbar;

public class TinkTransactionsListTheme implements TransactionsListFragment.Theme {

	private Context context;
	private int statusbarColor;
	private int backgroundColor;

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
}
