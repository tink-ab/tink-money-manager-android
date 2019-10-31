package com.tink.pfmsdk.view

import android.content.Context
import android.graphics.Typeface
import androidx.annotation.StyleRes
import com.tink.pfmsdk.extensions.BooleanAttribute
import com.tink.pfmsdk.extensions.ColorAttribute
import com.tink.pfmsdk.extensions.DimensionAttribute
import com.tink.pfmsdk.extensions.FloatAttribute
import com.tink.pfmsdk.R
import com.tink.pfmsdk.extensions.TypefaceAttribute
import com.tink.pfmsdk.extensions.fetchThemeAttributesFromStyle
import javax.inject.Singleton

@Singleton
open class H2(context: Context) : TextViewXmlTheme(context,
    R.style.h2
)

open class H2Action(context: Context) : TextViewXmlTheme(context,
    R.style.h2_action
)

open class Deci(context: Context) : TextViewXmlTheme(context,
    R.style.deci
)
open class DeciBold(context: Context) : TextViewXmlTheme(context,
    R.style.deci_bold
)
open class DeciPrimary(context: Context) : TextViewXmlTheme(context,
    R.style.deci_primary
)
open class DeciActionBold(context: Context) : TextViewXmlTheme(context,
    R.style.deci_action_bold
)
open class DeciDisabled(context: Context) : TextViewXmlTheme(context,
    R.style.deci_disabled
)

open class Giga(context: Context) : TextViewXmlTheme(context,
    R.style.giga
)
open class GigaRegular(context: Context) : TextViewXmlTheme(context,
    R.style.giga_regular
)

open class Hecto(context: Context) : TextViewXmlTheme(context,
    R.style.hecto
)
open class HectoSecondary(context: Context) : TextViewXmlTheme(context,
    R.style.hecto_secondary
)
open class HectoTertiary(context: Context) : TextViewXmlTheme(context,
    R.style.hecto_tertiary
)
open class HectoDisabled(context: Context) : TextViewXmlTheme(context,
    R.style.hecto_disabled
)

open class Mega(context: Context) : TextViewXmlTheme(context,
    R.style.mega
)
open class MegaBold(context: Context) : TextViewXmlTheme(context,
    R.style.mega_bold
)

open class Micro(context: Context) : TextViewXmlTheme(context,
    R.style.micro
)
open class MicroPrimary(context: Context) : TextViewXmlTheme(context,
    R.style.micro_primary
)

open class Nano(context: Context) : TextViewXmlTheme(context,
    R.style.nano
)
open class NanoPrimary(context: Context) : TextViewXmlTheme(context,
    R.style.nano_primary
)
open class NanoTertiary(context: Context) : TextViewXmlTheme(context,
    R.style.nano_tertiary
)
open class NanoTitle(context: Context) : TextViewXmlTheme(context,
    R.style.nano_title
)

open class Peta(context: Context) : TextViewXmlTheme(context,
    R.style.peta
)

open class Tera(context: Context) : TextViewXmlTheme(context,
    R.style.tera
)


open class TextViewXmlTheme(val context: Context, @StyleRes themeId: Int) :
    TinkTextView.Theme {
    private var textColor = 0
    private var typeface: Typeface? = null
    private var textSize = 0f
    private var lineHeight = 0f
    private var letterSpacing = 0f
    private var textAllCaps = false

    init {
        val attrs = listOf(
            ColorAttribute(android.R.attr.textColor) { textColor = it },
            TypefaceAttribute(android.R.attr.fontFamily) {
                typeface = it
            },
            DimensionAttribute(android.R.attr.textSize) {
                textSize = it
            },
            DimensionAttribute(R.attr.lineHeight) {
                lineHeight = it
            },
            FloatAttribute(android.R.attr.letterSpacing) {
                letterSpacing = it
            },
            BooleanAttribute(android.R.attr.textAllCaps) {
                textAllCaps = it
            }
        )
        context.fetchThemeAttributesFromStyle(themeId, attrs)
    }

    override fun getTextColor(): Int {
        return textColor
    }

    override fun getFont(): Typeface? {
        return typeface
    }

    override fun getTextSize(): Float {
        return textSize
    }

    override fun getLineHeight(): Float {
        return lineHeight
    }

    override fun getSpacing(): Float {
        return letterSpacing
    }

    override fun toUpperCase(): Boolean {
        return textAllCaps
    }
}
