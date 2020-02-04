package com.tink.pfmsdk.charts

import androidx.annotation.ColorInt

interface ColorGenerator {
    @ColorInt
    fun color(@ColorInt baseColor: Int, idx: Int): Int
}