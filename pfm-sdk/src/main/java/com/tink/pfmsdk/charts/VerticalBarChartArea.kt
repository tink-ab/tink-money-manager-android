package com.tink.pfmsdk.charts

import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.google.common.base.Strings
import com.tink.pfmsdk.R
import com.tink.pfmsdk.util.ChartCurrencyUtils
import com.tink.pfmsdk.util.PortableChartUtils
import com.tink.pfmsdk.util.ScreenUtils
import se.tink.utils.DateUtils
import java.util.ArrayList
import java.util.Locale
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

internal class VerticalBarChartArea : ChartArea {
    private var showXLines = false
    private var showZeroLine = false
    private var showMeanLine = false
    private var showAmountLabels = false
    private var showPeriodLabels = false
    private var includeVerticalPadding = false
    private var amountLabelsAboveBars = false
    private var paddingBetweenBars = 0
    var selectedIndex = -1
    // TODO should have default values?
    var barColor = -1
        get() = if (field == -1) {
            ContextCompat
                .getColor(
                    context!!,
                    R.color.vertical_bar_chart_default_color
                ) // TODO should have default values?
        } else field
    var negativeBarColor = -1
        get() = if (field == -1) {
            barColor
        } else field
    private var selectedBarColor = -1
    private var textPaint: Paint? = null
    private var amountTextPaint: Paint? = null
    private var lineTextPaint: Paint? = null
    private var zeroLinePaint: Paint? = null
    private var meanValuePaint: Paint? = null
    private val barRects: MutableList<Rect> = mutableListOf()
    private var adapter: BarChartAreaAdapter? = null
    private var isSelector = false
    private var cornerRadii = 0
    private var event: IndexSelectorEvent? = null
    private var locale: Locale? = null
    private var timezoneCode: String? = null
    private var currencyCode: String? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    fun initialize(
        locale: Locale?,
        timezoneCode: String?,
        currencyCode: String?
    ) {
        this.locale = locale
        this.timezoneCode = timezoneCode
        this.currencyCode = currencyCode
        setupPaints()
    }

    private fun setupPaints() {
        getTextPaint()
        getAmountTextPaint()
        lineTextPaint = Paint(getTextPaint())
        lineTextPaint!!.isAntiAlias = true
        lineTextPaint!!.textAlign = Paint.Align.LEFT
        zeroLinePaint = Paint()
        zeroLinePaint!!.isAntiAlias = true
        zeroLinePaint!!.strokeWidth = ScreenUtils.dpToPixels(context, 1).toFloat()
        zeroLinePaint!!.style = Paint.Style.STROKE
        meanValuePaint = Paint(zeroLinePaint)
        meanValuePaint!!.pathEffect = DashPathEffect(
            floatArrayOf(
                ScreenUtils.dpToPixels(context, 1).toFloat(),
                ScreenUtils.dpToPixels(context, 3).toFloat()
            ), 0f
        )
    }

    override fun leftPadding(): Float {
        return ScreenUtils.dpToPixels(context, 12).toFloat()
    }

    override fun rightPadding(): Float {
        return 0f
    }

    override fun topPadding(): Float {
        return if (includeVerticalPadding) amountVerticalMargin + getAmountTextPaint()
            .textSize else 0f
    }

    override fun bottomPadding(): Float {
        return 0f
    }

    override fun yLabelsSideMargin(): Int {
        return ScreenUtils.dpToPixels(context, 8)
    }

    private fun drawZeroLine(zeroY: Float, canvas: Canvas) {
        canvas.drawLine(0f, zeroY, width.toFloat(), zeroY, zeroLinePaint!!)
    }

    private fun drawXLine(
        canvas: Canvas,
        y: Float,
        value: Double
    ) {
        canvas.drawLine(0f, y, canvas.width.toFloat(), y, axisPaint)
        val textToDraw = ChartCurrencyUtils.getInstance(locale)
            .formatCurrencyWithAmountSignAndWithoutSymbol(value, currencyCode)
        canvas.drawText(
            textToDraw, ScreenUtils.dpToPixels(context, 6).toFloat(),
            y + getTextPaint().textSize + labelPadding, lineTextPaint!!
        )
    }

    private fun drawXLines(zeroPoint: Float, canvas: Canvas) {
        val bounds = bounds(canvas)
        val spacingBetweenLines =
            bounds.height() / NUMBER_OF_LINES
        val linesOverZero =
            floor(zeroPoint / spacingBetweenLines.toDouble()).toInt()
        var lastY = zeroPoint - spacingBetweenLines
        val deltaValue = maxValue - minValue
        val valuePerLine = deltaValue * (spacingBetweenLines / bounds.height())
        for (i in 0 until linesOverZero) {
            drawXLine(canvas, lastY, valuePerLine * (i + 1))
            lastY -= spacingBetweenLines
        }
        val linesUnderZero =
            floor((canvas.height - zeroPoint) / spacingBetweenLines.toDouble())
        lastY = zeroPoint + spacingBetweenLines
        var i = 0
        while (i < linesUnderZero) {
            drawXLine(canvas, lastY, -(valuePerLine * (i + 1)))
            lastY += spacingBetweenLines
            i++
        }
    }

    private fun getBarDrawables(
        zeroY: Float,
        barWidth: Double,
        canvas: Canvas
    ): List<BarWithLabel> {
        val barList: MutableList<BarWithLabel> =
            ArrayList()
        // TODO should be used for some bar charts.
//        float[] positiveRadii = new float[]{getBarCornerRadii(), getBarCornerRadii(), getBarCornerRadii(), getBarCornerRadii(), 0, 0, 0, 0};
//        float[] negativeRadii = new float[]{0, 0, 0, 0, getBarCornerRadii(), getBarCornerRadii(), getBarCornerRadii(), getBarCornerRadii()};
/* Positive and negative radii */
        val radiiDown = floatArrayOf(
            0f,
            0f,
            0f,
            0f,
            barCornerRadii.toFloat(),
            barCornerRadii.toFloat(),
            barCornerRadii.toFloat(),
            barCornerRadii.toFloat()
        )
        val radiiUp = floatArrayOf(
            barCornerRadii.toFloat(),
            barCornerRadii.toFloat(),
            barCornerRadii.toFloat(),
            barCornerRadii.toFloat(),
            0f,
            0f,
            0f,
            0f
        )
        var offsettedX = paddingToFirstBar.toDouble()
        for (i in 0 until adapter!!.count) {
            val drawable =
                GradientDrawable()
            drawable.shape = GradientDrawable.RECTANGLE
            val item = adapter!!.items[i]
            var color: Int
            if (i == selectedIndex) {
                color = selectedBarColor
                if (selectedBarColor == -1) {
                    color = barColor
                }
            } else {
                color = if (item.amount >= 0) {
                    barColor
                } else {
                    negativeBarColor
                }
            }
            drawable.setColor(color)
            var endY = getPointY(item.amount, bounds(canvas))
            if (item.amount >= 0) {
                if (item.amount == 0.0) {
                    endY--
                }
                drawable.cornerRadii = radiiUp
                drawable.setBounds(
                    offsettedX.toInt(),
                    endY.toInt(),
                    (offsettedX + barWidth).toInt(),
                    zeroY.toInt()
                )
            } else {
                drawable.cornerRadii = radiiDown
                drawable.setBounds(
                    offsettedX.toInt(),
                    zeroY.toInt(),
                    (offsettedX + barWidth).toInt(),
                    endY.toInt()
                )
            }
            val rect = drawable.bounds
            var point: Point
            point =
                if (amountLabelsAboveBars || (item.amount - minValue) / adapter!!.difference < 0.1) {
                    if (item.amount >= 0) {
                        Point(
                            rect.centerX(),
                            (rect.top - amountVerticalMargin)
                        )
                    } else {
                        Point(
                            rect.centerX(),
                            (rect.bottom + amountVerticalMargin + getAmountTextPaint().textSize * .75f).toInt()
                        )
                    }
                } else {
                    Point(rect.centerX(), rect.top + amountVerticalMargin)
                }
            barRects.add(rect)
            val text = ChartCurrencyUtils.getInstance(locale)
                .formatCurrencyWithAmountSignAndWithoutSymbol(item.amount, currencyCode)
            barList.add(BarWithLabel(drawable, point, text))
            offsettedX += barWidth + paddingBetweenBars
        }
        return barList
    }

    private fun drawBarLabels(
        barWidth: Double,
        canvas: Canvas
    ) {
        var offsettedX = paddingToFirstBar + barWidth / 2
        for (i in 0 until adapter!!.count) {
            val item = adapter!!.items[i]
            val date = DateUtils.getInstance(locale, timezoneCode)
                .getMonthFromDateTime(item.period!!.stop)
            val bounds = bounds(canvas)
            val y = bounds.bottom - ScreenUtils
                .dpToPixels(
                    context,
                    BOTTOM_LABELS_BOTTOM_MARGIN_DP
                )
            if (item.amount >= 0) {
                canvas.drawText(date, offsettedX.toFloat(), y, getTextPaint())
            } else {
                canvas.drawText(date, offsettedX.toFloat(), y, getTextPaint())
            }
            offsettedX += barWidth + paddingBetweenBars
        }
    }

    private fun drawMeanLine(
        position: Float,
        canvas: Canvas
    ) {
        val path = Path()
        val sideMargin = ScreenUtils.dpToPixels(context, 8).toFloat()
        path.moveTo(sideMargin, position)
        path.lineTo(width - sideMargin, position)
        canvas.drawPath(path, meanValuePaint!!)
    }

    private fun drawBars(
        bars: List<BarWithLabel>,
        canvas: Canvas
    ) {
        for (bar in bars) {
            bar.gradient.draw(canvas)
            if (showAmountLabels) {
                canvas.drawText(
                    bar.labelText,
                    bar.labelPoint.x.toFloat(),
                    bar.labelPoint.y.toFloat(),
                    getAmountTextPaint()
                )
            }
        }
    }

    override fun draw(canvas: Canvas) {
        if (adapter == null) {
            return
        }
        val yLines = PortableChartUtils
            .getSupportLines(minValue.toInt(), maxValue.toInt())
        adapter!!.minValue = min(yLines[0], adapter!!.minValue.toInt()).toDouble()
        adapter!!.maxValue = max(yLines[yLines.size - 1], adapter!!.maxValue.toInt()).toDouble()
        super.draw(canvas)
        if (adapter == null || Strings.isNullOrEmpty(currencyCode)) {
            return
        }
        if (showMeanLine) {
            val meanYValue = getPointY(adapter!!.meanValue, bounds(canvas))
            drawMeanLine(meanYValue, canvas)
        }
        val zeroY = getPointY(0.0, bounds(canvas))
        val barWidth =
            (width - paddingToFirstBar - rightPaddingWithRoomForLabels() - paddingBetweenBars * adapter!!.count) / adapter!!.count.toDouble()
        if (showZeroLine) {
            drawZeroLine(zeroY, canvas)
        }
        if (showXLines) {
            drawXLines(zeroY, canvas)
        }
        drawBars(getBarDrawables(zeroY, barWidth, canvas), canvas)
        if (showPeriodLabels) {
            drawBarLabels(barWidth, canvas)
        }
        drawYLabels(canvas, yLines)
    }

    private fun rightPaddingWithRoomForLabels(): Int {
        return ScreenUtils.dpToPixels(context, 40)
    }

    private fun drawYLabels(
        canvas: Canvas,
        lines: List<Int>
    ) { // TODO bool useExact = lines.Exists (l => l % 1000 != 0); ??
        if (lines.isEmpty()) {
            return
        }
        var maxWidth = 0f
        for (line in lines) {
            val text =
                ChartCurrencyUtils.getInstance(locale).formatShort(line.toDouble(), currencyCode)
            val size = ScreenUtils.getSize(text, getAmountYLabelPaint())
            maxWidth = max(size.width().toFloat(), maxWidth)
        }
        for (line in lines) {
            drawYLabel(canvas, line)
        }
    }

    private fun drawYLabel(
        canvas: Canvas,
        lineValue: Int
    ) {
        if (getAmountYLabelPaint().textSize <= 0) {
            return
        }
        val bounds = bounds(canvas)
        val y = getPointY(lineValue.toDouble(), bounds)
        val text =
            ChartCurrencyUtils.getInstance(locale).formatShort(lineValue.toDouble(), currencyCode)
        ScreenUtils.getSize(text, getAmountYLabelPaint())
        canvas.drawText(
            text,
            bounds.right - yLabelsSideMargin(),
            y, getAmountYLabelPaint()
        )
    }

    private fun getIndexAtPoint(point: PointF): Int {
        val width = paddingBetweenBars + 1.toFloat()
        val left = point.x - width / 2
        val hitarea =
            Rect(left.toInt(), 0, (left + width).toInt(), height)
        for (i in 0 until adapter!!.count) {
            if (Rect.intersects(barRects[i], hitarea)) {
                return i
            }
        }
        return if (hitarea.left < 0) 0 else barRects.size - 1
    }

    fun handleClick(x: Float, y: Float) {
        if (!isSelector) {
            return
        }
        val selectedIndexLocal = getIndexAtPoint(PointF(x, y))
        if (selectedIndex != selectedIndexLocal) {
            selectedIndex = selectedIndexLocal
            event!!.indexSelected(selectedIndex)
            invalidate()
        }
    }

    fun setIndexSelectorEvent(event: IndexSelectorEvent?) {
        this.event = event
    }

    interface IndexSelectorEvent {
        fun indexSelected(index: Int)
    }

    private fun getPointY(value: Double, bounds: RectF): Float {
        return bounds.top + (bounds.height() - bottomTextAreaHeight) * (1
                - (value - minValue) / (maxValue - minValue)).toFloat()
    }

    private val bottomTextAreaHeight: Float
        get() = if (minValue < 0 && maxValue > 0) ScreenUtils.dpToPixels(
            context,
            76
        ).toFloat() else ScreenUtils.dpToPixels(context, 56).toFloat()

    private val maxValue: Double
        get() = if (adapter != null) {
            if (adapter!!.minValue < 0 && adapter!!.maxValue < 0) {
                0.0
            } else adapter!!.maxValue
        } else 0.0

    private val minValue: Double
        get() = if (adapter != null) {
            if (adapter!!.maxValue > 0 && adapter!!.minValue > 0) {
                0.0
            } else adapter!!.minValue
        } else 0.0

    private val labelPadding: Int
        get() = ScreenUtils.dpToPixels(context, 2)

    private val amountVerticalMargin: Int
        get() = ScreenUtils.dpToPixels(context, 12)

    private val paddingToFirstBar: Int
        get() = leftPadding().toInt() + if (showXLines) ScreenUtils.dpToPixels(
            context,
            46
        ) else paddingBetweenBars

    private val barCornerRadii: Int
        get() = ScreenUtils.dpToPixels(context, cornerRadii)

    fun setAdapter(adapter: BarChartAreaAdapter?) {
        this.adapter = adapter
    }

    fun setPaddingBetweenBars(paddingBetweenBars: Int) {
        this.paddingBetweenBars = paddingBetweenBars
    }

    fun setTextColor(color: Int) {
        getTextPaint().color = color
        lineTextPaint!!.color = color
    }

    fun setZeroLineColor(color: Int) {
        zeroLinePaint!!.color = color
    }

    fun setMeanValueColor(color: Int) {
        meanValuePaint!!.color = color
    }

    fun setShowXLines(showXLines: Boolean) {
        this.showXLines = showXLines
    }

    fun setShowZeroLine(showZeroLine: Boolean) {
        this.showZeroLine = showZeroLine
    }

    fun setShowMeanLine(showMeanLine: Boolean) {
        this.showMeanLine = showMeanLine
    }

    fun setShowAmountLabels(showAmountLabels: Boolean) {
        this.showAmountLabels = showAmountLabels
    }

    fun setShowPeriodLabels(showPeriodLabels: Boolean) {
        this.showPeriodLabels = showPeriodLabels
    }

    fun setIncludeVerticalPadding(includeVerticalPadding: Boolean) {
        this.includeVerticalPadding = includeVerticalPadding
    }

    fun setAmountLabelsAboveBars(amountLabelsAboveBars: Boolean) {
        this.amountLabelsAboveBars = amountLabelsAboveBars
    }

    fun setSelectedBarColor(selectedBarColor: Int) {
        this.selectedBarColor = selectedBarColor
    }

    fun setIsSelector(isSelector: Boolean) {
        this.isSelector = isSelector
    }

    fun setCornerRadii(cornerRadii: Int) {
        this.cornerRadii = cornerRadii
    }

    private fun getTextPaint(): Paint {
        if (textPaint == null) {
            textPaint = Paint()
            textPaint!!.isAntiAlias = true
            textPaint!!.textAlign = Paint.Align.CENTER
        }
        return textPaint!!
    }

    private fun getAmountTextPaint(): Paint {
        if (amountTextPaint == null) {
            amountTextPaint = Paint()
            amountTextPaint!!.isAntiAlias = true
            amountTextPaint!!.textAlign = Paint.Align.CENTER
        }
        return amountTextPaint!!
    }

    private var amountYLabelPaint: Paint? = null

    private fun getAmountYLabelPaint(): Paint {
        if (amountYLabelPaint == null) {
            amountYLabelPaint = Paint()
            amountYLabelPaint!!.isAntiAlias = true
            amountYLabelPaint!!.textAlign = Paint.Align.RIGHT
        }
        return amountYLabelPaint!!
    }

    fun setupAmountTextPaint(
        typeface: Typeface?,
        textColor: Int,
        textSize: Float,
        spacing: Float
    ) {
        getAmountTextPaint().typeface = typeface
        getAmountTextPaint().color = textColor
        getAmountTextPaint().textSize = textSize
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getAmountTextPaint().letterSpacing = spacing
        }
        // TODO lineHeight
    }

    fun setupAmountYLabelTextPaint(
        typeface: Typeface?, textColor: Int, textSize: Float,
        spacing: Float
    ) {
        getAmountYLabelPaint().typeface = typeface
        getAmountYLabelPaint().color = textColor
        getAmountYLabelPaint().textSize = textSize
        getAmountYLabelPaint().textAlign = Paint.Align.RIGHT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getAmountYLabelPaint().letterSpacing = spacing
        }
        // TODO lineHeight
    }

    fun setupDateTextPaint(
        typeface: Typeface?,
        textColor: Int,
        textSize: Float,
        spacing: Float
    ) {
        getTextPaint().typeface = typeface
        getTextPaint().color = textColor
        getTextPaint().textSize = textSize
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getTextPaint().letterSpacing = spacing
        }
        // TODO lineHeight
    }

    fun clearAllData() {
        adapter = null
        barRects.clear()
        textPaint = null
        amountTextPaint = null
        lineTextPaint = null
        zeroLinePaint = null
        meanValuePaint = null
    }

    companion object {
        private const val NUMBER_OF_LINES = 6
        private const val BOTTOM_LABELS_BOTTOM_MARGIN_DP = 24
    }
}