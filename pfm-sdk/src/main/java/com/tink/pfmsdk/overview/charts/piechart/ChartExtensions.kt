package com.tink.pfmsdk.overview.charts.piechart

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils
import com.tink.pfmsdk.R
import com.tink.pfmsdk.charts.PieChartLabelView
import com.tink.pfmsdk.charts.PieChartView
import com.tink.pfmsdk.charts.ColorGenerator
import com.tink.pfmsdk.charts.extensions.sumByFloat


fun PieChartView.addBackSegment(type: String, @ColorInt baseColor: Int) {
    val color = ColorUtils.setAlphaComponent(baseColor, (0.4f * 255).toInt())
    val colorPressed = getPressedColor(color)
    addSegment(0f, 360f, color, colorPressed).apply {
        id = R.id.back_segment
        transitionName = "$type-back-segment"
        transitionGroup = R.id.transition_group_back
    }
}

fun <T> PieChartView.addSegments(
    data: List<T>,
    value: (T) -> Float,
    colorGenerator: ColorGenerator,
    @ColorInt baseColor: Int,
    createLabel: ((item: T, startAngle: Float, sweep: Float) -> PieChartLabelView?)? = null,
    startFrom: Float = 0f,
    fullSweep: Float = 360f,
    onClick: ((T) -> Unit)? = null
) {
    var startAngle = startFrom
    val sum = data.sumByFloat(value)
    data.filter { value(it) > 0 }.forEachIndexed { idx, item ->
        val sweep = value(item) / sum * fullSweep
        val color = colorGenerator.color(baseColor, idx)
        val colorPressed = getPressedColor(color)
        addSegment(startAngle, sweep, color, colorPressed).apply {
            transitionGroup = R.id.transition_group_main
            onClick?.let { listener ->
                setOnClickListener { listener(item) }
            }
        }
        createLabel?.invoke(item, startAngle, sweep)?.let { addView(it) }
        startAngle += sweep
    }
}

private fun PieChartView.addSegment(startAngle: Float, sweep: Float, color: Int, colorPressed: Int): PieChartView.PieChartSegmentView {
    return createSegment().also {
        it.startAngle = startAngle
        it.angle = sweep
        it.colorStateList = createStateList(color, colorPressed)
        addView(it)
    }
}

@ColorInt
private fun getPressedColor(@ColorInt color: Int): Int {
    val hsv = FloatArray(3)
    Color.colorToHSV(color, hsv)
    hsv[2] *= 0.9f
    return Color.HSVToColor(Color.alpha(color), hsv)
}

private fun createStateList(color: Int, colorPressed: Int) = ColorStateList(
    arrayOf(
        intArrayOf(android.R.attr.state_pressed),
        intArrayOf()
    ), intArrayOf(
        colorPressed,
        color
    )
)
