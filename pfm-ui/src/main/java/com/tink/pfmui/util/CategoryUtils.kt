@file:JvmName("CategoryUtils")

package com.tink.pfmui.util

import android.content.Context
import androidx.core.content.ContextCompat
import com.tink.pfmui.R
import com.tink.pfmui.view.TreeListSelectionItem
import se.tink.commons.categories.enums.CategoryExpenseType
import se.tink.commons.categories.enums.CategoryType
import se.tink.commons.categories.getIcon
import se.tink.commons.categories.iconBackgroundColor
import se.tink.commons.categories.iconColor
import se.tink.commons.categories.isUncategorized
import se.tink.core.models.Category

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
        children?.isEmpty() == false -> {
            children.mapNotNull { it.findChildByCode(code) }.firstOrNull()
        }
        else -> null
    }
}

private fun Category.nameWithDefaultChildFormat(context: Context): String {
    return if (
        isDefaultChild &&
        parent.children.size > 1 &&
        !this.code.startsWith(CategoryExpenseType.EXPENSES_MISC.code) // to avoid "Other Other"
    ) {
        String.format(context.getString(R.string.tink_category_default_child_format), name)
    } else {
        name
    }
}


internal fun Category.toTreeListSelectionItem(context: Context): TreeListSelectionItem {
    return if (children.isEmpty()) {
        TreeListSelectionItem.ChildItem(
            id = code,
            label = nameWithDefaultChildFormat(context)
        )
    } else {
        TreeListSelectionItem.TopLevelItem(
            id = code,
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

private fun getType(category: Category?): Category.Type = category?.type ?: Category.Type.TYPE_UNKKNOWN

private fun getDarkColorId(type: Category.Type): Int =
    when (type) {
        Category.Type.TYPE_EXPENSES -> R.attr.tink_expensesDarkColor
        Category.Type.TYPE_INCOME -> R.attr.tink_incomeDarkColor
        Category.Type.TYPE_TRANSFER,
        Category.Type.TYPE_UNKKNOWN -> R.attr.tink_transferColor
        else -> R.attr.tink_transferColor
    }

private fun getTextColorId(type: Category.Type): Int =
    when (type) {
        Category.Type.TYPE_EXPENSES -> R.attr.tink_colorOnExpenses
        Category.Type.TYPE_INCOME -> R.attr.tink_colorOnIncome
        Category.Type.TYPE_TRANSFER,
        Category.Type.TYPE_UNKKNOWN -> R.attr.tink_colorOnTransfer
        else -> R.attr.tink_colorOnTransfer
    }

private fun getColorId(type: Category.Type): Int =
    when (type) {
        Category.Type.TYPE_EXPENSES -> R.attr.tink_expensesColor
        Category.Type.TYPE_INCOME -> R.attr.tink_incomeColor
        Category.Type.TYPE_TRANSFER, Category.Type.TYPE_UNKKNOWN -> R.attr.tink_transferColor
        else -> R.attr.tink_transferColor
    }