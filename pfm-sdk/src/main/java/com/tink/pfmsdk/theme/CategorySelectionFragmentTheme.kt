package com.tink.pfmsdk.theme

import android.content.Context
import com.tink.pfmsdk.BaseFragment
import com.tink.pfmsdk.view.TinkToolbar
import se.tink.core.models.Category

internal interface CategorySelectionFragmentTheme : BaseFragment.Theme

internal fun getCategorySelectionThemeForType(context: Context, type: Category.Type): CategorySelectionFragmentTheme {
//    return when (type) {
//        Category.Type.TYPE_INCOME -> IncomeCategorySelectionFragmentTheme(context)
//        Category.Type.TYPE_EXPENSES -> ExpensesCategorySelectionFragmentTheme(context)
//        else -> throw IllegalArgumentException("Wrong type $type")
//    }
    // TODO: Confirm with design the expected behavior - do we change the toolbar according to new category type?
    // Do we change the buttons and checkboxes colors too?
    return DefaultCategorySelectionFragmentTheme(context)
}

private class ExpensesCategorySelectionFragmentTheme(private val context: Context) : CategorySelectionFragmentTheme {
    override fun getToolbarTheme(): TinkToolbar.Theme = TinkExpensesToolbarTheme(context)
    override fun getStatusBarTheme(): StatusBarTheme = TinkExpenseStatusBarTheme(context)
}

private class IncomeCategorySelectionFragmentTheme(private val context: Context) : CategorySelectionFragmentTheme {
    override fun getToolbarTheme(): TinkToolbar.Theme = TinkIncomeToolbarTheme(context)
    override fun getStatusBarTheme(): StatusBarTheme = TinkIncomeStatusBarTheme(context)
}

private class DefaultCategorySelectionFragmentTheme(private val context: Context) : CategorySelectionFragmentTheme {
    override fun getToolbarTheme(): TinkToolbar.Theme = TinkDefaultToolbarTheme(context)
    override fun getStatusBarTheme(): StatusBarTheme = TinkDefaultStatusBarTheme(context)
}