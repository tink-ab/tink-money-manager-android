package com.tink.pfmsdk.theme;

import android.content.Context;
import androidx.core.content.ContextCompat;
import com.tink.pfmsdk.R;
import se.tink.commons.extensions.ContextUtils;

public class TinkExpenseStatusBarTheme extends TinkDefaultStatusBarTheme {

	public TinkExpenseStatusBarTheme(Context context) {
		super(context);
	}

	@Override
	public int getStatusBarColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_expensesDarkColor);
	}
}
