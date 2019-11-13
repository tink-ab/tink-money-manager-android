package com.tink.pfmsdk.theme

import android.content.Context
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import com.tink.pfmsdk.BaseFragment
import com.tink.pfmsdk.R
import com.tink.pfmsdk.overview.charts.ChartType
import com.tink.pfmsdk.view.TinkToolbar

interface TabPieChartFragmentTheme : BaseFragment.Theme {
    val iconTheme: CategoryIconTheme
    @get:AttrRes
    val chartItemColor: Int
}

fun getTabPieChartThemeForType(context: Context, type: ChartType): TabPieChartFragmentTheme {
    return when (type) {
        ChartType.EXPENSES -> ExpensesTabPieChartFragmentTheme(context)
        ChartType.LEFT_TO_SPEND -> throw IllegalArgumentException("Wrong type $type")
        ChartType.INCOME -> IncomeTabPieChartFragmentTheme(context)
    }
}

private class ExpensesTabPieChartFragmentTheme(private val context: Context) : TabPieChartFragmentTheme {
    override fun getToolbarTheme(): TinkToolbar.Theme = TinkExpensesToolbarTheme(context)
    override fun getStatusBarTheme(): StatusBarTheme = TinkExpenseStatusBarTheme(context)

    override val iconTheme: CategoryIconTheme = ExpensesIconTheme()
    override val chartItemColor: Int get() = R.attr.tink_expensesColor
}

private class IncomeTabPieChartFragmentTheme(private val context: Context) : TabPieChartFragmentTheme {
    override fun getToolbarTheme(): TinkToolbar.Theme = TinkIncomeToolbarTheme(context)
    override fun getStatusBarTheme(): StatusBarTheme = TinkIncomeStatusBarTheme(context)

    override val iconTheme: CategoryIconTheme = IncomeIconTheme()
    override val chartItemColor: Int get() = R.attr.tink_incomeColor
}

