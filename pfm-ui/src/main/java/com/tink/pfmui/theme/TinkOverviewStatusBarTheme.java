package com.tink.pfmui.theme;

import android.content.Context;
import androidx.core.content.ContextCompat;
import com.tink.pfmui.R;

public class TinkOverviewStatusBarTheme extends TinkDefaultStatusBarTheme {

	public TinkOverviewStatusBarTheme(Context context) {
		super(context);
	}

	@Override
	public int getStatusBarColor() {
		return ContextCompat.getColor(context, R.color.tink_status_bar_no_toolbar);
	}
}
