package com.tink.pfmsdk.theme;

import android.content.Context;
import androidx.core.content.ContextCompat;
import com.tink.pfmsdk.R;

public class TinkExpenseStatusBarTheme extends TinkDefaultStatusBarTheme {

	public TinkExpenseStatusBarTheme(Context context) {
		super(context);
	}

	@Override
	public int getStatusBarColor() {
		return ContextCompat.getColor(context, R.color.expensesDark);
	}
}
