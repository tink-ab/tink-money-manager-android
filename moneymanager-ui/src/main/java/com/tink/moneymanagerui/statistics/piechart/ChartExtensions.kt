package com.tink.moneymanagerui.statistics.piechart

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.statistics.PieChartLabelView
import com.tink.moneymanagerui.statistics.PieChartView
import com.tink.moneymanagerui.statistics.extensions.sumByFloat
import com.tink.moneymanagerui.statistics.utils.ColorGenerator

internal fun PieChartView.addBackSegment(type: String, @ColorInt baseColor: Int) {
    val color = ColorUtils.setAlphaComponent(baseColor, (0.4f * 255).toInt())
    val colorPressed = getPressedColor(color)
    addSegment(0f, 360f, color, colorPressed).apply {
        id = R.id.tink_back_segment
        transitionName = "$type-back-segment"
        transitionGroup = R.id.tink_transition_group_back
    }
}

internal fun <T> PieChartView.addSegments(
    data: List<T>,
    value: (T) -> Float,
    colorGenerator: ColorGenerator,
    @ColorInt baseColor: Int,
    currency: String,
    createLabel: ((item: T, currency: String, startAngle: Float, sweep: Float) -> PieChartLabelView?)? = null,
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
            transitionGroup = R.id.tink_transition_group_main
            onClick?.let { listener ->
                setOnClickListener { listener(item) }
            }
        }
        createLabel?.invoke(item, currency, startAngle, sweep)?.let { addView(it) }
        startAngle += sweep
    }
}

private fun PieChartView.addSegment(
    startAngle: Float,
    sweep: Float,
    color: Int,
    colorPressed: Int
): PieChartView.PieChartSegmentView {
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
    ),
    intArrayOf(
        colorPressed,
        color
    )
)
