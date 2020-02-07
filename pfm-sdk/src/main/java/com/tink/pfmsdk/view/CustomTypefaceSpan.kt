package com.tink.pfmsdk.view

import android.annotation.SuppressLint
import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.TypefaceSpan

@SuppressLint("ParcelCreator")
internal class CustomTypefaceSpan(private val newType: Typeface, family: String? = null) :
    TypefaceSpan(family) {

    override fun updateDrawState(ds: TextPaint) {
        applyCustomTypeFace(ds, newType)
    }

    override fun updateMeasureState(paint: TextPaint) {
        applyCustomTypeFace(paint, newType)
    }

    private fun applyCustomTypeFace(paint: Paint, tf: Typeface) {
        val oldStyle: Int
        val old = paint.typeface
        oldStyle = old?.style ?: 0
        val newStyle = oldStyle and tf.style.inv()
        if (newStyle and Typeface.BOLD != 0) {
            paint.isFakeBoldText = true
        }
        if (newStyle and Typeface.ITALIC != 0) {
            paint.textSkewX = -0.25f
        }
        paint.typeface = tf
    }
}