package com.tink.pfmsdk.theme;

import android.content.Context;
import androidx.core.content.ContextCompat;
import com.tink.pfmsdk.R;
import com.tink.pfmsdk.view.TinkToolbar;
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
		return ContextCompat.getColor(context, R.color.colorPrimary);
	}

	@Override
	public float getElevationDP() {
		return 4.0f;
	}

	@Override
	public int getTitleColor() {
		return ContextUtils.getColorCompat(context, R.color.colorOnPrimary);
	}

	@Override
	public ToolbarTextTheme getActionButtonTheme() {
		if (actionButtonTheme == null) {
			actionButtonTheme = new TinkDefaultToolbarActionButtonTheme(context);
		}
		return actionButtonTheme;
	}
}
