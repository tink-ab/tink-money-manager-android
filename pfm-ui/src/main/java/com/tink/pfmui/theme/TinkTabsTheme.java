package com.tink.pfmui.theme;


import android.content.Context;
import com.tink.pfmui.R;
import com.tink.pfmui.view.NanoTitle;
import com.tink.pfmui.view.TinkTabs;
import com.tink.pfmui.view.TinkTextView;
import javax.inject.Inject;
import org.jetbrains.annotations.NotNull;
import se.tink.commons.extensions.ContextUtils;

public class TinkTabsTheme implements TinkTabs.Theme {

	private Context context;

	@Inject
	TinkTabsTheme(Context context) {
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

	@NotNull
	@Override
	public TinkTextView.Theme getTabsTitle() {
		return new NanoTitle(context);
	}

}
