package com.tink.moneymanagerui.theme

import android.content.Context
import android.graphics.Typeface
import com.tink.moneymanagerui.R
import se.tink.commons.extensions.getColorFromAttr
import com.tink.moneymanagerui.view.Hecto
import com.tink.moneymanagerui.util.FontUtils
import com.tink.moneymanagerui.view.TinkSnackbar
import com.tink.moneymanagerui.view.TinkTextView

open class TinkDefaultSnackbarTheme(private val context: Context) : TinkSnackbar.Theme {
    override val textTheme: TinkTextView.Theme
    override val buttonTheme: TinkTextView.Theme
    override val backgroundColor: Int
        get() = context.getColorFromAttr(R.attr.tink_snackbarColor)
    override val loadingIndicatorColor: Int
        get() = context.getColorFromAttr(R.attr.tink_colorOnSnackBar)

    init {
        textTheme = object : Hecto(context) {
            override var textColor: Int
                get() = context.getColorFromAttr(R.attr.tink_colorOnSnackBar)
                set(textColor) {
                    super.textColor = textColor
                }
        }
        buttonTheme = object : Hecto(context) {
            override var textColor: Int
                get() = context.getColorFromAttr(R.attr.tink_colorOnSnackBar)
                set(textColor) {
                    super.textColor = textColor
                }
            override var font: Typeface?
                get() = FontUtils.getTypeface(FontUtils.BOLD_FONT, context)
                set(font) {
                    super.font = font
                }
        }
    }
}