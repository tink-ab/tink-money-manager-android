package com.tink.pfmsdk.charts.models

import android.graphics.Typeface
import com.tink.pfmsdk.charts.VerticalBarChartArea

internal class VerticalBarChart {
    var barChartView: VerticalBarChartArea? = null
    var items: List<PeriodBalance>? = null
    var isShowAmountLabels = false
    var backgroundColor = 0
    var barColor = 0
    var negativeBarColor = 0
    var zerolineColor = 0
    var meanValueColor = 0
    var paddingBetweenBars = 0
    var typefaceAmountLabel: Typeface? = null
    var textColorAmountLabel = 0
    var textSizeAmountLabel = 0f
    var textLineHeightAmountLabel = 0f
    var textSpacingAmountLabel = 0f
    var typefaceDateLabel: Typeface? = null
    var textColorDateLabel = 0
    var textSizeDateLabel = 0f
    var textLineHeightDateLabel = 0f
    var textSpacingDateLabel = 0f
    var isShowXLines = false // if this is true, labels on Y-axis is not shown = false
    var isShowZeroLine = false
    var isShowMeanLine = false
    var isShowPeriodLabels = false
    var isIncludeVerticalPadding = false
    var isAmountLabelsAboveBars = false
    var isSelector = false
    var cornerRadii = 0
    var selectedIndex = -1
    var selectedBarColor = -1
    var typefaceYLabel: Typeface? = null
    var yLabelTextColor = 0
    var textSizeYLabel = 0f
    var spacingYLabel = 0f

}