package com.tink.moneymanagerui.util

import androidx.core.graphics.ColorUtils
import kotlin.math.roundToInt

object ColorsUtils {
    fun adjustAlpha(baseColor: Int, factor: Float): Int {
        val alpha = (255 * factor).roundToInt()
        return ColorUtils.setAlphaComponent(baseColor, alpha)
    }
}