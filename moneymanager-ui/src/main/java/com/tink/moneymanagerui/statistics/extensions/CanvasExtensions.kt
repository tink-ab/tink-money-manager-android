package com.tink.moneymanagerui.statistics.extensions

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import com.tink.moneymanagerui.statistics.barchart.BarChartWithAmountLabels

/**
 * Draws a simple bar chart in the Rect defined. Bars will be rounded at the top.
 * Very small bars will be slightly adjusted to a minimum value to prevent  artifacts with the rounded-corners.
 */
internal fun Canvas.drawBarChart(
    rectF: RectF,
    data: List<Float>,
    barWidth: Float,
    barPaint: Paint,
    barCornerRadius: Float? = null
) {
    val count = data.size
    val betweenMargin = (rectF.width() - count * barWidth) / (count - 1)
    val cornerRadius = barCornerRadius ?: barWidth / 2
    val bottom = rectF.bottom

    val max = data.maxOrNull() ?: return
    for ((index, top) in data.map { (1 - it / max) * rectF.height() + rectF.top }.withIndex()) {
        drawBarAtIndex(
            index,
            rectF.left,
            top,
            bottom,
            barWidth,
            cornerRadius,
            betweenMargin,
            barPaint
        )
    }
}

internal fun Canvas.drawBarChartWithAmountLabels(
    rectF: RectF,
    data: List<BarChartWithAmountLabels.AmountWithLabel>,
    barWidth: Float,
    barPaint: Paint,
    barCornerRadius: Float,
    amountLabelPaint: Paint,
    amountLabelTopMargin: Int,
    amountBottomMargin: Int
) {
    val count = data.size
    val betweenMargin = (rectF.width() - count * barWidth) / (count - 1)
    val bottom = rectF.bottom
    val amountLabelHeight = amountLabelPaint.textSize + amountLabelTopMargin

    val max = data.maxByOrNull { it.amount } ?: return

    for (
        (index, top) in data.map {
            (1 - it.amount / max.amount) * (rectF.height() - amountBottomMargin) +
                (rectF.top + amountLabelHeight)
        }.withIndex()
    ) {
        drawBarAtIndex(
            index,
            rectF.left,
            top,
            bottom,
            barWidth,
            barCornerRadius,
            betweenMargin,
            barPaint,
            AmountLabel(data[index].amountLabel, amountBottomMargin, amountLabelPaint)
        )
    }
}

private fun Canvas.drawBarAtIndex(
    index: Int,
    left: Float,
    top: Float,
    bottom: Float,
    barWidth: Float,
    barCornerRadius: Float,
    betweenMargin: Float,
    barPaint: Paint,
    amountLabel: AmountLabel? = null
) {
    val adjustedTop: Float
    val x = index * (betweenMargin + barWidth) + left

    if (bottom - top < 0.001) { // Amount is 0, draw thin placeholder bar
        adjustedTop = bottom - 2f
        drawRect(x, adjustedTop, x + barWidth, bottom, barPaint)
    } else {
        val minBarHeight = 2 * barCornerRadius
        adjustedTop = if (bottom - top < minBarHeight) bottom - minBarHeight else top
        drawRoundRect(
            x,
            adjustedTop,
            x + barWidth,
            bottom,
            barCornerRadius,
            barCornerRadius,
            barPaint
        )
        drawRect(x, bottom - barCornerRadius, x + barWidth, bottom, barPaint)
    }

    amountLabel?.let {
        val rect = RectF(
            x,
            adjustedTop,
            x + barWidth,
            bottom
        )
        val point = PointF(rect.centerX(), adjustedTop - it.bottomMargin)
        drawText(it.label, point.x, point.y, it.labelPaint)
    }
}

private data class AmountLabel(val label: String, val bottomMargin: Int, val labelPaint: Paint)
