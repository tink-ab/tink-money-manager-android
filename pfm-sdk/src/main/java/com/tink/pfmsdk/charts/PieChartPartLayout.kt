package com.tink.pfmsdk.charts

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.tink.pfmsdk.R
import kotlin.math.atan
import kotlin.math.sin

internal class PieChartPartLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ViewGroup(context, attrs, defStyleAttr) {

    private val visiblePart: Float

    private var chartView: PieChartView? = null

    val visibleAngle: Float

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.PieChartPartLayout)
        visiblePart = a.getFloat(R.styleable.PieChartPartLayout_chart_visible_part, 0.5f)
        visibleAngle = Math.toDegrees(atan(1f - 2f * visiblePart).toDouble()).toFloat()
        a.recycle()
    }

    override fun addView(child: View?, index: Int, params: LayoutParams?) {
        if (chartView != null || child !is PieChartView) {
            throw IllegalArgumentException("Can hold only one child - PieChartView")
        }
        chartView = child
        super.addView(child, index, params)
    }

    override fun onViewRemoved(child: View?) {
        if (chartView === child) {
            chartView = null
        }
        super.onViewRemoved(child)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val chart = chartView
        if (chart == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            return
        }
        val w = MeasureSpec.getSize(widthMeasureSpec)
        val h = MeasureSpec.getSize(heightMeasureSpec)

        val sin = sin(Math.toRadians(visibleAngle.toDouble()))
        val maxR = w / (1f - (1f - chart.thicknessRatio) * sin)

        chart.measure(MeasureSpec.makeMeasureSpec((2 * maxR).toInt(), MeasureSpec.AT_MOST), heightMeasureSpec)
        chart.preLayout(chart.measuredWidth, chart.measuredHeight)
        setMeasuredDimension(getVisibleChartWidth(), h)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val chart = chartView ?: return
        val chartHeight = chart.measuredHeight
        val chartWidth = chart.measuredWidth

        val verticalPadding = ((height - chartHeight) / 2f).toInt()
        val chartLeft = getVisibleChartWidth() - chartWidth

        chart.layout(chartLeft, verticalPadding, chartLeft + chartWidth, verticalPadding + chartHeight)
    }

    private fun getVisibleChartWidth(): Int {
        val chart = chartView ?: return 0
        return (chart.measuredWidth / 2f - (chart.outerRadius - chart.thickness) * sin(Math.toRadians(visibleAngle.toDouble()))).toInt()
    }
}