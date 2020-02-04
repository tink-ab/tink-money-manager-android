package com.tink.pfmsdk.theme;


import android.content.Context;
import com.tink.pfmsdk.R;
import com.tink.pfmsdk.overview.charts.TabLeftToSpendFragment;
import com.tink.pfmsdk.util.ColorsUtils;
import com.tink.pfmsdk.view.Hecto;
import com.tink.pfmsdk.view.NanoPrimary;
import com.tink.pfmsdk.view.Tera;
import com.tink.pfmsdk.view.TinkTextView;
import com.tink.pfmsdk.view.TinkTextView.Theme;
import com.tink.pfmsdk.view.TinkToolbar;
import javax.inject.Inject;
import se.tink.commons.extensions.ContextUtils;

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
		return ContextUtils.getColorFromAttr(context, R.attr.tink_transparentColor);
	}

	@Override
	public int getCenterDateMarkColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_leftToSpendColor);
	}

	@Override
	public int getLineChartZeroLine() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_transparentColor);
	}

	@Override
	public int getZeroLineColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_transparentColor);
	}

	@Override
	public int getAreaGradientBottomColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_transparentColor);
	}

	@Override
	public int getAreaGradientTopColor() {
		int color = ContextUtils.getColorFromAttr(context, R.attr.tink_leftToSpendColor);
		return ColorsUtils.adjustAlpha(color, 0.25f);
	}

	@Override
	public int getLinePaintColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_leftToSpendColor);
	}

	@Override
	public int getDateMarkerGradientBottom() {
		int color = ContextUtils.getColorFromAttr(context, R.attr.tink_leftToSpendColor);
		return ColorsUtils.adjustAlpha(color, 0.25f);
	}

	@Override
	public int getDateMarkerGradientTop50() {
		int color = ContextUtils.getColorFromAttr(context, R.attr.tink_leftToSpendColor);
		return ColorsUtils.adjustAlpha(color, 0.25f);
	}

	@Override
	public int getDateMarkerGradientTop80() {
		int color = ContextUtils.getColorFromAttr(context, R.attr.tink_leftToSpendColor);
		return ColorsUtils.adjustAlpha(color, 0.25f);
	}

	@Override
	public int get6MonthBarColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_leftToSpendColor);
	}

	@Override
	public int get6MonthNegativeBarColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_leftToSpendLightColor);
	}

	@Override
	public int getMeanValueColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_chartMeanValueColor);
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

	@Override
	public int getAverageLineColor() {
		return ContextUtils.getColorFromAttr(context, R.attr.tink_transparentColor);
	}

	@Override
	public TinkToolbar.Theme getToolbarTheme() {
		return new TinkLeftToSpendToolbarTheme(context);
	}

	public StatusBarTheme getStatusBarTheme() {
		return new TinkLeftToSpendStatusBarTheme(context);
	}
}
