package com.tink.pfmsdk.theme;


import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import com.tink.pfmsdk.R;
import com.tink.pfmsdk.overview.charts.TabIncomeBarChartFragment;
import com.tink.pfmsdk.view.Hecto;
import com.tink.pfmsdk.view.NanoPrimary;
import com.tink.pfmsdk.view.Tera;
import com.tink.pfmsdk.view.TinkTextView.Theme;
import javax.inject.Inject;

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
		return ContextCompat.getColor(context, R.color.incomeBarChart_chartBackgroundColor);
	}

	@Override
	public int getZeroLineColor() {
		return ContextCompat.getColor(context, R.color.incomeBarChart_zeroLineColor);
	}

	@Override
	public int get6MonthBarColor() {
		return ContextCompat.getColor(context, R.color.incomeBarChart_sixMonthBarColor);
	}

	@Override
	public int get6MonthNegativeBarColor() {
		return ContextCompat.getColor(context, R.color.incomeBarChart_sixMonthNegativeBarColor);
	}

	@Override
	public int getMeanValueColor() {
		return ContextCompat.getColor(context, R.color.incomeBarChart_meanValueColor);
	}

	@Override
	public int get1YearBarColor() {
		return ContextCompat.getColor(context, R.color.incomeBarChart_twelveMonthBarColor);
	}

	@Override
	public int get1YearNegativeBarColor() {
		return ContextCompat.getColor(context, R.color.incomeBarChart_twelveMonthNegativeBarColor);
	}

	// TODO: PFMSDK
//	@Override
//	public TinkIconView.Theme getIconTheme() {
//		return new TinkPlainIconTheme(context) {
//			@Override
//			public float getIconSize() {
//				return context.getResources().getDimension(R.dimen.icon_size_small);
//			}
//
//			@Override
//			public int getIconColor() {
//				return ContextCompat.getColor(context, R.color.incomeDark);
//			}
//		};
//	}

	@Override
	public TinkIncomeToolbarTheme getToolbarTheme() {
		return new TinkIncomeToolbarTheme(context);
	}

	public StatusBarTheme getStatusBarTheme() {
		return new TinkIncomeStatusBarTheme(context);
	}
}
