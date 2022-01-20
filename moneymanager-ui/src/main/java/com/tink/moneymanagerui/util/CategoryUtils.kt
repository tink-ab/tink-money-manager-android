@file:JvmName("CategoryUtils")

package com.tink.moneymanagerui.util

import android.content.Context
import com.tink.model.category.Category
import com.tink.moneymanagerui.view.TreeListSelectionItem
import se.tink.commons.categories.getIcon
import se.tink.commons.categories.iconBackgroundColor
import se.tink.commons.categories.iconColor

internal fun Category.findChildByCategoryId(categoryId: String): Category? {
    return when {
        this.id == categoryId -> this
        children.isNotEmpty() -> {
            children.mapNotNull { it.findChildByCategoryId(categoryId) }.firstOrNull()
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
