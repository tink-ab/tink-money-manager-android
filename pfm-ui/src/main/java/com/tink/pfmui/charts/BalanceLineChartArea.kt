package com.tink.pfmui.charts

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.CornerPathEffect
import android.graphics.DashPathEffect
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.graphics.RadialGradient
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.Typeface
import android.util.AttributeSet
import com.google.common.collect.Lists
import com.tink.pfmui.R
import com.tink.pfmui.charts.models.PeriodBalance
import com.tink.pfmui.util.ChartCurrencyUtils
import com.tink.pfmui.util.MathUtils
import com.tink.pfmui.util.PortableChartUtils
import com.tink.pfmui.util.ScreenUtils
import org.joda.time.DateTime
import org.joda.time.Interval
import se.tink.commons.extensions.getColorFromAttr
import se.tink.utils.DateUtils
import java.util.ArrayList
import java.util.Collections
import java.util.Locale
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

internal class BalanceLineChartArea : ChartArea {
    private var pathPaint: Paint? = null
    private var areaPaint: Paint? = null
    private var linePaint: Paint? = null
    private var points: List<PointF?>? = null
    private var linePoints: List<PointF?>? = null
    private var curveAdaptedPoints: List<PointF?>? = null
    var areaGradientTopColor = 0
    var areaGradientBottomColor = 0
    private var locale: Locale? = null
    private var timezoneCode: String? = null
    private var currencyCode: String? = null
    private var items: List<PeriodBalance>? = null
    private var averageItems: List<PeriodBalance>? = null
    private var zeroLinePaint: Paint? = null
    private var averageLinePaint: Paint? = null
    private var extremeValueBasedPadding = false
    private var markedDatePaint: Paint? = null
    private var markedDatePaint2: Paint? = null
    private var markedDatePaint3: Paint? = null
    private var markedDateTickPaint: Paint? = null
    private var markedDateTextPaint: Paint? = null
    private var showXLabels: XLabelAlignment? = null
    private var showYLabels: YLabelAlignment? = null
    var markToday = false
    private var markDate: String? = null
    private var left = 0f
    private var right = 0f
    private var top = 0f
    private var bottom = 0f
    private var minValue = 0.0
    private var maxValue = 0.0
    private var dates: List<String>? = null
    private var amountLabelsTypeface: Typeface? = null
    private var amountLabelsColor = 0
    private var amountLabelsTextSize = 0f
    private var markerGradientBottomColor = 0
    private var markerGradientTop80OpacityColor = 0
    private var markerGradientTop50OpacityColor = 0
    override fun topPadding(): Float {
        if (context == null) {
            return 0f
        }
        var padding = 0f
        if (maxValue >= 0) {
            padding += if (extremeValueBasedPadding) {
                ScreenUtils.dpToPixels(context, 20).toFloat()
            } else {
                ScreenUtils.dpToPixels(context, 1)
                    .toFloat() // Make at least room for the stroke
            }
        }
        if (markToday) {
            padding += ScreenUtils.dpToPixels(context, 5).toFloat()
        }
        padding = max(padding, ScreenUtils.dpToPixels(context, 20).toFloat())
        return padding
    }

    override fun bottomPadding(): Float {
        if (context == null) {
            return 0f
        }
        var padding = 0f
        if (minValue < 0) {
            padding += if (extremeValueBasedPadding) {
                ScreenUtils.dpToPixels(context, 20).toFloat()
            } else {
                ScreenUtils.dpToPixels(context, 1)
                    .toFloat() // Make at least room for the stroke
            }
        }
        if (markToday) {
            padding += ScreenUtils.dpToPixels(context, 25).toFloat()
        }
        if (makeRoomForXLabels()) {
            padding += xLabelPaint.textSize + xLabelBottomMargin * 2
        }
        return padding
    }

    override fun leftPadding(): Float {
        return 0f
    }

    override fun rightPadding(): Float {
        return 0f
    }

    override fun yLabelsSideMargin(): Int {
        return ScreenUtils.dpToPixels(context, 16)
    }

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
        currencyCode: String?,
        locale: Locale?,
        timezoneCode: String?
    ) {
        this.locale = locale
        this.timezoneCode = timezoneCode
        this.currencyCode = currencyCode
        markDate = DateUtils.getInstance(locale, timezoneCode).todayAsString
        extremeValueBasedPadding = false
        markedDateTickPaint = Paint()
        markedDateTickPaint!!.strokeCap = Paint.Cap.ROUND
        markedDateTickPaint!!.isAntiAlias = true
        markedDateTickPaint!!.strokeWidth = ScreenUtils.dpToPixels(context, 1.34f).toFloat()
        markedDateTickPaint!!.style = Paint.Style.STROKE
        markedDatePaint = Paint()
        markedDatePaint!!.strokeCap = Paint.Cap.ROUND
        markedDatePaint!!.isAntiAlias = true
        markedDatePaint!!.strokeWidth = ScreenUtils.dpToPixels(context, 2).toFloat()
        markedDatePaint2 = Paint()
        markedDatePaint2!!.strokeCap = Paint.Cap.ROUND
        markedDatePaint2!!.isAntiAlias = true
        markedDatePaint2!!.style = Paint.Style.FILL_AND_STROKE
        markedDatePaint3 = Paint()
        markedDatePaint3!!.strokeCap = Paint.Cap.ROUND
        markedDatePaint3!!.isAntiAlias = true
        markedDatePaint3!!.style = Paint.Style.FILL_AND_STROKE
        markedDateTextPaint = Paint()
        markedDateTextPaint!!.isAntiAlias = true
        markedDateTextPaint!!.textAlign = Paint.Align.CENTER
        //        markedDateTextPaint.setTextSize(); TODO : TextSize = UIUtils.GetDimension (Resource.Dimension.smallTextSize)
        markedDateTextPaint!!.typeface =
            Typeface.DEFAULT_BOLD // TODO : UIUtils.Font.GetTypeface (UIUtils.Font.Semibold)
        pathPaint = Paint()
        pathPaint!!.isAntiAlias = true
        pathPaint!!.color = context.getColorFromAttr(R.attr.tink_transparentColor)
        areaPaint = Paint()
        areaPaint!!.isAntiAlias = true
        areaPaint!!.strokeCap = Paint.Cap.ROUND
        areaPaint!!.style = Paint.Style.FILL
        linePaint = Paint()
        linePaint!!.isAntiAlias = true
        linePaint!!.isDither = true
        linePaint!!.style = Paint.Style.STROKE
        linePaint!!.strokeJoin = Paint.Join.ROUND
        linePaint!!.strokeCap = Paint.Cap.ROUND
        linePaint!!.pathEffect = CornerPathEffect(10f)
        linePaint!!.strokeWidth = ScreenUtils.dpToPixels(context, 3).toFloat()
    }

    private fun getPointY(value: Double, bounds: RectF): Float {
        if (maxValue == minValue) {
            maxValue++
        }
        return bounds.top + bounds.height() * (1 - (value - minValue) / (maxValue
                - minValue)).toFloat()
    }

    private fun getPointX(date: String?, bounds: RectF): Float {
        if (dates!!.size < 2) {
            return (-1).toFloat()
        }
        val first = dates!![0]
        val last = dates!![dates!!.size - 1]
        val firstDate = DateTime.parse(first)
        val lastDate = DateTime.parse(last)
        val dateTime = DateTime.parse(date)
        val interval = Interval(firstDate, lastDate)
        val dateRange = interval.toDuration().toStandardDays().days
        val interval1 = Interval(firstDate, dateTime)
        val differanceFromCurrentDate =
            interval1.toDuration().toStandardDays().days
        val ratio =
            differanceFromCurrentDate.toFloat() / dateRange.toFloat()
        return bounds.left + bounds.width() * ratio
    }

    private fun drawXAxis(canvas: Canvas) {
        val bounds = bounds(canvas)
        val y = getPointY(0.0, bounds)
        canvas.drawLine(bounds.left, y, bounds.right, y, axisPaint)
    }

    private fun drawYLabels(
        canvas: Canvas,
        lines: List<Int>
    ) { // TODO bool useExact = lines.Exists (l => l % 1000 != 0); ??
        if (lines.isEmpty()) {
            return
        }
        var maxWidth = 0f
        yLabelPaint.typeface = amountLabelsTypeface
        yLabelPaint.color = amountLabelsColor
        yLabelPaint.textSize = amountLabelsTextSize
        for (line in lines) {
            val text =
                ChartCurrencyUtils.getInstance(locale).formatShort(line.toDouble(), currencyCode)
            val size = ScreenUtils.getSize(text, yLabelPaint)
            maxWidth = max(size.width().toFloat(), maxWidth)
        }
        for (line in lines) {
            drawYLabel(canvas, line.toDouble(), maxWidth)
        }
    }

    private fun drawYLabel(
        canvas: Canvas,
        value: Double,
        maxWidth: Float
    ) {
        if (showYLabels == YLabelAlignment.NONE) {
            return
        }
        if (yLabelPaint.textSize <= 0) {
            return
        }
        val bounds = bounds(canvas)
        val y = getPointY(value, bounds)
        val text =
            ChartCurrencyUtils.getInstance(locale).formatShort(value, currencyCode)
        val size = ScreenUtils.getSize(text, yLabelPaint)
        canvas.drawText(
            text,
            bounds.right - maxWidth - yLabelsSideMargin(),
            y, yLabelPaint
        )
    }

    private fun drawXLabels(canvas: Canvas) {
        for (date in datesToShow!!) {
            drawXLabel(date, canvas)
        }
    }

    private fun drawXLabel(date: String, canvas: Canvas) {
        val bounds = bounds(canvas)
        val x = getPointX(date, bounds)
        if (showXLabels == XLabelAlignment.NONE) {
            return
        }
        val text =
            DateUtils.getInstance(locale, timezoneCode).formatDateString(date)
        val textHeight = xLabelPaint.textSize / 2
        if (showXLabels == XLabelAlignment.BOTTOM_OUTSIDE_CHARTAREA) {
            canvas.drawText(
                text, x + ScreenUtils.dpToPixels(context, 2),
                canvas.height - (textHeight / 2 + xLabelBottomMargin), xLabelPaint
            )
        } else if (showXLabels == XLabelAlignment.BOTTOM_INSIDE_CHART_AREA
            || showXLabels == XLabelAlignment.BOTTOM_INSIDE_FOR_POSITIVE_OUTSIDE_FOR_NEGATIVE
        ) {
            canvas.drawText(
                text, x + ScreenUtils.dpToPixels(context, 8),
                canvas.height - (textHeight / 2 + xLabelBottomMargin), xLabelPaint
            )
        } else if (showXLabels == XLabelAlignment.TOP_INSIDE_CHART_AREA) {
            canvas.drawText(
                text, x + ScreenUtils.dpToPixels(context, 8),
                bounds.top + textHeight + ScreenUtils.dpToPixels(context, 12), xLabelPaint
            )
        }
    }

    override fun draw(canvas: Canvas) {
        if (averageItems == null || items == null || dates == null) {
            return
        }
        val yLines = PortableChartUtils
            .getSupportLines(minValue.toInt(), maxValue.toInt())
        minValue = min(yLines[0], minValue.toInt()).toDouble()
        maxValue = max(yLines[yLines.size - 1], maxValue.toInt()).toDouble()
        val curvedPoints =
            getCurvePoints(averageItems, bounds(canvas))
        points = curvedPoints
        curveAdaptedPoints = curvedPoints
        val lineCurvedPoints =
            getCurvePoints(items, bounds(canvas))
        linePoints = lineCurvedPoints
        drawArea(canvas)
        if (!markDate.isNullOrEmpty()) {
            drawDateMarker(canvas, markDate)
        }
        drawZeroLine(canvas)
        drawPath(canvas)
        if (showYLabels != YLabelAlignment.NONE) {
            drawYLabels(canvas, yLines)
        }
        super.draw(canvas)
    }

    private fun drawArea(canvas: Canvas) {
        var points = curveAdaptedPoints
        if (hasVariant(VARIANT_AREA_BELOW_ACTUAL)) {
            points = linePoints
        }
        if (points == null || points.isEmpty()) {
            return
        }
        val y0 = getPointY(minValue, bounds(canvas))
        val path = getPath(points)
        path.lineTo(points[points.size - 1]!!.x, y0)
        path.lineTo(points[0]!!.x, y0)
        path.close()
        var amount = maxOnlyAverage
        if (hasVariant(VARIANT_AREA_BELOW_ACTUAL)) {
            amount = maxWithoutAverage
        }
        val amountForGradient = minValue
        areaPaint!!.shader = LinearGradient(
            0f,
            getPointY(amount, bounds(canvas)),
            0f,
            getPointY(amountForGradient, bounds(canvas)),
            areaGradientTopColor,
            areaGradientBottomColor,
            Shader.TileMode.CLAMP
        )
        canvas.drawPath(path, areaPaint!!)
    }

    private fun getAverageLinePaint(): Paint {
        if (averageLinePaint == null) {
            averageLinePaint = Paint()
            averageLinePaint!!.isAntiAlias = true
            averageLinePaint!!.style = Paint.Style.STROKE
            averageLinePaint!!.strokeWidth = ScreenUtils.dpToPixels(context, 1).toFloat()
            val dashWidth = ScreenUtils.dpToPixels(context, 2).toFloat()
            val dashGap = ScreenUtils.dpToPixels(context, 4).toFloat()
            averageLinePaint!!.pathEffect = DashPathEffect(
                floatArrayOf(
                    dashWidth,
                    dashGap
                ), 0f
            )
            averageLinePaint!!.color = Color.TRANSPARENT
        }
        return averageLinePaint!!
    }

    private fun drawPath(canvas: Canvas) {
        val curveAdaptedPoints =
            curveAdaptedPoints
        val linePoints = linePoints
        if (linePoints != null && linePoints.isNotEmpty()) {
            val path = getPath(linePoints)
            canvas.drawPath(path, linePaint!!)
        }
        if (curveAdaptedPoints != null && curveAdaptedPoints.isNotEmpty()) {
            val averagePath = getPath(curveAdaptedPoints)
            canvas.drawPath(averagePath, getAverageLinePaint())
        }
    }

    private fun drawZeroLine(canvas: Canvas) {
        canvas.drawLine(
            0f, getPointY(0.0, bounds(canvas)), bounds(canvas).width(),
            getPointY(0.0, bounds(canvas)), getZeroLinePaint()
        )
    }

    private fun getZeroLinePaint(): Paint {
        if (zeroLinePaint == null) {
            zeroLinePaint = Paint()
            zeroLinePaint!!.isAntiAlias = true
            zeroLinePaint!!.strokeWidth = ScreenUtils.dpToPixels(context, 1).toFloat()
        }
        return zeroLinePaint!!
    }

    private fun getCurvePoints(
        items: List<PeriodBalance>?,
        bounds: RectF
    ): List<PointF?> {
        val points: MutableList<PointF?> =
            ArrayList()
        if (items != null) {
            for (periodBalance in items) {
                points.add(getCurvePoint(periodBalance, bounds))
            }
        }
        return points
    }

    private fun getCurvePoint(
        periodBalance: PeriodBalance,
        bounds: RectF
    ): PointF {
        return PointF(
            getPointX(periodBalance.period.toString(), bounds),
            getPointY(periodBalance.amount, bounds)
        )
    }

    fun setZeroLinePaintColor(color: Int) {
        getZeroLinePaint().color = color
    }

    fun setSecondaryLineColor(color: Int) {
        getAverageLinePaint().color = color
    }

    private var maxWithoutAverage = 0.0
    private var maxOnlyAverage = 0.0

    fun setData(
        items: List<PeriodBalance>,
        averageItems: List<PeriodBalance>
    ) {
        this.items = items
        this.averageItems = averageItems
        val localItems: List<PeriodBalance> =
            ArrayList(items)
        val localAverageItems: List<PeriodBalance> =
            ArrayList(averageItems)
        Collections.sort(localItems) { a1, a2 -> a1.amount.compareTo(a2.amount) }
        Collections.sort(localAverageItems) { a1, a2 ->
            a1.amount.compareTo(a2.amount)
        }
        val minAndMaxItems =
            Manager.sharedInstance().getMinAndMaxValue(localItems)
        val maxValueItems = minAndMaxItems[0]
        val minValueItems = minAndMaxItems[1]
        maxWithoutAverage = max(maxValueItems, 0.0)
        val minAndMaxAverageItems = Manager.sharedInstance()
            .getMinAndMaxValue(localAverageItems)
        val maxValueAverageItems = minAndMaxAverageItems[0]
        val minValueAverageItems = minAndMaxAverageItems[1]
        maxOnlyAverage = max(maxValueAverageItems, 0.0)
        var maxValue = max(maxValueItems, maxValueAverageItems)
        var minValue = min(minValueItems, minValueAverageItems)
        maxValue = max(maxValue, 0.0)
        minValue = min(minValue, 0.0)
        val datesFromAvg =
            getDatesFromPeriodBalances(averageItems)
        val datesItems = getDatesFromPeriodBalances(items)
        dates = union(datesFromAvg, datesItems)
    }

    private fun union(
        list1: List<String>,
        list2: List<String>
    ): List<String> =
        mutableSetOf<String>().apply {
            addAll(list1)
            addAll(list2)
        }
            .sorted()

    private fun getDatesFromPeriodBalances(periodBalances: List<PeriodBalance>): List<String> {
        val dates: MutableList<String> =
            ArrayList()
        for (periodBalance in periodBalances) {
            if (periodBalance.period != null) {
                dates.add(periodBalance.period.toString())
            }
        }
        return dates
    }

    private fun drawDateMarker(canvas: Canvas, date: String?) {
        val index = getIndex(date)
        if (index < 0) {
            return
        }
        if (!markToday) {
            return
        }
        val dateTime = DateTime.parse(date)
        if (dateTime.isAfterNow) {
            return
        }
        val bounds = RectF(canvas.clipBounds)
        val x = getPointX(date, bounds)
        if (!MathUtils.isWithinBounds(index, linePoints)) {
            return
        }
        val point = linePoints!![index]
        val radius = todayDotPaintRadius
        // Tick
        val text =
            DateUtils.getInstance(locale, timezoneCode).formatDateHuman(markDate)
        val textY = bottom - ScreenUtils.dpToPixels(context, 16)
        val textBounds = Rect()
        // The 2 dp is textview padding from top
        val textViewPaddingTop = ScreenUtils.dpToPixels(context, 2)
        markedDateTextPaint!!.getTextBounds(text, 0, text.length, textBounds)
        left = x - textBounds.width() / 2 - ScreenUtils.dpToPixels(context, 10)
        right = x + textBounds.width() / 2 + ScreenUtils.dpToPixels(context, 10)
        top = (textY.toInt() - textBounds.height() / 2 - ScreenUtils.dpToPixels(context, 5)
                - textViewPaddingTop).toFloat()
        bottom = textY.toInt() + textBounds.height() / 2 + ScreenUtils.dpToPixels(
            context,
            5
        ).toFloat()
        val fillerRect = RectF()
        fillerRect[left, top, right] = bottom
        val radius3 = radius + ScreenUtils.dpToPixels(context, 15)
        markedDatePaint3!!.strokeWidth = 0f
        markedDatePaint3!!.shader = RadialGradient(
            point!!.x,
            point.y,
            radius3,
            markerGradientBottomColor,
            markerGradientTop50OpacityColor,
            Shader.TileMode.MIRROR
        )
        canvas.drawCircle(point.x, point.y, radius3, markedDatePaint3!!)
        val radius2 = radius + ScreenUtils.dpToPixels(context, 8)
        markedDatePaint2!!.strokeWidth = 0f
        markedDatePaint2!!.shader = RadialGradient(
            point.x,
            point.y,
            radius2,
            markerGradientBottomColor,
            markerGradientTop80OpacityColor,
            Shader.TileMode.MIRROR
        )
        canvas.drawCircle(point.x, point.y, radius2, markedDatePaint2!!)
        markedDatePaint?.style = if (DateUtils.getInstance(
                locale,
                timezoneCode
            ).isToday(date)
        ) Paint.Style.FILL_AND_STROKE else Paint.Style.STROKE
        canvas.drawCircle(point.x, point.y, radius, markedDatePaint!!)
    }

    private fun getIndex(date: String?): Int {
        if (date == null || dates!!.isEmpty()) {
            return -1
        }
        for (i in dates!!.indices) {
            if (dates!![i] == date) {
                return i
            }
        }
        return -1
    }

    private val datesToShow: List<String>?
        get() {
            if (dates == null) {
                return null
            }
            return if (dates!!.size < 3) {
                dates
            } else {
                val datesToShow: MutableList<String> =
                    Lists.newArrayList()
                val interval = (dates!!.size - 1) / 3.0
                datesToShow.add(dates!![(interval * 0.5).roundToInt()])
                datesToShow.add(dates!![(interval * 1.5).roundToInt()])
                datesToShow.add(dates!![(interval * 2.5).roundToInt()])
                datesToShow
            }
        }

    private val todayDotPaintRadius: Float
        get() = ScreenUtils.dpToPixels(context, 3.5f).toFloat()

    private fun makeRoomForXLabels(): Boolean {
        return XLabelAlignment.BOTTOM_OUTSIDE_CHARTAREA == showXLabels ||
                (XLabelAlignment.BOTTOM_INSIDE_FOR_POSITIVE_OUTSIDE_FOR_NEGATIVE == showXLabels
                        && minValue < 0)
    }

    private val xLabelBottomMargin: Float
        get() = if (showXLabels == XLabelAlignment.BOTTOM_INSIDE_FOR_POSITIVE_OUTSIDE_FOR_NEGATIVE || showXLabels == XLabelAlignment.BOTTOM_OUTSIDE_CHARTAREA || showXLabels == XLabelAlignment.BOTTOM_INSIDE_CHART_AREA
        ) {
            ScreenUtils.dpToPixels(context, 6).toFloat()
        } else 0f

    val lastLabelMarginNeeded: Int
        get() = ScreenUtils.dpToPixels(context, 31)

    val firstLabelMarginNeeded: Int
        get() = ScreenUtils.dpToPixels(context, 27)

    fun setDateMarkerCenterColor(color: Int) {
        markedDatePaint!!.color = color
    }

    fun setShowXLabels(showXLabels: XLabelAlignment?) {
        this.showXLabels = showXLabels
    }

    fun setShowYLabels(showYLabels: YLabelAlignment?) {
        this.showYLabels = showYLabels
    }

    fun setAmountLabelFontType(
        typeface: Typeface?,
        color: Int,
        textSize: Float
    ) {
        amountLabelsTypeface = typeface
        amountLabelsColor = color
        amountLabelsTextSize = textSize
    }

    fun setTodayDotPaintGradientColors(
        dateMarkerGradientBottom: Int,
        dateMarkerGradientTop50: Int, dateMarkerGradientTop80: Int
    ) {
        markerGradientBottomColor = dateMarkerGradientBottom
        markerGradientTop50OpacityColor = dateMarkerGradientTop50
        markerGradientTop80OpacityColor = dateMarkerGradientTop80
    }

    private fun getPath(points: List<PointF?>?): Path {
        val path = Path()
        if (points != null && points.isNotEmpty()) {
            val firstPoint = points[0]
            path.moveTo(firstPoint!!.x, firstPoint.y)
            var p1: PointF? = PointF(firstPoint.x, firstPoint.y)
            for (p2 in points) {
                path.quadTo(p1!!.x, p1.y, (p2!!.x + p1.x) / 2, (p2.y + p1.y) / 2)
                p1 = p2
            }
            val lastPoint = points[points.size - 1]
            path.lineTo(lastPoint!!.x, lastPoint.y)
        }
        return path
    }

    fun setLinePaint(color: Int) {
        linePaint!!.color = color
    }

    private var variant = VARIANT_DEFAULT
    fun setVariant(variant: Int) {
        this.variant = variant
    }

    private fun hasVariant(variant: Int): Boolean {
        return this.variant and variant == variant
    }

    companion object {
        const val VARIANT_DEFAULT = 0
        const val VARIANT_DASHED_AVERAGE = 1
        const val VARIANT_AREA_BELOW_ACTUAL = 2
    }
}