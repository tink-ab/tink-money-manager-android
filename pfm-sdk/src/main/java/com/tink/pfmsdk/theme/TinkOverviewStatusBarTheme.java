package com.tink.pfmsdk.theme;

import android.content.Context;
import androidx.core.content.ContextCompat;
import com.tink.pfmsdk.R;

public class TinkOverviewStatusBarTheme extends TinkDefaultStatusBarTheme {

	public TinkOverviewStatusBarTheme(Context context) {
		super(context);
	}

	@Override
	public int getStatusBarColor() {
		return ContextCompat.getColor(context, R.color.status_bar_no_toolbar);
	}
}
