package com.tink.pfmsdk.theme;

import android.content.Context;
import androidx.core.content.ContextCompat;
import com.tink.pfmsdk.R;

public class TinkIncomeStatusBarTheme extends TinkDefaultStatusBarTheme {

	public TinkIncomeStatusBarTheme(Context context) {
		super(context);
	}

	@Override
	public int getStatusBarColor() {
		return ContextCompat.getColor(context, R.color.incomeDark);
	}
}
