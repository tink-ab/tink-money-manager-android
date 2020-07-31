package com.tink.pfmui.theme

import android.content.Context
import com.tink.pfmui.transaction.SimilarTransactionsFragment
import com.tink.pfmui.util.getColorId
import com.tink.pfmui.view.TinkSnackbar
import com.tink.pfmui.view.TinkToolbar
import se.tink.commons.extensions.getColorFromAttr

internal class TinkTransactionSimilarTheme(
    private val context: Context
) : SimilarTransactionsFragment.Theme {

    private var categoryCode: String? = null

    override fun setCategory(categoryCode: String) {
        this.categoryCode = categoryCode
    }

    override fun getToolbarTheme(): TinkToolbar.Theme {
        return object : TinkDefaultToolbarTheme(context) {
            override val backgroundColor: Int
                get() = getColor()
        }
    }

    override fun getStatusBarTheme(): StatusBarTheme {
        return object : TinkDefaultStatusBarTheme(context) {
            override fun getStatusBarColor(): Int {
                return getColor()
            }
        }
    }

    override val snackbarErrorTheme: TinkSnackbar.Theme
        get() = TinkErrorSnackbarTheme(context)

    internal fun getColor(): Int {
        val id = getColorId(categoryCode ?: "")
        return context.getColorFromAttr(id)
    }
}