@file:JvmName("CategoryUtils")

package com.tink.pfmsdk.util

import android.content.Context
import androidx.core.content.ContextCompat
import com.tink.pfmsdk.R
import com.tink.pfmsdk.view.TreeListSelectionItem
import se.tink.commons.categories.enums.CategoryExpenseType
import se.tink.commons.categories.enums.CategoryType
import se.tink.commons.categories.getIcon
import se.tink.commons.categories.iconBackgroundColor
import se.tink.commons.categories.iconColor
import se.tink.commons.categories.isUncategorized
import se.tink.core.models.Category

const val REIMBURSEMENT_CODE = "income:refund.other"

fun String.isUncategorized(): Boolean = this.isUncategorized()

fun Category.isUncategorized(): Boolean {
    return code.isUncategorized()
}

fun String.isIncome(): Boolean {
    return this.startsWith(CategoryType.INCOME.stringCode)
}

fun String.isExpense(): Boolean {
    return this.startsWith(CategoryType.EXPENSES.stringCode)
}

fun String.isReimbursement(): Boolean {
    return REIMBURSEMENT_CODE == this
}

fun Category.findChildByCode(code: String): Category? {
    return when {
        this.code == code -> this
        children?.isEmpty() == false -> {
            children.mapNotNull { it.findChildByCode(code) }.firstOrNull()
        }
        else -> null
    }
}

fun Category.nameWithDefaultChildFormat(context: Context): String {
    return if (
        isDefaultChild &&
        parent.children.size > 1 &&
        !this.code.startsWith(CategoryExpenseType.EXPENSES_MISC.code) // to avoid "Other Other"
    ) {
        String.format(context.getString(R.string.category_default_child_format), name)
    } else {
        name
    }
}


fun Category.toTreeListSelectionItem(context: Context): TreeListSelectionItem {
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

fun getColor(context: Context, category: Category?): Int {
    val type = getType(category)
    val id = getColorId(type)
    return ContextCompat.getColor(context, id)
}

fun getDarkColor(context: Context, category: Category?): Int {
    val type = getType(category)
    val id = getDarkColorId(type)
    return ContextCompat.getColor(context, id)
}

fun getTextColor(context: Context, category: Category?): Int {
    val type = getType(category)
    val id = getTextColorId(type)
    return ContextCompat.getColor(context, id)
}

fun getType(category: Category?): Category.Type = category?.type ?: Category.Type.TYPE_UNKKNOWN

private fun getDarkColorId(type: Category.Type): Int =
    when (type) {
        Category.Type.TYPE_EXPENSES -> R.color.expensesDark
        Category.Type.TYPE_INCOME -> R.color.incomeDark
        Category.Type.TYPE_TRANSFER,
        Category.Type.TYPE_UNKKNOWN -> R.color.transferDark
        else -> R.color.transferDark
    }

private fun getTextColorId(type: Category.Type): Int =
    when (type) {
        Category.Type.TYPE_EXPENSES -> R.color.colorOnExpenses
        Category.Type.TYPE_INCOME -> R.color.colorOnIncome
        Category.Type.TYPE_TRANSFER,
        Category.Type.TYPE_UNKKNOWN -> R.color.colorOnTransfer
        else -> R.color.colorOnTransfer
    }

private fun getColorId(type: Category.Type): Int =
    when (type) {
        Category.Type.TYPE_EXPENSES -> R.color.expenses
        Category.Type.TYPE_INCOME -> R.color.income
        Category.Type.TYPE_TRANSFER, Category.Type.TYPE_UNKKNOWN -> R.color.transfer
        else -> R.color.transfer
    }