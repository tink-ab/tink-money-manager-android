package com.tink.pfmsdk.theme

import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import com.tink.pfmsdk.R

internal interface CategoryIconTheme {
    @get:AttrRes
    val iconColorAttr: Int
    @get:AttrRes
    val iconCircleColorAttr: Int
}

internal class ExpensesIconTheme : CategoryIconTheme {
    override val iconColorAttr: Int get() = R.attr.tink_expensesColor
    override val iconCircleColorAttr: Int get() = R.attr.tink_expensesLightColor
}

internal class IncomeIconTheme : CategoryIconTheme {
    override val iconColorAttr: Int get() = R.attr.tink_incomeColor
    override val iconCircleColorAttr: Int get() = R.attr.tink_incomeLightColor
}

