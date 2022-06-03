package com.tink.moneymanagerui.statistics.piechart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Typeface
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import com.tink.moneymanagerui.MoneyManagerFeatureType
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.theme.resolveColorForFeature

private const val START_ANGLE_OFFSET = -90

class CircularProgressChart : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        applyAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        applyAttributes(attrs)
    }

    var progress: Double = 0.0
        set(value) {
            field = value
            invalidate()
        }

    var invertedProgress: Boolean = false

    private var label: String? = null
    private var backgroundRingStrokeWidth: Int = 0
    private var canvasRect = Rect()
    private var textBounds = Rect()

    private val progressArcPaint: Paint = Paint().apply {
        strokeCap = Paint.Cap.ROUND
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private val backgroundRingPaint: Paint = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private val labelPaint: TextPaint = TextPaint().apply {
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }

    private fun applyAttributes(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.TinkCircularProgressChart, 0, 0
        ).apply {
            try {
                val arcWidth = getDimensionPixelSize(
                    R.styleable.TinkCircularProgressChart_tink_progressArcStrokeWidth,
                    0
                )
                val backgroundRingWidth = getDimensionPixelSize(
                    R.styleable.TinkCircularProgressChart_tink_backgroundRingStrokeWidth,
                    0
                )
                setStrokeWidth(arcWidth, backgroundRingWidth)

                progressArcPaint.color =
                    context.resolveColorForFeature(R.attr.tink_expensesColor, MoneyManagerFeatureType.BUDGETS)

                backgroundRingPaint.color =
                    context.resolveColorForFeature(R.attr.tink_expensesLightColor, MoneyManagerFeatureType.BUDGETS)

                progress =
                    getFloat(R.styleable.TinkCircularProgressChart_tink_progress, 0F).toDouble()

                invertedProgress =
                    getBoolean(R.styleable.TinkCircularProgressChart_tink_invertedProgress, false)
            } finally {
                recycle()
            }
        }
    }

    override fun draw(canvas: Canvas) {
        with(canvas) {
            getClipBounds(canvasRect)
            canvasRect.inset(backgroundRingStrokeWidth / 2, backgroundRingStrokeWidth / 2)
            drawOval(RectF(canvasRect), backgroundRingPaint)
            drawArc(
                RectF(canvasRect),
                START_ANGLE_OFFSET.toFloat(),
                -360 * progress.toFloat() * (if (invertedProgress) -1 else 1),
                false,
                progressArcPaint
            )

            label?.let {
                drawText(
                    it,
                    canvasRect.centerX().toFloat(),
                    (canvasRect.centerY() + textBounds.height() / 2).toFloat(),
                    labelPaint
                )
            }
        }
        super.draw(canvas)
    }

    fun setProgressArcColor(@AttrRes colorAttr: Int, moneyManagerFeatureType: MoneyManagerFeatureType) {
        progressArcPaint.color =
            context.resolveColorForFeature(colorAttr, moneyManagerFeatureType)
        invalidate()
    }

    fun setBackgroundRingColor(@AttrRes colorAttr: Int, moneyManagerFeatureType: MoneyManagerFeatureType) {
        backgroundRingPaint.color =
            context.resolveColorForFeature(colorAttr, moneyManagerFeatureType)
        invalidate()
    }

    private fun setStrokeWidth(progressArcWidth: Int, backgroundRingWidth: Int) {
        progressArcPaint.strokeWidth = progressArcWidth.toFloat()
        backgroundRingStrokeWidth = backgroundRingWidth
        backgroundRingPaint.strokeWidth = backgroundRingStrokeWidth.toFloat()
        invalidate()
    }

    private fun updateTextBounds() {
        label?.let { labelPaint.getTextBounds(it, 0, it.length, textBounds) }
    }

    class VisualSettings(
        var backgroundRingColor: Int,
        var backgroundRingOpacity: Float,
        var progressArcColor: Int,
        var labelTextColor: Int,
        var labelTextSize: Float,
        var labelTypeface: Typeface
    )
}
