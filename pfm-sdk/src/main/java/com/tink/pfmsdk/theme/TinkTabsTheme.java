package com.tink.pfmsdk.theme;


import android.content.Context;
import androidx.core.content.ContextCompat;
import com.tink.pfmsdk.R;
import com.tink.pfmsdk.view.NanoTitle;
import com.tink.pfmsdk.view.TinkTabs;
import com.tink.pfmsdk.view.TinkTextView;
import javax.inject.Inject;
import se.tink.commons.extensions.ContextUtils;

public class TinkTabsTheme implements TinkTabs.Theme {

	private Context context;

	@Inject
	public TinkTabsTheme(Context context) {
		this.context = context;
	}

	@Override
	public int getBackgroundColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_expensesColor);
	}

	@Override
	public int getMarkerColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_expensesDarkColor);
	}

	@Override
	public int getElevation() {
		return 4;
	}

	@Override
	public TinkTextView.Theme getTabsTitle() {
		return new NanoTitle(context);
	}

}
