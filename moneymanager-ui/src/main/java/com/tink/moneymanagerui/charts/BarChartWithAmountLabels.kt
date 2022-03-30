package com.tink.moneymanagerui.charts

import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.charts.extensions.drawBarChartWithAmountLabels
import com.tink.moneymanagerui.util.ScreenUtils
import se.tink.commons.extensions.getColorFromAttr
import se.tink.commons.extensions.whenNonNull
import se.tink.commons.utils.extractTextStyle

internal class BarChartWithAmountLabels : View {
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

    var data: List<AmountWithLabel>? by InvalidateDelegate(null)
    var showAmountLabels = true

    private var barWidth: Float = 1f
    private var barChartMarginRight: Int = 0
    private var barChartMarginLeft: Int = 0

    val barChartMarginBottom = ScreenUtils.dpToPixels(context, 32)
    private val averageLineOverdraw = ScreenUtils.dpToPixels(context, 12).toFloat()
    private val amountLabelBottomMargin = ScreenUtils.dpToPixels(context, 8)
    private val amountLabelTopMargin = ScreenUtils.dpToPixels(context, 5)
    private val barChartCornerRadius = ScreenUtils.dpToPixels(context, 4).toFloat()

    private val barPaint = Paint().apply {
        isAntiAlias = true
    }

    private val barLabelPaint = TextPaint().apply {
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }

    private val visibleAmountLabelPaint: TextPaint = TextPaint().apply {
        textSize = resources.getDimension(R.dimen.tink_pico_text_size)
        color = context.getColorFromAttr(R.attr.tink_textColorSecondary)
        typeface = ResourcesCompat.getFont(context, R.font.tink_font_regular)
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }

    private val transparentAmountLabelPaint: TextPaint = TextPaint().apply {
        textSize = resources.getDimension(R.dimen.tink_pico_text_size)
        color = context.getColorFromAttr(R.attr.tink_textColorSecondary)
        typeface = ResourcesCompat.getFont(context, R.font.tink_font_regular)
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
        alpha = 0
    }

    private val averageLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)
        strokeWidth = ScreenUtils.dpToPixels(context, 1).toFloat()
        val dashWidth = ScreenUtils.dpToPixels(context, 1).toFloat()
        val dashGap = ScreenUtils.dpToPixels(context, 3).toFloat()
        pathEffect = DashPathEffect(arrayOf(dashWidth, dashGap).toFloatArray(), 0f)
    }

    private val averageLinePath = Path()

    private val barChartBounds = RectF()

    fun getLabelHeight() = visibleAmountLabelPaint.textSize + amountLabelTopMargin

    init {
        setLayerType(LAYER_TYPE_HARDWARE, null)
    }

    private fun applyAttributes(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.TinkBarChartWithThreshold, 0, 0
        ).apply {
            try {
                barWidth = getDimension(R.styleable.TinkBarChartWithThreshold_tink_bar_width, 1f)
                barPaint.color =
                    getColor(R.styleable.TinkBarChartWithThreshold_tink_bar_color_below_threshold, 0)
                averageLinePaint.color =
                    getColor(R.styleable.TinkBarChartWithThreshold_tink_average_line_color, 0)

                val barLabelTextStyle =
                    getResourceId(R.styleable.TinkBarChartWithThreshold_tink_bar_label_style, -1)
                setTextStyles(barLabelTextStyle, barLabelPaint)

                getDimensionPixelSize(R.styleable.TinkBarChartWithThreshold_tink_horizontal_margin, 0).also {
                    barChartMarginLeft = it
                    barChartMarginRight = it
                }
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

        if (canvas == null ||
            data.isNullOrEmpty()
        ) return

        whenNonNull(
            canvas,
            data
        ) { canvas, data ->
            // Compute bounds
            val dataMax = data.maxOf { it.amount }

            barChartBounds.set(
                0f,
                0f,
                globalRight,
                globalBottom - barChartMarginBottom
            )

            barChartBounds.right -= barChartMarginRight
            barChartBounds.left += barChartMarginLeft

            val amountLabelHeight = visibleAmountLabelPaint.textSize + amountLabelTopMargin

            val averageLineY =
                (
                    (barChartBounds.top + amountLabelHeight) +
                        (barChartBounds.height() - amountLabelBottomMargin) *
                        (1 - data.map { it.amount }.average() / dataMax)
                    ).toFloat()

            val amountLabelPaint = if (showAmountLabels) {
                visibleAmountLabelPaint
            } else {
                transparentAmountLabelPaint
            }

            // Draw items
            canvas.drawBarChartWithAmountLabels(
                barChartBounds,
                data,
                barWidth,
                barPaint,
                barChartCornerRadius,
                amountLabelPaint,
                amountLabelTopMargin,
                amountLabelBottomMargin
            )

            // Draw average line
            averageLinePath.reset()
            averageLinePath.moveTo(barChartBounds.left - averageLineOverdraw, averageLineY)
            averageLinePath.lineTo(barChartBounds.right + averageLineOverdraw, averageLineY)
            canvas.drawPath(averageLinePath, averageLinePaint)

            // Draw labels
            val betweenMargin = (barChartBounds.width() - data.size * barWidth) / (data.size - 1)
            for ((index, label) in data.map { it.label }.withIndex()) {
                val x = index * (betweenMargin + barWidth) + barChartBounds.left
                canvas.drawText(
                    label,
                    x + barWidth / 2,
                    globalBottom - barLabelPaint.descent(),
                    barLabelPaint
                )
            }
        }
    }

    data class AmountWithLabel(
        val amount: Float,
        val label: String,
        val amountLabel: String
    )
}
