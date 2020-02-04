package com.tink.pfmsdk.charts.models;


import android.graphics.Typeface;
import com.tink.pfmsdk.charts.VerticalBarChartArea;
import java.util.List;

public class VerticalBarChart {

	private VerticalBarChartArea barChartView;
	private List<PeriodBalance> items;
	private boolean showAmountLabels;
	private int backgroundColor;
	private int barColor;
	private int negativeBarColor;
	private int zerolineColor;
	private int meanValueColor;
	private int paddingBetweenBars;
	private Typeface typefaceAmountLabel;
	private int textColorAmountLabel;
	private float textSizeAmountLabel;
	private float textLineHeightAmountLabel;
	private float textSpacingAmountLabel;
	private Typeface typefaceDateLabel;
	private int textColorDateLabel;
	private float textSizeDateLabel;
	private float textLineHeightDateLabel;
	private float textSpacingDateLabel;
	private boolean showXLines; // if this is true, labels on Y-axis is not shown
	private boolean showZeroLine;
	private boolean showMeanLine;
	private boolean showPeriodLabels;
	private boolean includeVerticalPadding;
	private boolean amountLabelsAboveBars;
	private boolean isSelector;
	private int cornerRadii;
	private int selectedIndex = -1;
	private int selectedBarColor = -1;
	private Typeface typefaceYLabel;
	private int yLabelTextColor;
	private float textSizeYLabel;
	private float spacingYLabel;

	public VerticalBarChartArea getBarChartView() {
		return barChartView;
	}

	public void setBarChartView(VerticalBarChartArea barChartView) {
		this.barChartView = barChartView;
	}

	public List<PeriodBalance> getItems() {
		return items;
	}

	public void setItems(List<PeriodBalance> items) {
		this.items = items;
	}

	public boolean isShowAmountLabels() {
		return showAmountLabels;
	}

	public void setShowAmountLabels(boolean showAmountLabels) {
		this.showAmountLabels = showAmountLabels;
	}

	public int getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public int getBarColor() {
		return barColor;
	}

	public void setBarColor(int barColor) {
		this.barColor = barColor;
	}

	public int getNegativeBarColor() {
		return negativeBarColor;
	}

	public void setNegativeBarColor(int negativeBarColor) {
		this.negativeBarColor = negativeBarColor;
	}

	public int getZerolineColor() {
		return zerolineColor;
	}

	public void setZerolineColor(int zerolineColor) {
		this.zerolineColor = zerolineColor;
	}

	public int getMeanValueColor() {
		return meanValueColor;
	}

	public void setMeanValueColor(int meanValueColor) {
		this.meanValueColor = meanValueColor;
	}

	public int getPaddingBetweenBars() {
		return paddingBetweenBars;
	}

	public void setPaddingBetweenBars(int paddingBetweenBars) {
		this.paddingBetweenBars = paddingBetweenBars;
	}

	public Typeface getTypefaceAmountLabel() {
		return typefaceAmountLabel;
	}

	public void setTypefaceAmountLabel(Typeface typefaceAmountLabel) {
		this.typefaceAmountLabel = typefaceAmountLabel;
	}

	public int getTextColorAmountLabel() {
		return textColorAmountLabel;
	}

	public void setTextColorAmountLabel(int textColorAmountLabel) {
		this.textColorAmountLabel = textColorAmountLabel;
	}

	public float getTextSizeAmountLabel() {
		return textSizeAmountLabel;
	}

	public void setTextSizeAmountLabel(float textSizeAmountLabel) {
		this.textSizeAmountLabel = textSizeAmountLabel;
	}

	public float getTextLineHeightAmountLabel() {
		return textLineHeightAmountLabel;
	}

	public void setTextLineHeightAmountLabel(float textLineHeightAmountLabel) {
		this.textLineHeightAmountLabel = textLineHeightAmountLabel;
	}

	public float getTextSpacingAmountLabel() {
		return textSpacingAmountLabel;
	}

	public void setTextSpacingAmountLabel(float textSpacingAmountLabel) {
		this.textSpacingAmountLabel = textSpacingAmountLabel;
	}

	public Typeface getTypefaceDateLabel() {
		return typefaceDateLabel;
	}

	public void setTypefaceDateLabel(Typeface typefaceDateLabel) {
		this.typefaceDateLabel = typefaceDateLabel;
	}

	public int getTextColorDateLabel() {
		return textColorDateLabel;
	}

	public void setTextColorDateLabel(int textColorDateLabel) {
		this.textColorDateLabel = textColorDateLabel;
	}

	public float getTextSizeDateLabel() {
		return textSizeDateLabel;
	}

	public void setTextSizeDateLabel(float textSizeDateLabel) {
		this.textSizeDateLabel = textSizeDateLabel;
	}

	public float getTextLineHeightDateLabel() {
		return textLineHeightDateLabel;
	}

	public void setTextLineHeightDateLabel(float textLineHeightDateLabel) {
		this.textLineHeightDateLabel = textLineHeightDateLabel;
	}

	public float getTextSpacingDateLabel() {
		return textSpacingDateLabel;
	}

	public void setTextSpacingDateLabel(float textSpacingDateLabel) {
		this.textSpacingDateLabel = textSpacingDateLabel;
	}

	public boolean isShowXLines() {
		return showXLines;
	}

	public void setShowXLines(boolean showXLines) {
		this.showXLines = showXLines;
	}

	public boolean isShowZeroLine() {
		return showZeroLine;
	}

	public void setShowZeroLine(boolean showZeroLine) {
		this.showZeroLine = showZeroLine;
	}

	public boolean isShowMeanLine() {
		return showMeanLine;
	}

	public void setShowMeanLine(boolean showMeanLine) {
		this.showMeanLine = showMeanLine;
	}

	public boolean isShowPeriodLabels() {
		return showPeriodLabels;
	}

	public void setShowPeriodLabels(boolean showPeriodLabels) {
		this.showPeriodLabels = showPeriodLabels;
	}

	public boolean isIncludeVerticalPadding() {
		return includeVerticalPadding;
	}

	public void setIncludeVerticalPadding(boolean includeVerticalPadding) {
		this.includeVerticalPadding = includeVerticalPadding;
	}

	public boolean isAmountLabelsAboveBars() {
		return amountLabelsAboveBars;
	}

	public void setAmountLabelsAboveBars(boolean amountLabelsAboveBars) {
		this.amountLabelsAboveBars = amountLabelsAboveBars;
	}

	public boolean isSelector() {
		return isSelector;
	}

	public void setSelector(boolean selector) {
		isSelector = selector;
	}

	public int getCornerRadii() {
		return cornerRadii;
	}

	public void setCornerRadii(int cornerRadii) {
		this.cornerRadii = cornerRadii;
	}

	public int getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	public int getSelectedBarColor() {
		return selectedBarColor;
	}

	public void setSelectedBarColor(int selectedBarColor) {
		this.selectedBarColor = selectedBarColor;
	}

	public Typeface getTypefaceYLabel() {
		return typefaceYLabel;
	}

	public void setTypefaceYLabel(Typeface typefaceYLabel) {
		this.typefaceYLabel = typefaceYLabel;
	}

	public int getYLabelTextColor() {
		return yLabelTextColor;
	}

	public void setYLabelTextColor(int YLabelTextColor) {
		this.yLabelTextColor = YLabelTextColor;
	}

	public float getTextSizeYLabel() {
		return textSizeYLabel;
	}

	public void setTextSizeYLabel(float textSizeYLabel) {
		this.textSizeYLabel = textSizeYLabel;
	}

	public float getSpacingYLabel() {
		return spacingYLabel;
	}

	public void setSpacingYLabel(float spacingYLabel) {
		this.spacingYLabel = spacingYLabel;
	}
}
