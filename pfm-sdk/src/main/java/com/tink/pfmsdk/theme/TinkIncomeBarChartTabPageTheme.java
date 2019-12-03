package com.tink.pfmsdk.theme;


import android.content.Context;
import com.tink.pfmsdk.R;
import com.tink.pfmsdk.overview.charts.TabIncomeBarChartFragment;
import com.tink.pfmsdk.view.Hecto;
import com.tink.pfmsdk.view.NanoPrimary;
import com.tink.pfmsdk.view.Tera;
import com.tink.pfmsdk.view.TinkTextView.Theme;
import javax.inject.Inject;
import se.tink.commons.extensions.ContextUtils;

public class TinkIncomeBarChartTabPageTheme implements TabIncomeBarChartFragment.Theme {

	private Context context;

	@Inject
	public TinkIncomeBarChartTabPageTheme(Context context) {
		this.context = context;
	}

	@Override
	public Theme getTotalAmountTitle() {
		return new Tera(context);
	}

	@Override
	public Theme getPageInformation() {
		return new Hecto(context);
	}

	@Override
	public Theme getBarChartsAmountLabels() {
		return new NanoPrimary(context);
	}

	@Override
	public Theme getBarChartsDateLabels() {
		return new AxisLabel(context);
	}

	@Override
	public Theme getBarYLabel() {
		return new AxisLabel(context);
	}

	@Override
	public int getChartBackgroundColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_transparentColor);
	}

	@Override
	public int getZeroLineColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_transparentColor);
	}

	@Override
	public int get6MonthBarColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_incomeColor);
	}

	@Override
	public int get6MonthNegativeBarColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_incomeLightColor);
	}

	@Override
	public int getMeanValueColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_chartMeanValueColor);
	}

	@Override
	public int get1YearBarColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_incomeColor);
	}

	@Override
	public int get1YearNegativeBarColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_incomeLightColor);
	}

	@Override
	public TinkIncomeToolbarTheme getToolbarTheme() {
		return new TinkIncomeToolbarTheme(context);
	}

	public StatusBarTheme getStatusBarTheme() {
		return new TinkIncomeStatusBarTheme(context);
	}
}
