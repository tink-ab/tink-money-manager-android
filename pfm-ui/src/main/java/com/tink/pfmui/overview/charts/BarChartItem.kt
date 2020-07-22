package com.tink.pfmui.overview.charts

import androidx.annotation.AttrRes
import androidx.annotation.FloatRange

data class BarChartItem(
    val amountLabel: String?,
    val periodLabel: String?,
    @FloatRange(from = 0.0, to = 1.0) val barHeightFactor: Float,
    @AttrRes val barColor: Int
)

class BarChartItems(
    val list: List<BarChartItem>,
    val averageFactor: Float
) : List<BarChartItem> by list
