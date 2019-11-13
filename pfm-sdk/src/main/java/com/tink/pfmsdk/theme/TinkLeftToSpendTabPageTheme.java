package com.tink.pfmsdk.theme;


import android.content.Context;
import androidx.core.content.ContextCompat;
import com.tink.pfmsdk.R;
import com.tink.pfmsdk.overview.charts.TabLeftToSpendFragment;
import com.tink.pfmsdk.view.Hecto;
import com.tink.pfmsdk.view.NanoPrimary;
import com.tink.pfmsdk.view.Tera;
import com.tink.pfmsdk.view.TinkTextView;
import com.tink.pfmsdk.view.TinkTextView.Theme;
import com.tink.pfmsdk.view.TinkToolbar;
import javax.inject.Inject;
import se.tink.commons.extensions.ContextUtils;
import se.tink.utils.ColorsUtils;

public class TinkLeftToSpendTabPageTheme implements TabLeftToSpendFragment.Theme {

	private Context context;

	@Inject
	public TinkLeftToSpendTabPageTheme(Context context) {
		this.context = context;
	}

	@Override
	public TinkTextView.Theme getTotalAmountTitle() {
		return new Tera(context);
	}

	@Override
	public TinkTextView.Theme getPageInformation() {
		return new Hecto(context);
	}

	@Override
	public TinkTextView.Theme getBarChartsAmountLabels() {
		return new NanoPrimary(context);
	}

	@Override
	public TinkTextView.Theme getBarChartsDateLabels() {
		return new AxisLabel(context);
	}

	@Override
	public TinkTextView.Theme getLineChartAmountLabels() {
		return new AxisLabel(context);
	}

	@Override
	public Theme getBarYLabel() {
		return new AxisLabel(context);
	}

	@Override
	public int getChartBackgroundColor() {
		return ContextCompat.getColor(context, R.color.leftToSpendBarChart_chartBackgroundColor);
	}

	@Override
	public int getCenterDateMarkColor() {
		return ContextCompat.getColor(context, R.color.leftToSpendBarChart_centerDateMarkColor);
	}

	@Override
	public int getLineChartZeroLine() {
		return ContextCompat.getColor(context, R.color.leftToSpendBarChart_lineChartZeroLine);
	}

	@Override
	public int getZeroLineColor() {
		return ContextCompat.getColor(context, R.color.leftToSpendBarChart_zeroLineColor);
	}

	@Override
	public int getAreaGradientBottomColor() {
		return ContextCompat.getColor(context, R.color.leftToSpendBarChart_areaGradientBottomColor);
	}

	@Override
	public int getAreaGradientTopColor() {
		int color = ContextCompat.getColor(context, R.color.leftToSpendBarChart_areaGradientTopColor);
		return ColorsUtils.adjustAlpha(color, 0.25f);
	}

	@Override
	public int getLinePaintColor() {
		return ContextCompat.getColor(context, R.color.leftToSpendBarChart_linePaintColor);
	}

	@Override
	public int getDateMarkerGradientBottom() {
		int color = ContextCompat.getColor(context, R.color.leftToSpendBarChart_dateMarkerGradientBottom);
		return ColorsUtils.adjustAlpha(color, 0.25f);
	}

	@Override
	public int getDateMarkerGradientTop50() {
		int color = ContextCompat.getColor(context, R.color.leftToSpendBarChart_dateMarkerGradientTop50);
		return ColorsUtils.adjustAlpha(color, 0.25f);
	}

	@Override
	public int getDateMarkerGradientTop80() {
		int color = ContextCompat.getColor(context, R.color.leftToSpendBarChart_dateMarkerGradientTop80);
		return ColorsUtils.adjustAlpha(color, 0.25f);
	}

	@Override
	public int get6MonthBarColor() {
		return ContextCompat.getColor(context, R.color.leftToSpendBarChart_sixMonthBarColor);
	}

	@Override
	public int get6MonthNegativeBarColor() {
		return ContextCompat.getColor(context, R.color.leftToSpendBarChart_sixMonthNegativeBarColor);
	}

	@Override
	public int getMeanValueColor() {
		return ContextCompat.getColor(context, R.color.leftToSpendBarChart_meanValueColor);
	}

	@Override
	public int get1YearBarColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_leftToSpendColor);
	}

	@Override
	public int get1YearNegativeBarColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_leftToSpendLightColor);
	}

	@Override
	public Theme getXLabelTheme() {
		return new AxisLabel(context);
	}

	// TODO: PFMSDK
//	@Override
//	public TinkIconView.Theme getTutorialMarkerTheme() {
//		return new TinkPlainIconTheme(context);
//	}

	@Override
	public int getAverageLineColor() {
		return ContextCompat.getColor(context, R.color.chart_mean_value_color);
	}

	@Override
	public TinkToolbar.Theme getToolbarTheme() {
		return new TinkLeftToSpendToolbarTheme(context);
	}

	public StatusBarTheme getStatusBarTheme() {
		return new TinkLeftToSpendStatusBarTheme(context);
	}
}
