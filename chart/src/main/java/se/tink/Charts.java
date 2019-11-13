package se.tink;


import android.content.Context;
import android.graphics.Typeface;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;
import java.util.List;
import java.util.Locale;
import se.tink.android.charts.R;
import se.tink.barchart.BarChartAreaAdapter;
import se.tink.barchart.VerticalBarChartArea;
import se.tink.commons.extensions.ContextUtils;
import se.tink.core.models.Labels;
import se.tink.core.models.PeriodBalance;
import se.tink.core.models.VerticalBarChart;
import se.tink.enums.XLabelAlignment;
import se.tink.enums.YLabelAlignment;
import se.tink.labels.LabelsView;
import se.tink.linechart.BalanceLineChartArea;
import se.tink.managers.Manager;
import se.tink.utils.ScreenUtils;

public class Charts {

	private static Charts sharedInstance = new Charts();

	public static Charts sharedInstance() {
		return sharedInstance;
	}

	private static final int HEADER_LABEL = View.generateViewId();

	public void updateStatisticsForLineChart(
		BalanceLineChartArea chart,
		List<PeriodBalance> items,
		List<PeriodBalance> averageDataForAMonth) {

		chart.setData(items, averageDataForAMonth);
		chart.invalidate();
	}

	public void setupBarChart(
		Context context, String currencyCode, Locale locale, String timezoneCode,
		VerticalBarChart barChart) {

		double[] minAndMax = Manager.sharedInstance().getMinAndMaxValue(barChart.getItems());
		double maxValue = minAndMax[0];
		double minValue = minAndMax[1];
		BarChartAreaAdapter adapter = new BarChartAreaAdapter(barChart.getItems(), minValue,
			maxValue);

		VerticalBarChartArea chart = barChart.getBarChartView();
		chart.initialize(context, locale, timezoneCode, currencyCode);
		chart.setAdapter(adapter);
		chart.setPaddingBetweenBars(
			ScreenUtils.dpToPixels(context, barChart.getPaddingBetweenBars()));
		chart.setBackgroundColor(barChart.getBackgroundColor());
		chart.setBarColor(barChart.getBarColor());
		chart.setNegativeBarColor(barChart.getNegativeBarColor());
		chart.setZeroLineColor(barChart.getZerolineColor());
		chart.setMeanValueColor(barChart.getMeanValueColor());
		chart.setupAmountTextPaint(barChart.getTypefaceAmountLabel(),
			barChart.getTextColorAmountLabel(),
			barChart.getTextSizeAmountLabel(), barChart.getTextLineHeightAmountLabel(),
			barChart.getTextSpacingAmountLabel());
		chart.setupDateTextPaint(barChart.getTypefaceDateLabel(), barChart.getTextColorDateLabel(),
			barChart.getTextSizeDateLabel(), barChart.getTextLineHeightDateLabel(),
			barChart.getTextSpacingDateLabel());
		chart.setShowXLines(barChart.isShowXLines());
		chart.setShowZeroLine(barChart.isShowZeroLine());
		chart.setShowMeanLine(barChart.isShowMeanLine());
		chart.setShowAmountLabels(barChart.isShowAmountLabels());
		chart.setShowPeriodLabels(barChart.isShowPeriodLabels());
		chart.setIncludeVerticalPadding(barChart.isIncludeVerticalPadding());
		chart.setAmountLabelsAboveBars(barChart.isAmountLabelsAboveBars());
		chart.setIsSelector(barChart.isSelector());
		chart.setCornerRadii(barChart.getCornerRadii());
		chart.setSelectedIndex(barChart.getSelectedIndex());
		chart.setSelectedBarColor(barChart.getSelectedBarColor());
		chart
			.setupAmountYLabelTextPaint(barChart.getTypefaceYLabel(), barChart.getYLabelTextColor(),
				barChart.getTextSizeYLabel(), barChart.getSpacingYLabel());

	}

	public void setupLineChart(
		BalanceLineChartArea lineChart,
		Context context,
		Locale locale,
		String currencyCode,
		String timezoneCode,
		boolean showXLabels,
		boolean showYLabels,
		boolean showDateMarker,
		int backgroundColor,
		int centerDateMarkColor,
		int zerolineColor,
		int areaGradientBottomColor,
		int areaGradientTopColor,
		int linePaintColor) {

		int transparentColor = ContextUtils.getColorFromAttr(context, R.attr.tink_transparentColor);

		setupLineChart(lineChart, context, currencyCode, locale, timezoneCode, showXLabels,
			showYLabels,
			showDateMarker,
			backgroundColor, centerDateMarkColor,
			zerolineColor, areaGradientBottomColor, areaGradientTopColor, linePaintColor, null, 0,
			0f,
			transparentColor, transparentColor, transparentColor);
	}

	public void setupLineChart(
		BalanceLineChartArea lineChart,
		Context context,
		String currencyCode,
		Locale locale,
		String timezoneCode,
		boolean showXLabels,
		boolean showYLabels,
		boolean showDateMarker,
		int backgroundColor,
		int centerDateMarkColor,
		int zerolineColor,
		int areaGradientBottomColor,
		int areaGradientTopColor,
		int linePaintColor,
		Typeface amountLabelsTypeface,
		int amountLabelsColor,
		float amountLabelsTextSize,
		int dateMarkerGradientBottom,
		int dateMarkerGradientTop50,
		int dateMarkerGradientTop80) {

		lineChart.initialize(context, currencyCode, locale, timezoneCode);
		lineChart.setMarkToday(showDateMarker);
		lineChart.setBackgroundColor(backgroundColor);
		lineChart.setDateMarkerCenterColor(centerDateMarkColor);
		lineChart.setZeroLinePaintColor(zerolineColor);
		lineChart.setAreaGradientBottomColor(areaGradientBottomColor);
		lineChart.setAreaGradientTopColor(areaGradientTopColor);
		lineChart.setLinePaint(linePaintColor);
		lineChart
			.setAmountLabelFontType(amountLabelsTypeface, amountLabelsColor, amountLabelsTextSize);
		lineChart.setTodayDotPaintGradientColors(dateMarkerGradientBottom, dateMarkerGradientTop50,
			dateMarkerGradientTop80);

		if (showXLabels) {
			lineChart.setShowXLabels(XLabelAlignment.BOTTOM_INSIDE_CHART_AREA);
		} else {
			lineChart.setShowXLabels(XLabelAlignment.NONE);
		}

		if (showYLabels) {
			lineChart.setShowYLabels(YLabelAlignment.RIGHT_INSIDE_CHARTAREA);
		} else {
			lineChart.setShowYLabels(YLabelAlignment.NONE);
		}
	}


	public void setupLabels(
		Context context,
		RelativeLayout view,
		Labels labels,
		int screenWidth,
		int paddingTop,
		int labelTwoPadding) {

		LabelsView oldLabelsView = (LabelsView) view.getChildAt(0);
		if (labels != null) {
			view.removeView(oldLabelsView);
		}

		LabelsView labelsView = new LabelsView(context);
		labelsView.initialize(context, labels, screenWidth, 0, paddingTop,
			Constants.OPTICAL_VERTICAL_ADJUSTMENT);
		labelsView.setId(IdUtils.getPieLabelsId());
		labelsView.updateScrolledForText2(0, labelTwoPadding);
		labelsView.setId(HEADER_LABEL);

		view.addView(labelsView);
	}
}

class IdUtils {

	private static int pieLabelsId = -1;

	public static int getPieLabelsId() {
		if (pieLabelsId == -1) {
			pieLabelsId = View.generateViewId();
		}

		return pieLabelsId;
	}
}

