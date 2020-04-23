package com.tink.pfmui.overview.charts

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tink.pfmui.R
import com.tink.pfmui.util.toTreeListSelectionItem
import com.tink.pfmui.view.TreeListSelectionItem
import se.tink.android.categories.CategoryRepository
import se.tink.android.di.application.ApplicationScoped
import se.tink.commons.categories.getIcon
import se.tink.commons.categories.iconBackgroundColor
import se.tink.commons.categories.iconColor
import se.tink.core.models.Category
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
                Category.Type.TYPE_EXPENSES -> categoryTree?.expenses
                Category.Type.TYPE_INCOME -> categoryTree?.income
                else -> throw IllegalArgumentException("Unknown type $type")
            }
            mutableListOf<TreeListSelectionItem>().apply {
                parentCategory?.let { category ->
                    if (includeTopLevel) {
                        add(
                            TreeListSelectionItem.TopLevelItem(
                                id = category.code,
                                label = context.getString(R.string.tink_all_categories),
                                iconRes = category.getIcon(),
                                iconColor = category.iconColor(),
                                iconBackgroundColor = category.iconBackgroundColor(),
                                children = emptyList()
                            )
                        )
                    }
                    category.children
                        ?.sortedBy { item -> item.sortOrder }
                        ?.forEach { child ->
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