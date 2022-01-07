package com.tink.moneymanagerui.util

import android.content.Context
import kotlin.math.ceil

object DimensionUtils {
    fun getPixelsFromDP(dp: Float, context: Context): Float {
        return ceil((dp * context.resources.displayMetrics.density).toDouble())
            .toFloat()
    }

    fun getEmFromDp(dp: Float): Float {
        return 0.0624f * dp
    }
}