package com.tink.moneymanagerui.charts

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Property
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.view.ViewGroupOverlay
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.charts.extensions.childOrNull
import com.tink.moneymanagerui.charts.extensions.children
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin
import kotlin.properties.ObservableProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private const val ZERO_ANGLE = -90.0

internal class PieChartView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ViewGroup(context, attrs, defStyleAttr) {

    private val overlayViews = mutableListOf<View>()
    private var maxRadiusPercent: Float

    val segments get() = children.asSequence().mapNotNull { it as? PieChartSegmentView }
    val labels get() = children.asSequence().mapNotNull { it as? PieChartLabelView }

    private val bounds get() = Rect(paddingLeft, paddingTop, width - paddingRight, height - paddingBottom)

    var outerRadius by observable(0f) { invalidateChildren() }
    var transitionOuterRadius by observable(0f) { invalidateChildren() }

    var thicknessRatio by observable(0.25f) { invalidateChildren() }
    var thickness by observable(0f) { invalidateChildren() }
    var transitionThickness by observable(0f) { invalidateChildren() }

    var onInnerDiameterDetermined: (innerDiameter: Int) -> Unit = {}

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.TinkPieChartView)
        maxRadiusPercent = a.getFloat(R.styleable.TinkPieChartView_tink_max_radius_percent, 1f)
        thicknessRatio = a.getFloat(R.styleable.TinkPieChartView_tink_thickness_percent, 0.25f)
        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        for (child in children) {
            child.measure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    // Can be called to calculate layout parameters without doing any heavy lifting
    fun preLayout(width: Int, height: Int) {
        val radius = minOf(width - paddingLeft - paddingRight, height - paddingTop - paddingBottom).toFloat() / 2f
        val maxLabelRadius = labels.maxByOrNull { it.radialSize }?.radialSize ?: 0f
        var pieRadius = maxOf(0f, radius - maxLabelRadius)
        pieRadius = minOf(pieRadius, maxRadiusPercent * radius)

        outerRadius = pieRadius
        thickness = pieRadius * thicknessRatio
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        preLayout(width, height)

        val bounds = this.bounds
        for (segment in segments) segment.layout(bounds)

        for (label in labels) label.prelayout(bounds.centerX(), bounds.centerY(), outerRadius.toInt())
        LabelLayout.layout(labels.toList())
        for (label in labels) label.layout(left, top, right, bottom)

        val innerDiameter = (outerRadius - thickness) * 2
        onInnerDiameterDetermined(innerDiameter.toInt())
    }

    private fun invalidateChildren() {
        children.forEach { it.invalidate() }
        overlayViews.forEach { it.invalidate() }
    }

    fun createSegment() = PieChartSegmentView(context)

    fun layoutAndAddToOverlay(view: PieChartSegmentView) {
        view.layout(bounds)
        super.getOverlay().add(view)
        overlayViews.add(view)
    }

    fun clearOverlay() {
        super.getOverlay().clear()
        overlayViews.clear()
    }

    override fun getOverlay(): ViewGroupOverlay = throw IllegalAccessError("Use layoutAndAddToOverlay and clearOverlay methods")

    inner class PieChartSegmentView(context: Context) : View(context) {
        private val path = Path()
        private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
        }

        // Can be used to group segments together for transition purposes
        var transitionGroup = 0

        // Value from 0 to 360
        var startAngle by observable(0f) { invalidate() }
        var angle by observable(0f) { invalidate() }

        // Overrides color set directly below.
        var colorStateList: ColorStateList? by observable(null) {
            updateColorFromState()
        }

        // Current color. Can be changed anytime with state change if [colorStateList] is set
        var color: Int
            get() = paint.color
            set(value) {
                paint.color = value
                invalidate()
            }

        private var centerX by observable(0f) { invalidate() }
        private var centerY by observable(0f) { invalidate() }

        private val touchSlop = ViewConfiguration.get(context).scaledTouchSlop

        override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
            centerX = w / 2f
            centerY = h / 2f
        }

        override fun onDraw(canvas: Canvas) {
            val outerRadius = outerRadius + transitionOuterRadius
            val thickness = thickness + transitionThickness

            paint.strokeWidth = thickness
            val drawRadius = outerRadius - thickness / 2

            path.reset()
            path.addArc(
                centerX - drawRadius,
                centerY - drawRadius,
                centerX + drawRadius,
                centerY + drawRadius,
                startAngle + ZERO_ANGLE.toFloat(),
                angle
            )
            canvas.drawPath(path, paint)
        }

        override fun drawableStateChanged() {
            super.drawableStateChanged()
            updateColorFromState()
        }

        private fun updateColorFromState() {
            colorStateList?.let {
                color = it.getColorForState(drawableState, color)
            }
        }

        @SuppressLint("ClickableViewAccessibility")
        override fun onTouchEvent(event: MotionEvent): Boolean {
            if (!hasOnClickListeners()) {
                // Do not pass events for not clickable views, they will go pressed state
                return false
            }
            val x = event.x
            val y = event.y
            val actionMasked = event.actionMasked
            if (actionMasked == MotionEvent.ACTION_DOWN) {
                val pointAngle = (Math.toDegrees(atan2(y - centerY, x - centerX).toDouble()) - ZERO_ANGLE + 360f) % 360
                if (pointAngle < startAngle || pointAngle > startAngle + angle) {
                    return false
                }
                val pointRadius = hypot(x - centerX, y - centerY)
                if (pointRadius < outerRadius - thickness - touchSlop || pointRadius > outerRadius + touchSlop) {
                    return false
                }
            }
            return super.onTouchEvent(event)
        }
    }

    companion object {

        val THICKNESS: Property<PieChartView, Float> =
            object : Property<PieChartView, Float>(Float::class.java, "thickness") {
                override fun get(item: PieChartView) = item.thickness
                override fun set(item: PieChartView, value: Float) {
                    item.thickness = value
                }
            }

        val RADIUS: Property<PieChartView, Float> =
            object : Property<PieChartView, Float>(Float::class.java, "outerRadius") {
                override fun get(item: PieChartView) = item.outerRadius
                override fun set(item: PieChartView, value: Float) {
                    item.outerRadius = value
                }
            }

        val ANGLE: Property<PieChartSegmentView, Float> =
            object : Property<PieChartSegmentView, Float>(Float::class.java, "angle") {
                override fun get(item: PieChartSegmentView) = item.angle
                override fun set(item: PieChartSegmentView, value: Float) {
                    item.angle = value
                }
            }

        val START_ANGLE: Property<PieChartSegmentView, Float> =
            object : Property<PieChartSegmentView, Float>(Float::class.java, "startAngle") {
                override fun get(item: PieChartSegmentView) = item.startAngle
                override fun set(item: PieChartSegmentView, value: Float) {
                    item.startAngle = value
                }
            }

        val COLOR: Property<PieChartSegmentView, Int> =
            object : Property<PieChartSegmentView, Int>(Int::class.java, "color") {
                override fun get(item: PieChartSegmentView) = item.color
                override fun set(item: PieChartSegmentView, value: Int) {
                    item.color = value
                }
            }
    }
}

@SuppressLint("ViewConstructor")
internal open class PieChartLabelView(context: Context, anchorAngle: Float) : ViewGroup(context) {

    private var centerX = 0f
    private var centerY = 0f
    private var baseRadius = 0f

    var anchorAngle by observable(anchorAngle) { invalidate() }

    var decorator: Decorator? by observable(null) {
        if (decorator != null) setWillNotDraw(false)
    }

    var radialPadding = 0f

    var centerAngle = 0f

    private val childRadius
        get() = childOrNull(0)?.let {
            hypot(it.measuredWidth / 2f, it.measuredHeight / 2f)
        } ?: 0f

    val radialSize get() = 2 * childRadius + radialPadding

    private val childCenterRadius get() = baseRadius + radialPadding + childRadius
    val archSize: Float get() = (2 * Math.toDegrees(asin((childRadius / childCenterRadius).toDouble()))).toFloat()

    private val anchor = PointF()
    private val center = PointF()
    private val tmp = PointF()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val childWidthSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.UNSPECIFIED)
        val childHeightSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.UNSPECIFIED)

        childOrNull(0)?.measure(childWidthSpec, childHeightSpec)
    }

    fun prelayout(centerX: Int, centerY: Int, outerRadius: Int) {
        this.centerX = centerX.toFloat()
        this.centerY = centerY.toFloat()
        this.baseRadius = outerRadius.toFloat()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        fillCenter(center, centerAngle)
        val cx = center.x.toInt()
        val cy = center.y.toInt()
        childOrNull(0)?.let {
            val w = it.measuredWidth / 2
            val h = it.measuredHeight / 2
            it.layout(cx - w, cy - h, cx + w, cy + h)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        decorator?.draw(canvas)
    }

    fun getAnchor() = anchor.also {
        it.x = centerX + baseRadius * cos(Math.toRadians(anchorAngle + ZERO_ANGLE)).toFloat()
        it.y = centerY + baseRadius * sin(Math.toRadians(anchorAngle + ZERO_ANGLE)).toFloat()
    }

    private fun fillCenter(center: PointF, angle: Float) {
        center.x = (centerX + childCenterRadius * cos(Math.toRadians(angle + ZERO_ANGLE))).toFloat()
        center.y = (centerY + childCenterRadius * sin(Math.toRadians(angle + ZERO_ANGLE))).toFloat()
    }

    private fun setCenterAngleTranslation(value: Float) {
        fillCenter(center, centerAngle)
        fillCenter(tmp, value)
        childOrNull(0)?.let {
            it.translationX = tmp.x - center.x
            it.translationY = tmp.y - center.y
        }
    }

    interface Decorator {
        var visible: Boolean
        fun draw(canvas: Canvas)
    }

    companion object {
        val ANCHOR_ANGLE: Property<PieChartLabelView, Float> =
            object : Property<PieChartLabelView, Float>(Float::class.java, "anchorAngle") {
                override fun get(item: PieChartLabelView) = item.anchorAngle
                override fun set(item: PieChartLabelView, value: Float) {
                    item.anchorAngle = value
                }
            }

        val TRANSITION_CENTER_ANGLE: Property<PieChartLabelView, Float> =
            object : Property<PieChartLabelView, Float>(Float::class.java, "transitionCenterAngle") {
                override fun get(item: PieChartLabelView) = throw NotImplementedError()
                override fun set(item: PieChartLabelView, value: Float) {
                    item.setCenterAngleTranslation(value)
                }
            }
    }
}

private fun <T> observable(initialValue: T, onAfterChange: () -> Unit): ReadWriteProperty<Any?, T> =
    object : ObservableProperty<T>(initialValue) {
        override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) = onAfterChange()
    }

private fun View.layout(b: Rect) = layout(b.left, b.top, b.right, b.bottom)
