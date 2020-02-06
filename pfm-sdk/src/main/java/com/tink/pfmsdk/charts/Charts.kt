package com.tink.pfmsdk.charts

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.RelativeLayout
import com.tink.pfmsdk.R
import com.tink.pfmsdk.charts.models.Labels
import com.tink.pfmsdk.charts.models.PeriodBalance
import com.tink.pfmsdk.charts.models.VerticalBarChart
import com.tink.pfmsdk.util.ScreenUtils
import se.tink.commons.extensions.getColorFromAttr
import java.util.Locale

private val HEADER_LABEL = View.generateViewId()

internal object Charts {

    @JvmStatic
    fun updateStatisticsForLineChart(
        chart: BalanceLineChartArea,
        items: List<PeriodBalance?>?,
        averageDataForAMonth: List<PeriodBalance?>?
    ) {
        chart.setData(items, averageDataForAMonth)
        chart.invalidate()
    }

    @JvmStatic
    fun setupBarChart(
        context: Context?,
        currencyCode: String?,
        locale: Locale?,
        timezoneCode: String?,
        barChart: VerticalBarChart
    ) {
        val minAndMax =
            Manager.sharedInstance().getMinAndMaxValue(barChart.items)
        val maxValue = minAndMax[0]
        val minValue = minAndMax[1]
        val adapter = BarChartAreaAdapter(
            barChart.items, minValue,
            maxValue
        )
        val chart = barChart.barChartView
        chart.initialize(context, locale, timezoneCode, currencyCode)
        chart.setAdapter(adapter)
        chart.setPaddingBetweenBars(
            ScreenUtils.dpToPixels(context, barChart.paddingBetweenBars)
        )
        chart.setBackgroundColor(barChart.backgroundColor)
        chart.barColor = barChart.barColor
        chart.negativeBarColor = barChart.negativeBarColor
        chart.setZeroLineColor(barChart.zerolineColor)
        chart.setMeanValueColor(barChart.meanValueColor)
        chart.setupAmountTextPaint(
            barChart.typefaceAmountLabel,
            barChart.textColorAmountLabel,
            barChart.textSizeAmountLabel, barChart.textLineHeightAmountLabel,
            barChart.textSpacingAmountLabel
        )
        chart.setupDateTextPaint(
            barChart.typefaceDateLabel, barChart.textColorDateLabel,
            barChart.textSizeDateLabel, barChart.textLineHeightDateLabel,
            barChart.textSpacingDateLabel
        )
        chart.setShowXLines(barChart.isShowXLines)
        chart.setShowZeroLine(barChart.isShowZeroLine)
        chart.setShowMeanLine(barChart.isShowMeanLine)
        chart.setShowAmountLabels(barChart.isShowAmountLabels)
        chart.setShowPeriodLabels(barChart.isShowPeriodLabels)
        chart.setIncludeVerticalPadding(barChart.isIncludeVerticalPadding)
        chart.setAmountLabelsAboveBars(barChart.isAmountLabelsAboveBars)
        chart.setIsSelector(barChart.isSelector)
        chart.setCornerRadii(barChart.cornerRadii)
        chart.selectedIndex = barChart.selectedIndex
        chart.setSelectedBarColor(barChart.selectedBarColor)
        chart
            .setupAmountYLabelTextPaint(
                barChart.typefaceYLabel, barChart.yLabelTextColor,
                barChart.textSizeYLabel, barChart.spacingYLabel
            )
    }

    fun setupLineChart(
        lineChart: BalanceLineChartArea,
        context: Context,
        locale: Locale?,
        currencyCode: String?,
        timezoneCode: String?,
        showXLabels: Boolean,
        showYLabels: Boolean,
        showDateMarker: Boolean,
        backgroundColor: Int,
        centerDateMarkColor: Int,
        zerolineColor: Int,
        areaGradientBottomColor: Int,
        areaGradientTopColor: Int,
        linePaintColor: Int
    ) {
        val transparentColor =
            context.getColorFromAttr(R.attr.tink_transparentColor)
        setupLineChart(
            lineChart, context, currencyCode, locale, timezoneCode, showXLabels,
            showYLabels,
            showDateMarker,
            backgroundColor, centerDateMarkColor,
            zerolineColor, areaGradientBottomColor, areaGradientTopColor, linePaintColor, null, 0,
            0f,
            transparentColor, transparentColor, transparentColor
        )
    }

    @JvmStatic
    fun setupLineChart(
        lineChart: BalanceLineChartArea,
        context: Context?,
        currencyCode: String?,
        locale: Locale?,
        timezoneCode: String?,
        showXLabels: Boolean,
        showYLabels: Boolean,
        showDateMarker: Boolean,
        backgroundColor: Int,
        centerDateMarkColor: Int,
        zerolineColor: Int,
        areaGradientBottomColor: Int,
        areaGradientTopColor: Int,
        linePaintColor: Int,
        amountLabelsTypeface: Typeface?,
        amountLabelsColor: Int,
        amountLabelsTextSize: Float,
        dateMarkerGradientBottom: Int,
        dateMarkerGradientTop50: Int,
        dateMarkerGradientTop80: Int
    ) {
        lineChart.initialize(context, currencyCode, locale, timezoneCode)
        lineChart.markToday = showDateMarker
        lineChart.setBackgroundColor(backgroundColor)
        lineChart.setDateMarkerCenterColor(centerDateMarkColor)
        lineChart.setZeroLinePaintColor(zerolineColor)
        lineChart.areaGradientBottomColor = areaGradientBottomColor
        lineChart.areaGradientTopColor = areaGradientTopColor
        lineChart.setLinePaint(linePaintColor)
        lineChart
            .setAmountLabelFontType(amountLabelsTypeface, amountLabelsColor, amountLabelsTextSize)
        lineChart.setTodayDotPaintGradientColors(
            dateMarkerGradientBottom, dateMarkerGradientTop50,
            dateMarkerGradientTop80
        )
        if (showXLabels) {
            lineChart.setShowXLabels(XLabelAlignment.BOTTOM_INSIDE_CHART_AREA)
        } else {
            lineChart.setShowXLabels(XLabelAlignment.NONE)
        }
        if (showYLabels) {
            lineChart.setShowYLabels(YLabelAlignment.RIGHT_INSIDE_CHARTAREA)
        } else {
            lineChart.setShowYLabels(YLabelAlignment.NONE)
        }
    }

    @JvmStatic
    fun setupLabels(
        context: Context?,
        view: RelativeLayout,
        labels: Labels?,
        screenWidth: Int,
        paddingTop: Int,
        labelTwoPadding: Int
    ) {
        val oldLabelsView = view.getChildAt(0) as? LabelsView
        if (oldLabelsView != null) {
            view.removeView(oldLabelsView)
        }
        val labelsView = LabelsView(context)
        labelsView.initialize(
            context, labels, screenWidth, 0, paddingTop,
            Constants.OPTICAL_VERTICAL_ADJUSTMENT
        )
        labelsView.id = IdUtils.pieLabelsId
        labelsView.updateScrolledForText2(0, labelTwoPadding)
        labelsView.id = HEADER_LABEL
        view.addView(labelsView)
    }
}

internal object IdUtils {
    var pieLabelsId = -1
        get() {
            if (field == -1) {
                field = View.generateViewId()
            }
            return field
        }
        private set
}