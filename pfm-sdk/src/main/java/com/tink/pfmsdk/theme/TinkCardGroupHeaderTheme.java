package com.tink.pfmsdk.theme;

import android.content.Context;
import com.tink.pfmsdk.view.CardGroupHeader.Theme;
import com.tink.pfmsdk.view.DeciActionBold;
import com.tink.pfmsdk.view.GroupPositionUtils;
import com.tink.pfmsdk.view.HectoTertiary;
import com.tink.pfmsdk.view.TinkTextView;

public class TinkCardGroupHeaderTheme implements Theme {

	private final Context context;

	public TinkCardGroupHeaderTheme(Context context) {
		this.context = context;
	}

	@Override
	public TinkTextView.Theme getHeaderTheme() {
		return new HectoTertiary(context);
	}

	@Override
	public TinkTextView.Theme getButtonTheme() {
		return new DeciActionBold(context);
	}

	@Override
	public GroupPositionUtils.Theme getBackground() {
		return new TinkDefaultGroupPositionTheme();
	}
}
