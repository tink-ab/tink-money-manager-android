package com.tink.pfmsdk.theme;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.tink.pfmsdk.R;
import com.tink.pfmsdk.overview.charts.LeftToSpendTutorialFragment;
import com.tink.pfmsdk.view.TinkToolbar;

public class TinkLeftToSpendTutorialTheme implements LeftToSpendTutorialFragment.Theme {

	private final Context context;

	public TinkLeftToSpendTutorialTheme(Context context) {
		this.context = context;
	}

	@Override
	public Drawable getChartDrawable() {
		return context.getDrawable(R.drawable.left_to_spend_example_tink);
	}

	// TODO: PFMSDK
//	@Override
//	public TinkIconView.Theme getCloseTheme() {
//		return new TinkPlainIconTheme(context) {
//			@Override
//			public float getIconSize() {
//				return DimensionUtils.getPixelsFromDP(24, context);
//			}
//		};
//	}

	@Override
	public TinkToolbar.Theme getToolbarTheme() {
		return null;
	}

	@Override
	public StatusBarTheme getStatusBarTheme() {
		return new TinkLeftToSpendStatusBarTheme(context);
	}
}
