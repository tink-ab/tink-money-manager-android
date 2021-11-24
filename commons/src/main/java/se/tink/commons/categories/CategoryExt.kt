package se.tink.commons.categories

import androidx.annotation.AttrRes
import se.tink.commons.R
import se.tink.commons.categories.enums.CategoryExpenseType
import se.tink.commons.categories.enums.CategoryIncomeType
import se.tink.commons.categories.enums.CategoryTransferType
import se.tink.commons.categories.enums.CategoryType
import com.tink.model.category.Category

const val UNCATEGORIZED_CODE = "expenses:misc.uncategorized"

@AttrRes
fun Category.getIcon(): Int {
    return iconFromCategoryCode(code)
}

@AttrRes
fun iconFromCategoryCode(code: String): Int = when {
    code.isUncategorized() -> R.attr.tink_icon_category_uncategorized
    code == CategoryType.EXPENSES.stringCode -> R.attr.tink_icon_category_all_expenses
    code == CategoryType.INCOME.stringCode -> R.attr.tink_icon_category_all_income
    code.startsWith(CategoryExpenseType.EXPENSES_TRANSPORT.code) -> R.attr.tink_icon_category_expenses_transport
    code.startsWith(CategoryExpenseType.EXPENSES_FOOD.code) -> R.attr.tink_icon_category_expenses_foodanddrinks
    code.startsWith(CategoryExpenseType.EXPENSES_HOME.code) -> R.attr.tink_icon_category_expenses_home
    code.startsWith(CategoryExpenseType.EXPENSES_WELLNESS.code) -> R.attr.tink_icon_category_expenses_healthandbeauty
    code.startsWith(CategoryExpenseType.EXPENSES_ENTERTAINMENT.code) -> R.attr.tink_icon_category_expenses_leisure
    code.startsWith(CategoryExpenseType.EXPENSES_HOUSE.code) -> R.attr.tink_icon_category_expenses_houseandgarden
    code.startsWith(CategoryExpenseType.EXPENSES_MISC.code) -> R.attr.tink_icon_category_expenses_other
    code.startsWith(CategoryExpenseType.EXPENSES_SHOPPING.code) -> R.attr.tink_icon_category_expenses_shopping
    code.startsWith(CategoryIncomeType.INCOME_BENEFITS.code) -> R.attr.tink_icon_category_income_benefits
    code.startsWith(CategoryIncomeType.INCOME_OTHER.code) -> R.attr.tink_icon_category_income_other
    code.startsWith(CategoryIncomeType.INCOME_FINANCIAL.code) -> R.attr.tink_icon_category_income_financial
    code.startsWith(CategoryIncomeType.INCOME_PENSION.code) -> R.attr.tink_icon_category_income_pension
    code.startsWith(CategoryIncomeType.INCOME_SALARY.code) -> R.attr.tink_icon_category_income_salary
    code.startsWith(CategoryIncomeType.INCOME_REFUND.code) -> R.attr.tink_icon_category_income_reimbursements
    code.startsWith(CategoryTransferType.TRANSFERS_EXCLUDE.code) -> R.attr.tink_icon_category_transfers_exclude
    code.startsWith(CategoryTransferType.TRANSFERS_SAVINGS.code) -> R.attr.tink_icon_category_transfers_savings
    code.startsWith(CategoryTransferType.TRANSFERS_OTHER.code) -> R.attr.tink_icon_category_transfers_other
    else -> R.attr.tink_icon_category_uncategorized
}

@AttrRes
fun Category.iconColor(): Int {
    return when {
        code.isUncategorized() -> R.attr.tink_uncategorizedIconColor
        code.isIncome() -> R.attr.tink_incomeIconColor
        code.isExpense() -> R.attr.tink_expensesIconColor
        else -> R.attr.tink_transferIconColor
    }
}

@AttrRes
fun Category.iconBackgroundColor(): Int {
    return when {
        code.isUncategorized() -> R.attr.tink_uncategorizedIconBackgroundColor
        code.isIncome() -> R.attr.tink_incomeIconBackgroundColor
        code.isExpense() -> R.attr.tink_expensesIconBackgroundColor
        else -> R.attr.tink_transferIconBackgroundColor
    }
}

@AttrRes
fun Category.graphColor(): Int {
    return when {
        code.isUncategorized() -> R.attr.tink_uncategorizedColor
        code.isIncome() -> R.attr.tink_incomeColor
        code.isExpense() -> R.attr.tink_expensesColor
        else -> R.attr.tink_transferColor
    }
}

fun String.isUncategorized(): Boolean {
    return UNCATEGORIZED_CODE == this
}

fun String.isExcluded() = startsWith(CategoryTransferType.TRANSFERS_EXCLUDE.code)
fun String.isIncome() = startsWith(CategoryType.INCOME.stringCode)
fun String.isExpense() = startsWith(CategoryType.EXPENSES.stringCode)

