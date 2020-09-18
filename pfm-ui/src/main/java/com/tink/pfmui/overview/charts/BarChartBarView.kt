package com.tink.pfmui.overview.charts

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.tink.pfmui.R
import com.tink.pfmui.charts.InvalidateDelegate

class BarChartBarView : View {

    var barHeight: Double by InvalidateDelegate(1.0)

    private val paint = Paint().apply {
        isAntiAlias = true
    }

    private var cornerRadius: Float = 0f

    private val bounds: Rect = Rect()

    constructor(context: Context) : super(context)

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        applyAttributes(attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        applyAttributes(attrs)
    }

    private fun applyAttributes(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.TinkBarChartBarView, 0, 0
        ).apply {
            try {
                paint.color =
                    getColor(R.styleable.TinkBarChartBarView_tink_barchartBarView_viewpager_indicator_color, 0)

                cornerRadius =
                    getDimensionPixelSize(R.styleable.TinkBarChartBarView_tink_barchartBarView_cornerRadius, 0).toFloat()
            } finally {
                recycle()
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBar(10f, paint)
    }

    private fun Canvas.drawBar(
        barCornerRadius: Float,
        barPaint: Paint
    ) {
        getClipBounds(bounds)

        val left = bounds.left.toFloat()
        val top = bounds.top.toFloat() + bounds.height().toFloat() * (1 - barHeight)
        val right = bounds.right.toFloat()
        val bottom = bounds.bottom.toFloat()


        val adjustedTop: Float

        if (bottom - top < 0.001) { // Amount is 0, draw thin placeholder bar
            adjustedTop = bottom - 2f
            drawRect(left, adjustedTop, right, bottom, barPaint)
        } else {
            val minBarHeight = 2 * barCornerRadius
            adjustedTop = if (bottom - top < minBarHeight) bottom - minBarHeight else top.toFloat()
            drawRoundRect(
                left,
                adjustedTop,
                right,
                bottom,
                cornerRadius,
                cornerRadius,
                barPaint
            )
            drawRect(left, bottom - minBarHeight, right, bottom, barPaint)
        }
    }
}