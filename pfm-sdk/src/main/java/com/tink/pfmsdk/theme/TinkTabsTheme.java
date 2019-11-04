package com.tink.pfmsdk.theme;


import android.content.Context;
import androidx.core.content.ContextCompat;
import com.tink.pfmsdk.R;
import com.tink.pfmsdk.view.NanoTitle;
import com.tink.pfmsdk.view.TinkTabs;
import com.tink.pfmsdk.view.TinkTextView;
import javax.inject.Inject;

public class TinkTabsTheme implements TinkTabs.Theme {

	private Context context;

	@Inject
	public TinkTabsTheme(Context context) {
		this.context = context;
	}

	@Override
	public int getBackgroundColor() {
		return ContextCompat.getColor(context, R.color.expenses);
	}

	@Override
	public int getMarkerColor() {
		return ContextCompat.getColor(context, R.color.expensesDark);
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
