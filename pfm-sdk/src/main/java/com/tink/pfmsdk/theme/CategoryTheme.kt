package com.tink.pfmsdk.theme

import androidx.annotation.ColorRes
import com.tink.pfmsdk.R

interface CategoryIconTheme {
    @get:ColorRes
    val iconColor: Int
    @get:ColorRes
    val iconCircleColor: Int
}

class ExpensesIconTheme : CategoryIconTheme {
    override val iconColor: Int get() = R.color.expenses
    override val iconCircleColor: Int get() = R.color.expensesLight
}

class IncomeIconTheme : CategoryIconTheme {
    override val iconColor: Int get() = R.color.income
    override val iconCircleColor: Int get() = R.color.incomeLight
}

