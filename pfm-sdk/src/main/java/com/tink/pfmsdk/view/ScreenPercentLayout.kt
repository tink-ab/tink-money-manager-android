package com.tink.pfmsdk.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.tink.pfmsdk.R

internal class ScreenPercentLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr) {

    private var widthPercent: Float
    private var heightPercent: Float

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ScreenPercentLayout, defStyleAttr, 0)
        try {
            widthPercent = a.getFloat(R.styleable.ScreenPercentLayout_width_percent, -1f)
            heightPercent = a.getFloat(R.styleable.ScreenPercentLayout_height_percent, -1f)
        } finally {
            a.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var wSpec = widthMeasureSpec
        var hSpec = heightMeasureSpec
        val dm = resources.displayMetrics
        if (widthPercent in 0f..1f) {
            wSpec = MeasureSpec.makeMeasureSpec((dm.widthPixels * widthPercent).toInt(), MeasureSpec.EXACTLY)
        }
        if (heightPercent in 0f..1f) {
            hSpec = MeasureSpec.makeMeasureSpec((dm.heightPixels * heightPercent).toInt(), MeasureSpec.EXACTLY)
        }
        super.onMeasure(wSpec, hSpec)
    }
}