package com.tink.pfmui.theme;


import android.content.Context;
import com.tink.pfmui.R;
import com.tink.pfmui.util.DimensionUtils;
import com.tink.pfmui.view.NanoTitle;
import com.tink.pfmui.view.TinkTabs;
import com.tink.pfmui.view.TinkTextView;
import javax.inject.Inject;
import org.jetbrains.annotations.NotNull;
import se.tink.commons.extensions.ContextUtils;

public class TinkIncomeTabsTheme extends TinkTabsTheme implements TinkTabs.Theme {

	private Context context;

	@Inject
	public TinkIncomeTabsTheme(Context context) {
		super(context);

		this.context = context;
	}

	@Override
	public int getBackgroundColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_incomeColor);
	}

	@Override
	public int getMarkerColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_colorOnIncome);
	}

	@NotNull
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

			@Override
			public int getTextColor() {
				return ContextUtils.getColorFromAttr(context, R.attr.tink_colorOnIncome);
			}
		};
	}

}
