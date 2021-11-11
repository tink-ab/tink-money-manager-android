package com.tink.moneymanagerui.charts

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.text.TextPaint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.annotation.ColorInt
import com.tink.moneymanagerui.MoneyManagerFeatureType
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.charts.extensions.drawBarChart
import com.tink.moneymanagerui.theme.resolveColorForFeature
import com.tink.moneymanagerui.util.ScreenUtils
import se.tink.commons.extensions.getColorFromAttr
import se.tink.commons.utils.extractTextStyle
import se.tink.commons.extensions.whenNonNull

private const val ALL_BARS_ACTIVE = -1

internal class BarChartWithThreshold : View {
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


    var data: List<Float>? by InvalidateDelegate(null)
    var labels: List<String>? by InvalidateDelegate(null)
    var threshold: Float? by InvalidateDelegate(null)
    var thresholdLabel: String? by InvalidateDelegate(null)
    var activeBarCount: Int by InvalidateDelegate(ALL_BARS_ACTIVE)

    private var barWidth: Float = 1f

    private val badgeMargin = ScreenUtils.dpToPixels(context, 8)
    private val badgePadding = ScreenUtils.dpToPixels(context, 8).toFloat()
    private val barChartMarginBottom = ScreenUtils.dpToPixels(context, 20)
    private val barChartMarginLeft = ScreenUtils.dpToPixels(context, 36)
    private val averageLineOverdraw = ScreenUtils.dpToPixels(context, 12).toFloat()


    private val barPaint = Paint().apply {
        isAntiAlias = true
    }

    private val overlayPaint = Paint().apply {
        xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
    }

    private val disabledOverlayPaint = Paint().apply {
        color = context.resolveColorForFeature(R.attr.tink_expensesLightColor, MoneyManagerFeatureType.BUDGETS)
        xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
    }

    private val barLabelPaint = TextPaint().apply {
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }

    private val thresholdLinePaint = Paint().apply {
        style = Paint.Style.STROKE
    }

    private val thresholdLabelPaint = TextPaint().apply {
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
    }

    private val thresholdBadgePaint = Paint().apply {
        isAntiAlias = true
    }

    private val averageLinePaint = Paint().apply {
        xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)
        strokeWidth = ScreenUtils.dpToPixels(context, 1).toFloat()
        pathEffect = DashPathEffect(arrayOf(3f, 9f).toFloatArray(), 0f)
    }

    private val barChartBounds = RectF()
    private val thresholdLabelBounds = Rect()
    private val thresholdBadgeBounds = RectF()

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    private fun applyAttributes(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.TinkBarChartWithThreshold, 0, 0
        ).apply {
            try {
                barWidth = getDimension(R.styleable.TinkBarChartWithThreshold_tink_bar_width, 1f)
                barPaint.color =
                    context.resolveColorForFeature(R.attr.tink_expensesColor, MoneyManagerFeatureType.BUDGETS)
                overlayPaint.color =
                    context.resolveColorForFeature(R.attr.tink_warningColor, MoneyManagerFeatureType.BUDGETS)
                thresholdLinePaint.color =
                        getColor(R.styleable.TinkBarChartWithThreshold_tink_threshold_line_color, 0)
                thresholdLinePaint.strokeWidth =
                        getDimension(R.styleable.TinkBarChartWithThreshold_tink_threshold_line_thickness, 0f)
                thresholdBadgePaint.color =
                        getColor(R.styleable.TinkBarChartWithThreshold_tink_threshold_badge_color, 0)
                averageLinePaint.color =
                        getColor(R.styleable.TinkBarChartWithThreshold_tink_average_line_color, 0)

                val barLabelTextStyle =
                    getResourceId(R.styleable.TinkBarChartWithThreshold_tink_bar_label_style, -1)
                val thresholdLabelTextStyle =
                    getResourceId(R.styleable.TinkBarChartWithThreshold_tink_threshold_label_style, -1)
                setTextStyles(barLabelTextStyle, barLabelPaint)
                setTextStyles(thresholdLabelTextStyle, thresholdLabelPaint)
            } finally {
                recycle()
            }
        }
    }

    private fun setTextStyles(styleId: Int, textPaint: TextPaint) {
        if (styleId > 0) {
            extractTextStyle(context, styleId).let {
                textPaint.color = it.textColor
                textPaint.textSize = it.textSize
                textPaint.typeface = it.fontFamily
            }
        }
    }


    @Suppress("NAME_SHADOWING")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val globalRight = width.toFloat()
        val globalBottom = height.toFloat()

        if (canvas == null
            || data.isNullOrEmpty()
            || labels.isNullOrEmpty()
            || threshold == null
            || thresholdLabel == null
        ) return

        whenNonNull(
            canvas,
            data,
            threshold,
            thresholdLabel
        ) { canvas, data, threshold, thresholdLabel ->

            //Compute bounds

            thresholdLabelPaint.getTextBounds(
                thresholdLabel,
                0,
                thresholdLabel.length,
                thresholdLabelBounds
            )

            thresholdBadgeBounds.apply {
                set(thresholdLabelBounds)
                inset(-badgePadding, -badgePadding)
            }

            val dataMax = data.maxOrNull()!!

            val thresholdLineY: Float

            if (dataMax > threshold) {
                barChartBounds.set(
                    0f,
                    thresholdBadgeBounds.height() / 2,
                    globalRight,
                    globalBottom - barChartMarginBottom
                )
                thresholdLineY = (1 - threshold / dataMax) * barChartBounds.height() +
                        barChartBounds.top
            } else {
                thresholdLineY = thresholdBadgeBounds.height() / 2
                barChartBounds.set(
                    0f,
                    thresholdLineY + globalBottom * (1 - dataMax / threshold),
                    globalRight,
                    globalBottom - barChartMarginBottom
                )
            }

            thresholdBadgeBounds.apply {
                offsetTo(globalRight - badgeMargin - width(), thresholdLineY - height() / 2)
            }

            barChartBounds.right -= thresholdBadgeBounds.width() + 2 * badgeMargin
            barChartBounds.left += barChartMarginLeft

            val averageLineY =
                (barChartBounds.top + barChartBounds.height() * (1 - data.average() / dataMax)).toFloat()

            //Draw items

            canvas.drawBarChart(barChartBounds, data, barWidth, barPaint)

            canvas.drawRect(
                barChartBounds.left,
                barChartBounds.top,
                barChartBounds.right,
                thresholdLineY,
                overlayPaint
            )

            val betweenMargin = (barChartBounds.width() - data.size * barWidth) / (data.size - 1)

            val barWidthWithMargin = betweenMargin + barWidth

            if (activeBarCount >= 0) {
                // Add disabled overlay for all inactive bars
                canvas.drawRect(
                    barChartBounds.left,
                    barChartBounds.top,
                    barChartBounds.right - (barWidthWithMargin * activeBarCount),
                    barChartBounds.bottom,
                    disabledOverlayPaint
                )
            }

            canvas.drawLine(
                0f,
                thresholdLineY,
                thresholdBadgeBounds.centerX(),
                thresholdLineY,
                thresholdLinePaint
            )

            canvas.drawLine(
                barChartBounds.left - averageLineOverdraw,
                averageLineY,
                barChartBounds.right + averageLineOverdraw,
                averageLineY,
                averageLinePaint
            )

            for ((index, label) in labels?.withIndex() ?: listOf()) {
                val x = index * (betweenMargin + barWidth) + barChartBounds.left
                canvas.drawText(
                    label,
                    x + barWidth / 2,
                    globalBottom - barLabelPaint.descent(),
                    barLabelPaint
                )
            }

            canvas.drawRoundRect(
                thresholdBadgeBounds,
                thresholdBadgeBounds.width() / 2,
                thresholdBadgeBounds.width() / 2,
                thresholdBadgePaint
            )
            canvas.drawText(
                thresholdLabel,
                thresholdBadgeBounds.centerX(),
                thresholdBadgeBounds.centerY() + thresholdLabelBounds.height() / 2 - thresholdLabelBounds.bottom,
                thresholdLabelPaint
            )
        }
    }
}
