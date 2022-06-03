package com.tink.moneymanagerui.category

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tink.model.category.Category
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.util.toTreeListSelectionItem
import com.tink.moneymanagerui.view.TreeListSelectionItem
import se.tink.android.categories.CategoryRepository
import se.tink.android.di.application.ApplicationScoped
import se.tink.commons.categories.getIcon
import se.tink.commons.categories.iconBackgroundColor
import se.tink.commons.categories.iconColor
import javax.inject.Inject

internal class CategorySelectionViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    @ApplicationScoped private val context: Context
) : ViewModel() {

    fun getCategoryItems(
        type: Category.Type,
        includeTopLevel: Boolean = true,
        includeTransfers: Boolean = true
    ): LiveData<List<TreeListSelectionItem>> {
        return Transformations.map(categoryRepository.categories) { categoryTree ->
            val parentCategory = when (type) {
                Category.Type.EXPENSE -> categoryTree?.expenses
                Category.Type.INCOME -> categoryTree?.income
                else -> throw IllegalArgumentException("Unknown type $type")
            }
            mutableListOf<TreeListSelectionItem>().apply {
                parentCategory?.let { category ->
                    if (includeTopLevel) {
                        add(
                            TreeListSelectionItem.TopLevelItem(
                                id = category.id,
                                label = context.getString(R.string.tink_all_categories),
                                iconRes = category.getIcon(),
                                iconColor = category.iconColor(),
                                iconBackgroundColor = category.iconBackgroundColor(),
                                children = emptyList()
                            )
                        )
                    }
                    category.children
                        .sortedBy { item -> item.sortOrder }
                        .forEach { child ->
                            if (!child.isDefaultChild) {
                                add(child.toTreeListSelectionItem(context))
                            }
                        }
                    if (includeTransfers) {
                        categoryTree?.transfers?.children?.forEach {
                            add(it.toTreeListSelectionItem(context))
                        }
                    }
                }
            }
        }
    }
}
