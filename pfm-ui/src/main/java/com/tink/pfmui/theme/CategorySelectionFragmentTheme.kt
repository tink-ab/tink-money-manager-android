package com.tink.pfmui.theme

import android.content.Context
import com.tink.pfmui.BaseFragment
import com.tink.pfmui.view.TinkToolbar
import se.tink.core.models.Category

internal interface CategorySelectionFragmentTheme : BaseFragment.Theme

internal fun getCategorySelectionThemeForType(context: Context, type: Category.Type): CategorySelectionFragmentTheme {
    return when (type) {
        Category.Type.TYPE_INCOME -> IncomeCategorySelectionFragmentTheme(context)
        Category.Type.TYPE_EXPENSES -> ExpensesCategorySelectionFragmentTheme(context)
        else -> throw IllegalArgumentException("Wrong type $type")
    }
}

private class ExpensesCategorySelectionFragmentTheme(private val context: Context) : CategorySelectionFragmentTheme {
    override fun getToolbarTheme(): TinkToolbar.Theme = TinkExpensesToolbarTheme(context)
    override fun getStatusBarTheme(): StatusBarTheme = TinkExpenseStatusBarTheme(context)
}

private class IncomeCategorySelectionFragmentTheme(private val context: Context) : CategorySelectionFragmentTheme {
    override fun getToolbarTheme(): TinkToolbar.Theme = TinkIncomeToolbarTheme(context)
    override fun getStatusBarTheme(): StatusBarTheme = TinkIncomeStatusBarTheme(context)
}