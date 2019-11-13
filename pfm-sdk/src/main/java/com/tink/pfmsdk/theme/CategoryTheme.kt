package com.tink.pfmsdk.theme

import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import com.tink.pfmsdk.R

interface CategoryIconTheme {
    @get:AttrRes
    val iconColor: Int
    @get:AttrRes
    val iconCircleColor: Int
}

class ExpensesIconTheme : CategoryIconTheme {
    override val iconColor: Int get() = R.attr.tink_expensesColor
    override val iconCircleColor: Int get() = R.attr.tink_expensesLightColor
}

class IncomeIconTheme : CategoryIconTheme {
    override val iconColor: Int get() = R.attr.tink_incomeColor
    override val iconCircleColor: Int get() = R.attr.tink_incomeLightColor
}

