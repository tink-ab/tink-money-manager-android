package se.tink.commons.extensions

import com.tink.model.category.Category
import com.tink.model.category.CategoryTree

fun CategoryTree.findCategoryByCode(code: String): Category {
    TODO("Core setup")
}

fun CategoryTree.getCategoryByType(type: Category.Type): Category {
    TODO("Core setup")
}


val Category.type: Category.Type get() {
    TODO("Core setup")
}

val Category.parent: Category
    get() {
    TODO("Core setup")
}

fun Category.getNameWithDefaultChildFormat(defaultChildFormat: String?): String? {
    return if (isDefaultChild && parent.children.size > 1) {
        String.format(defaultChildFormat!!, name)
    } else {
        name
    }
}
