package com.tink.pfmui.theme;

import android.content.Context;
import com.tink.pfmui.R;
import com.tink.pfmui.view.TinkToolbar;
import javax.inject.Inject;
import se.tink.commons.extensions.ContextUtils;

public class TinkOverviewToolbarTheme implements TinkToolbar.Theme {

	private final Context context;

	private ToolbarTextTheme actionButtonTheme;

	@Inject
	public TinkOverviewToolbarTheme(Context context) {
		this.context = context;
	}

	@Override
	public int getBackgroundColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_transparentColor);
	}

	@Override
	public int getTitleColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_textColorPrimary);
	}

	@Override
	public ToolbarTextTheme getActionButtonTheme() {
		if (actionButtonTheme == null) {
			actionButtonTheme = new TinkDefaultToolbarActionButtonTheme(context) {
				@Override
				public int getTextColor() {
					return ContextUtils.getColorFromAttr(context, R.attr.tink_textColorAction);
				}
			};
		}
		return actionButtonTheme;
	}

	@Override
	public float getElevationDP() {
		return 0;
	}
}
