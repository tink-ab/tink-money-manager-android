package com.tink.moneymanagerui.statistics.utils

import androidx.annotation.ColorInt

internal interface ColorGenerator {
    @ColorInt
    fun color(@ColorInt baseColor: Int, idx: Int): Int
}
