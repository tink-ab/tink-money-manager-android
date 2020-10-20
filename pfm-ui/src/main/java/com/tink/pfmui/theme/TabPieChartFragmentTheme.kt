package com.tink.pfmui.theme

import androidx.annotation.AttrRes
import com.tink.pfmui.R
import com.tink.pfmui.overview.charts.ChartType

internal interface TabPieChartFragmentTheme {
    val iconTheme: CategoryIconTheme
    @get:AttrRes
    val chartItemColor: Int
}

internal fun getTabPieChartThemeForType(type: ChartType): TabPieChartFragmentTheme {
    return when (type) {
        ChartType.EXPENSES -> ExpensesTabPieChartFragmentTheme()
        ChartType.LEFT_TO_SPEND -> throw IllegalArgumentException("Wrong type $type")
        ChartType.INCOME -> IncomeTabPieChartFragmentTheme()
    }
}

private class ExpensesTabPieChartFragmentTheme : TabPieChartFragmentTheme {
    override val iconTheme: CategoryIconTheme = ExpensesIconTheme()
    override val chartItemColor: Int get() = R.attr.tink_expensesColor
}

private class IncomeTabPieChartFragmentTheme : TabPieChartFragmentTheme {
    override val iconTheme: CategoryIconTheme = IncomeIconTheme()
    override val chartItemColor: Int get() = R.attr.tink_incomeColor
}

