package com.tink.moneymanagerui.view

import android.content.Context
import android.graphics.Typeface
import androidx.annotation.StyleRes
import com.tink.annotations.PfmScope
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.extensions.BooleanAttribute
import com.tink.moneymanagerui.extensions.ColorAttribute
import com.tink.moneymanagerui.extensions.DimensionAttribute
import com.tink.moneymanagerui.extensions.FloatAttribute
import com.tink.moneymanagerui.extensions.TypefaceAttribute
import com.tink.moneymanagerui.extensions.fetchThemeAttributesFromStyle

@PfmScope
internal open class H2(context: Context) : TextViewXmlTheme(context,
    R.style.tink_h2
)

internal open class H2Action(context: Context) : TextViewXmlTheme(context,
    R.style.tink_h2_action
)

internal open class Deci(context: Context) : TextViewXmlTheme(context,
    R.style.tink_deci
)
internal open class DeciBold(context: Context) : TextViewXmlTheme(context,
    R.style.tink_deci_bold
)
internal open class DeciPrimary(context: Context) : TextViewXmlTheme(context,
    R.style.tink_deci_primary
)
internal open class DeciActionBold(context: Context) : TextViewXmlTheme(context,
    R.style.tink_deci_action_bold
)
internal open class DeciDisabled(context: Context) : TextViewXmlTheme(context,
    R.style.tink_deci_disabled
)

internal open class Giga(context: Context) : TextViewXmlTheme(context,
    R.style.tink_giga
)
internal open class GigaRegular(context: Context) : TextViewXmlTheme(context,
    R.style.tink_giga_regular
)

internal open class Hecto(context: Context) : TextViewXmlTheme(context,
    R.style.tink_hecto
)
internal open class HectoSecondary(context: Context) : TextViewXmlTheme(context,
    R.style.tink_hecto_secondary
)
internal open class HectoTertiary(context: Context) : TextViewXmlTheme(context,
    R.style.tink_hecto_tertiary
)
internal open class HectoDisabled(context: Context) : TextViewXmlTheme(context,
    R.style.tink_hecto_disabled
)

internal open class Mega(context: Context) : TextViewXmlTheme(context,
    R.style.tink_mega
)
internal open class MegaBold(context: Context) : TextViewXmlTheme(context,
    R.style.tink_mega_bold
)

internal open class Micro(context: Context) : TextViewXmlTheme(context,
    R.style.tink_micro
)
internal open class MicroPrimary(context: Context) : TextViewXmlTheme(context,
    R.style.tink_micro_primary
)

internal open class Nano(context: Context) : TextViewXmlTheme(context,
    R.style.tink_nano
)
internal open class NanoPrimary(context: Context) : TextViewXmlTheme(context,
    R.style.tink_nano_primary
)
internal open class NanoTertiary(context: Context) : TextViewXmlTheme(context,
    R.style.tink_nano_tertiary
)
internal open class NanoTitle(context: Context) : TextViewXmlTheme(context,
    R.style.tink_nano_title
)

internal open class Tera(context: Context) : TextViewXmlTheme(context,
    R.style.tink_tera
)


internal open class TextViewXmlTheme(val context: Context, @StyleRes themeId: Int) :
    TinkTextView.Theme {
    override var textColor = 0
    override var textSize = 0f
    override var lineHeight = 0f
    override var font: Typeface? = null
    override val spacing: Float = 0f
    private var letterSpacing = 0f
    private var textAllCaps = false

    init {
        val attrs = listOf(
            ColorAttribute(android.R.attr.textColor) {
                textColor = it
            },
            TypefaceAttribute(android.R.attr.fontFamily) {
                font = it
            },
            DimensionAttribute(android.R.attr.textSize) {
                textSize = it
            },
            DimensionAttribute(R.attr.tink_lineHeight) {
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
