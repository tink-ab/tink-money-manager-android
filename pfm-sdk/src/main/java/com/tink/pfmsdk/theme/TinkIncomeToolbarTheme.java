package com.tink.pfmsdk.theme;

import android.content.Context;
import com.tink.pfmsdk.R;
import com.tink.pfmsdk.view.TinkToolbar;
import javax.inject.Inject;
import se.tink.commons.extensions.ContextUtils;

public class TinkIncomeToolbarTheme implements TinkToolbar.Theme {

	private final Context context;

	private ToolbarTextTheme actionButtonTheme;
	private ToolbarTextTheme titleTheme;

	@Inject
	public TinkIncomeToolbarTheme(Context context) {
		this.context = context;
	}

	@Override
	public int getBackgroundColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_incomeColor);
	}

	@Override
	public int getTitleColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_colorOnIncome);
	}

	@Override
	public ToolbarTextTheme getActionButtonTheme() {
		if (actionButtonTheme == null) {
			actionButtonTheme = new TinkDefaultToolbarActionButtonTheme(context) {
				@Override
				public int getTextColor() {
					return ContextUtils.getColorFromAttr(context, R.attr.tink_colorOnIncome);
				}
			};
		}
		return actionButtonTheme;
	}

	@Override
	public float getElevationDP() {
		return 4.0f;
	}

}
