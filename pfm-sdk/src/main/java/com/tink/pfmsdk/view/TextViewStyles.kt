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
internal open class H2(context: Context) : TextViewXmlTheme(context,
    R.style.h2
)

internal open class H2Action(context: Context) : TextViewXmlTheme(context,
    R.style.h2_action
)

internal open class Deci(context: Context) : TextViewXmlTheme(context,
    R.style.deci
)
internal open class DeciBold(context: Context) : TextViewXmlTheme(context,
    R.style.deci_bold
)
internal open class DeciPrimary(context: Context) : TextViewXmlTheme(context,
    R.style.deci_primary
)
internal open class DeciActionBold(context: Context) : TextViewXmlTheme(context,
    R.style.deci_action_bold
)
internal open class DeciDisabled(context: Context) : TextViewXmlTheme(context,
    R.style.deci_disabled
)

internal open class Giga(context: Context) : TextViewXmlTheme(context,
    R.style.giga
)
internal open class GigaRegular(context: Context) : TextViewXmlTheme(context,
    R.style.giga_regular
)

internal open class Hecto(context: Context) : TextViewXmlTheme(context,
    R.style.hecto
)
internal open class HectoSecondary(context: Context) : TextViewXmlTheme(context,
    R.style.hecto_secondary
)
internal open class HectoTertiary(context: Context) : TextViewXmlTheme(context,
    R.style.hecto_tertiary
)
internal open class HectoDisabled(context: Context) : TextViewXmlTheme(context,
    R.style.hecto_disabled
)

internal open class Mega(context: Context) : TextViewXmlTheme(context,
    R.style.mega
)
internal open class MegaBold(context: Context) : TextViewXmlTheme(context,
    R.style.mega_bold
)

internal open class Micro(context: Context) : TextViewXmlTheme(context,
    R.style.micro
)
internal open class MicroPrimary(context: Context) : TextViewXmlTheme(context,
    R.style.micro_primary
)

internal open class Nano(context: Context) : TextViewXmlTheme(context,
    R.style.nano
)
internal open class NanoPrimary(context: Context) : TextViewXmlTheme(context,
    R.style.nano_primary
)
internal open class NanoTertiary(context: Context) : TextViewXmlTheme(context,
    R.style.nano_tertiary
)
internal open class NanoTitle(context: Context) : TextViewXmlTheme(context,
    R.style.nano_title
)

internal open class Peta(context: Context) : TextViewXmlTheme(context,
    R.style.peta
)

internal open class Tera(context: Context) : TextViewXmlTheme(context,
    R.style.tera
)


internal open class TextViewXmlTheme(val context: Context, @StyleRes themeId: Int) :
    TinkTextView.Theme {
    override var textColor = 0
    private var typeface: Typeface? = null
    override var textSize = 0f
    override var lineHeight = 0f
    override val font: Typeface? = typeface
    override val spacing: Float = 0f
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

    override fun toUpperCase(): Boolean {
        return textAllCaps
    }
}
