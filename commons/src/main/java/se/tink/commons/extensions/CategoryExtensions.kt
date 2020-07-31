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
    for (childCategory in category.children) {
        val returnCategory = findRecursive(childCategory, categoryCode)
        if (returnCategory != null) {
            return returnCategory
        }
    }
    return null
}

fun CategoryTree.findCategoryById(id: String): Category? =
    findRecursiveById(expenses, id)
        ?: findRecursiveById(income, id)
        ?: findRecursiveById(transfers, id)


fun findRecursiveById(category: Category, categoryId: String): Category? {
    if (category.id == categoryId) {
        return category
    }
    for (childCategory in category.children) {
        val returnCategory = findRecursiveById(childCategory, categoryId)
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

val Category.recursiveIdList: List<String>
    get() = listOf(id) + children.flatMap { it.recursiveIdList }
