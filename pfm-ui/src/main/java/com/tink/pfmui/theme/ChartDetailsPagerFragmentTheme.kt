package com.tink.pfmui.theme

import android.content.Context
import com.tink.pfmui.BaseFragment
import com.tink.pfmui.overview.charts.ChartType
import com.tink.pfmui.view.TinkTabs
import com.tink.pfmui.view.TinkToolbar

internal interface ChartDetailsPagerFragmentTheme : BaseFragment.Theme {
    val tabsTheme: TinkTabs.Theme
}

internal fun getChartDetailsThemeForType(context: Context, type: ChartType): ChartDetailsPagerFragmentTheme {
    return when (type) {
        ChartType.EXPENSES -> ExpensesChartPageTheme(context)
        ChartType.LEFT_TO_SPEND -> LeftToSpendChartPageTheme(context)
        ChartType.INCOME -> IncomeChartPageTheme(context)
    }
}

private class ExpensesChartPageTheme(private val context: Context) : ChartDetailsPagerFragmentTheme {
    override fun getToolbarTheme(): TinkToolbar.Theme = TinkExpensesToolbarTheme(context)
    override fun getStatusBarTheme(): StatusBarTheme = TinkExpenseStatusBarTheme(context)
    override val tabsTheme: TinkTabs.Theme = TinkExpensesTabsTheme(context)
}

private class IncomeChartPageTheme(private val context: Context) : ChartDetailsPagerFragmentTheme {
    override fun getToolbarTheme(): TinkToolbar.Theme = TinkIncomeToolbarTheme(context)
    override fun getStatusBarTheme(): StatusBarTheme = TinkIncomeStatusBarTheme(context)
    override val tabsTheme: TinkTabs.Theme = TinkIncomeTabsTheme(context)
}

private class LeftToSpendChartPageTheme(private val context: Context) : ChartDetailsPagerFragmentTheme {
    override fun getToolbarTheme(): TinkToolbar.Theme = TinkLeftToSpendToolbarTheme(context)
    override fun getStatusBarTheme(): StatusBarTheme = TinkLeftToSpendStatusBarTheme(context)
    override val tabsTheme: TinkTabs.Theme = TinkLeftToSpendTabsTheme(context)
}

