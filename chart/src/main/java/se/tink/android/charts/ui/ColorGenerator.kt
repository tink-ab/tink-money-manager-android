package se.tink.android.charts.ui

import androidx.annotation.ColorInt

interface ColorGenerator {
    @ColorInt
    fun color(@ColorInt baseColor: Int, idx: Int): Int
}