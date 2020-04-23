package com.tink.pfmui.theme;

import android.content.Context;
import com.tink.pfmui.R;
import com.tink.pfmui.view.TinkToolbar;
import javax.inject.Inject;
import se.tink.commons.extensions.ContextUtils;

public class TinkDefaultToolbarTheme implements TinkToolbar.Theme {

	protected final Context context;

	private ToolbarTextTheme actionButtonTheme;
	private ToolbarTextTheme titleTheme;

	@Inject
	public TinkDefaultToolbarTheme(Context context) {
		this.context = context;
	}

	@Override
	public int getBackgroundColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_colorPrimary);
	}

	@Override
	public float getElevationDP() {
		return 4.0f;
	}

	@Override
	public int getTitleColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_colorOnPrimary);
	}

	@Override
	public ToolbarTextTheme getActionButtonTheme() {
		if (actionButtonTheme == null) {
			actionButtonTheme = new TinkDefaultToolbarActionButtonTheme(context);
		}
		return actionButtonTheme;
	}
}
