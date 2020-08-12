@file:JvmName("CategoryUtils")

package com.tink.pfmui.util

import android.content.Context
import androidx.core.content.ContextCompat
import com.tink.model.category.Category
import com.tink.pfmui.R
import com.tink.pfmui.view.TreeListSelectionItem
import se.tink.commons.categories.enums.CategoryType
import se.tink.commons.categories.getIcon
import se.tink.commons.categories.iconBackgroundColor
import se.tink.commons.categories.iconColor
import se.tink.commons.categories.isUncategorized

private const val REIMBURSEMENT_CODE = "income:refund.other"

internal fun String.isUncategorized(): Boolean = this.isUncategorized()

internal fun Category.isUncategorized(): Boolean {
    return code.isUncategorized()
}

internal fun String.isIncome(): Boolean {
    return this.startsWith(CategoryType.INCOME.stringCode)
}

internal fun String.isExpense(): Boolean {
    return this.startsWith(CategoryType.EXPENSES.stringCode)
}

internal fun String.isReimbursement(): Boolean {
    return REIMBURSEMENT_CODE == this
}

internal fun Category.findChildByCode(code: String): Category? {
    return when {
        this.code == code -> this
        !children?.isEmpty() -> {
            children.mapNotNull { it.findChildByCode(code) }.firstOrNull()
        }
        else -> null
    }
}

internal fun Category.toTreeListSelectionItem(context: Context): TreeListSelectionItem {
    return if (children.isEmpty()) {
        TreeListSelectionItem.ChildItem(
            id = id,
            label = name
        )
    } else {
        TreeListSelectionItem.TopLevelItem(
            id = id,
            label = name,
            iconRes = getIcon(),
            iconColor = iconColor(),
            iconBackgroundColor = iconBackgroundColor(),
            children = children
                .sortedBy { it.sortOrder }
                .takeIf { !(it.size == 1 && it[0].isDefaultChild) }
                ?.map { it.toTreeListSelectionItem(context) }
                ?: emptyList()
        )
    }
}

internal fun getColor(context: Context, category: Category?): Int {
    val type = getType(category)
    val id = getColorId(type)
    return ContextCompat.getColor(context, id)
}

internal fun getDarkColor(context: Context, category: Category?): Int {
    val type = getType(category)
    val id = getDarkColorId(type)
    return ContextCompat.getColor(context, id)
}

internal fun getTextColor(context: Context, category: Category?): Int {
    val type = getType(category)
    val id = getTextColorId(type)
    return ContextCompat.getColor(context, id)
}

private fun getType(category: Category?): Category.Type = category?.type ?: Category.Type.EXPENSE

private fun getDarkColorId(type: Category.Type): Int =
    when (type) {
        Category.Type.EXPENSE -> R.attr.tink_expensesDarkColor
        Category.Type.INCOME -> R.attr.tink_incomeDarkColor
        Category.Type.TRANSFER -> R.attr.tink_transferColor
    }

private fun getTextColorId(type: Category.Type): Int =
    when (type) {
        Category.Type.EXPENSE -> R.attr.tink_colorOnExpenses
        Category.Type.INCOME -> R.attr.tink_colorOnIncome
        Category.Type.TRANSFER -> R.attr.tink_colorOnTransfer
    }

private fun getColorId(type: Category.Type): Int =
    when (type) {
        Category.Type.EXPENSE -> R.attr.tink_expensesColor
        Category.Type.INCOME -> R.attr.tink_incomeColor
        Category.Type.TRANSFER -> R.attr.tink_transferColor
        else -> R.attr.tink_transferColor
    }

fun getColorId(categoryCode: String): Int =
    when {
        categoryCode.isExpense() ->  R.attr.tink_expensesColor
        categoryCode.isIncome() ->  R.attr.tink_incomeColor
        else -> R.attr.tink_transferColor
    }
