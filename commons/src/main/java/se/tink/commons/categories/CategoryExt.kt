package se.tink.commons.categories

import androidx.annotation.DrawableRes
import se.tink.commons.R
import se.tink.commons.categories.enums.CategoryExpenseType
import se.tink.commons.categories.enums.CategoryIncomeType
import se.tink.commons.categories.enums.CategoryTransferType
import se.tink.commons.categories.enums.CategoryType
import se.tink.core.models.Category

const val UNCATEGORIZED_CODE = "expenses:misc.uncategorized"

@DrawableRes
fun Category.getIcon(): Int {
    return iconFromCategoryCode(code)
}

@DrawableRes
fun iconFromCategoryCode(code: String): Int = when {
    code.isUncategorized() -> R.drawable.ic_uncategorized
    code == CategoryType.EXPENSES.stringCode -> R.drawable.ic_all_expenses
    code == CategoryType.INCOME.stringCode -> R.drawable.ic_all_income
    code.startsWith(CategoryExpenseType.EXPENSES_TRANSPORT.code) -> R.drawable.ic_transport
    code.startsWith(CategoryExpenseType.EXPENSES_FOOD.code) -> R.drawable.ic_foodanddrinks
    code.startsWith(CategoryExpenseType.EXPENSES_HOME.code) -> R.drawable.ic_home
    code.startsWith(CategoryExpenseType.EXPENSES_WELLNESS.code) -> R.drawable.ic_healthandbeauty
    code.startsWith(CategoryExpenseType.EXPENSES_ENTERTAINMENT.code) -> R.drawable.ic_leisure
    code.startsWith(CategoryExpenseType.EXPENSES_HOUSE.code) -> R.drawable.ic_houseandgarden
    code.startsWith(CategoryExpenseType.EXPENSES_MISC.code) -> R.drawable.ic_other
    code.startsWith(CategoryExpenseType.EXPENSES_SHOPPING.code) -> R.drawable.ic_shopping
    code.startsWith(CategoryIncomeType.INCOME_BENEFITS.code) -> R.drawable.ic_benefits
    code.startsWith(CategoryIncomeType.INCOME_OTHER.code) -> R.drawable.ic_other
    code.startsWith(CategoryIncomeType.INCOME_FINANCIAL.code) -> R.drawable.ic_financial
    code.startsWith(CategoryIncomeType.INCOME_PENSION.code) -> R.drawable.ic_pension
    code.startsWith(CategoryIncomeType.INCOME_SALARY.code) -> R.drawable.ic_salary
    code.startsWith(CategoryIncomeType.INCOME_REFUND.code) -> R.drawable.ic_reimbursements
    code.startsWith(CategoryTransferType.TRANSFERS_EXCLUDE.code) -> R.drawable.ic_exclude
    code.startsWith(CategoryTransferType.TRANSFERS_SAVINGS.code) -> R.drawable.ic_savings
    code.startsWith(CategoryTransferType.TRANSFERS_OTHER.code) -> R.drawable.ic_transfer
    else -> R.drawable.ic_uncategorized
}

fun Category.iconColor(): Int {
    return when {
        code.isUncategorized() -> R.attr.tink_uncategorizedColor
        code.isIncome() -> R.attr.tink_incomeColor
        code.isExpense() -> R.attr.tink_expensesColor
        else -> R.attr.tink_transferColor //TODO:PFMSDK
    }
}

fun Category.iconBackgroundColor(): Int {
    return when {
        code.isUncategorized() -> R.attr.tink_uncategorizedLightColor
        code.isIncome() -> R.attr.tink_incomeLightColor
        code.isExpense() -> R.attr.tink_expensesLightColor
        else -> R.attr.tink_transferColor //TODO:PFMSDK
    }
}

fun String.isUncategorized(): Boolean {
    return UNCATEGORIZED_CODE == this
}

fun String.isExcluded() = startsWith(CategoryTransferType.TRANSFERS_EXCLUDE.code)
fun String.isIncome() = startsWith(CategoryType.INCOME.stringCode)
fun String.isExpense() = startsWith(CategoryType.EXPENSES.stringCode)

