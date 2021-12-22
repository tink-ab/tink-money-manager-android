package com.tink.moneymanagerui.theme

import android.content.Context
import com.tink.moneymanagerui.R
import se.tink.commons.extensions.getColorFromAttr

class TinkErrorSnackbarTheme(var context: Context) : TinkDefaultSnackbarTheme(context) {
    override val backgroundColor: Int
        get() = context.getColorFromAttr(R.attr.tink_warningColor)
}