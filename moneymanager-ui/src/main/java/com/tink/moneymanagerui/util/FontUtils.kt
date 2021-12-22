package com.tink.moneymanagerui.util

import android.content.Context
import android.graphics.Typeface
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat
import com.tink.moneymanagerui.R

object FontUtils {
    fun getTypeface(@FontRes fontResId: Int, context: Context?): Typeface {
        return ResourcesCompat.getFont(context!!, fontResId)!!
    }

    val BOLD_FONT = R.font.tink_font_bold
    val REGULAR_FONT = R.font.tink_font_regular
}