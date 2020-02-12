package com.tink.pfmsdk.theme;

import android.content.Context;
import androidx.core.content.ContextCompat;
import com.tink.pfmsdk.R;
import com.tink.pfmsdk.view.TinkToolbar;
import javax.inject.Inject;
import se.tink.commons.extensions.ContextUtils;

public class TinkLeftToSpendToolbarTheme implements TinkToolbar.Theme {

	private final Context context;

	private ToolbarTextTheme actionButtonTheme;
	private ToolbarTextTheme titleTheme;

	@Inject
	public TinkLeftToSpendToolbarTheme(Context context) {
		this.context = context;
	}

	@Override
	public int getBackgroundColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_leftToSpendColor);
	}

	@Override
	public int getTitleColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_colorOnLeftToSpend);
	}

	@Override
	public ToolbarTextTheme getActionButtonTheme() {
		if (actionButtonTheme == null) {
			actionButtonTheme = new TinkDefaultToolbarActionButtonTheme(context) {
				@Override
				public int getTextColor() {
					return ContextUtils.getColorFromAttr(context, R.attr.tink_colorOnLeftToSpend);
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
