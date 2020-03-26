package se.tink.commons.extensions

import com.tink.model.category.Category
import com.tink.model.category.CategoryTree

fun CategoryTree.findCategoryByCode(code: String): Category? =
    findRecursive(expenses, code)
        ?: findRecursive(income, code)
        ?: findRecursive(transfers, code)

fun findRecursive(category: Category, categoryCode: String): Category? {
    if (category.code == categoryCode) {
        return category
    }
    if (category.children.isEmpty()) {
        return null
    }
    for (childCategory in category.children) {
        val returnCategory = findRecursive(childCategory, categoryCode)
        if (returnCategory != null) {
            return returnCategory
        }
    }
    return null
}

fun CategoryTree.getCategoryByType(type: Category.Type): Category =
    when (type) {
        Category.Type.INCOME -> income
        Category.Type.EXPENSE -> expenses
        Category.Type.TRANSFER -> transfers
    }

val Category.parent: Category
    get() {
    TODO("Core setup - how should we implement this?")
}

fun Category.getNameWithDefaultChildFormat(defaultChildFormat: String?): String? {
    return if (isDefaultChild && parent.children.size > 1) {
        String.format(defaultChildFormat!!, name)
    } else {
        name
    }
}
