package com.tink.pfmsdk.theme;


import android.content.Context;
import androidx.core.content.ContextCompat;
import com.tink.pfmsdk.R;
import com.tink.pfmsdk.util.DimensionUtils;
import com.tink.pfmsdk.view.NanoTitle;
import com.tink.pfmsdk.view.TinkTabs;
import com.tink.pfmsdk.view.TinkTextView;
import javax.inject.Inject;
import se.tink.utils.ColorsUtils;

public class TinkIncomeTabsTheme extends TinkTabsTheme implements TinkTabs.Theme {

	private Context context;

	@Inject
	public TinkIncomeTabsTheme(Context context) {
		super(context);

		this.context = context;
	}

	@Override
	public int getBackgroundColor() {
		return ContextCompat.getColor(context, R.color.income);
	}

	@Override
	public int getMarkerColor() {
		int color = ContextCompat.getColor(context, R.color.colorOnIncome);
		return ColorsUtils.adjustAlpha(color, 0.4f);
	}

	@Override
	public TinkTextView.Theme getTabsTitle() {
		return new NanoTitle(context) {
			@Override
			public boolean toUpperCase() {
				return true;
			}

			@Override
			public float getSpacing() {
				return DimensionUtils.getEmFromDp(2);
			}
		};
	}

}
