package com.tink.pfmsdk.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import com.tink.pfmsdk.R
import kotlin.math.roundToInt

private const val SCALE_ADJUSTMENT = 0.05f

internal class ViewPagerIndicator @JvmOverloads constructor(context: Context,
                                                   attrs: AttributeSet? = null,
                                                   defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    var hideStartAndEndCircles: Boolean = false
    @Suppress("JoinDeclarationAndAssignment")
    private val baseAlpha: Float
    private val circle: Drawable
    private val diameterMin: Int
    private val diameterMax: Int
    private var margin: Int

    init {
        baseAlpha = resources.getFraction(R.fraction.pager_indicator_base_alpha, 1, 1)
        circle = DrawableCompat.wrap(ContextCompat.getDrawable(context, R.drawable.circle)!!.mutate()).apply {
            setTint(readColor(attrs))
        }
        diameterMin = resources.getDimensionPixelSize(R.dimen.page_indicator_circle_size_min)
        diameterMax = resources.getDimensionPixelSize(R.dimen.page_indicator_circle_size_max)
        margin = resources.getDimension(R.dimen.page_indicator_margin).toInt()
    }

    private fun readColor(attrs: AttributeSet?): Int {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator)
        try {
            return a.getColor(R.styleable.ViewPagerIndicator_color, Color.BLACK)
        } finally {
            a.recycle()
        }
    }

    @Deprecated("Use initialize with view pager")
    fun initialize(size: Int) {
        setupCircles(size)
    }

    fun initialize(pager: ViewPager) {
        setupCircles(pager.adapter!!.count)
        pager.addOnPageChangeListener(object : SimpleOnPageChangeListener() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                shift(position, positionOffset)
            }
        })
    }

    private fun setupCircles(size: Int) {
        removeAllViews()
        for (i in 0 until size) {
            val child = View(context).apply {
                background = circle
                alpha = baseAlpha
                layoutParams = LinearLayout.LayoutParams(diameterMin, diameterMin).apply {
                    setMargins(margin, margin, margin, margin)
                }
                val hideCircle = hideStartAndEndCircles && (i == 0 || i == size - 1)
                visibility = if (hideCircle) View.INVISIBLE else View.VISIBLE
            }
            addView(child)
        }
    }

    fun shift(position: Int, value: Float) {
        with(getChildAt(position)) {
            alpha = baseAlpha + (1 - baseAlpha) * (1 - value)
            scale = scaleFactor(1 - value)
            layoutParams = (layoutParams as LinearLayout.LayoutParams).apply {
                adjustMargins(1 - value, position)
            }
        }
        val nextIndex = position + 1
        if (nextIndex < childCount) {
            with(getChildAt(nextIndex)) {
                alpha = baseAlpha + (1 - baseAlpha) * value
                scale = scaleFactor(value)
                layoutParams = (layoutParams as LinearLayout.LayoutParams).apply {
                    adjustMargins(value, nextIndex)
                }
            }
        }
    }

    private fun LinearLayout.LayoutParams.adjustMargins(fraction: Float, position: Int) {
        val adjustedVerticalMargin = marginFactor(fraction)

        val firstIndex = 0
        val lastIndex = childCount - 1

        when (position) {
            firstIndex -> setMargins(margin, margin, adjustedVerticalMargin, margin)
            lastIndex -> setMargins(adjustedVerticalMargin, margin, margin, margin)
            else -> setMargins(adjustedVerticalMargin, margin, adjustedVerticalMargin, margin)
        }
    }

    private fun scaleFactor(fraction: Float) = 1 + ((diameterMax - diameterMin) * fraction) / diameterMin

    private fun marginFactor(fraction: Float) = (margin + ((diameterMax - diameterMin) / 2) * fraction).roundToInt()
}

private var View.scale: Float
    get() = scaleX
    set(value) {
        val adjustedScale = value - SCALE_ADJUSTMENT
        scaleX = adjustedScale
        scaleY = adjustedScale
    }

