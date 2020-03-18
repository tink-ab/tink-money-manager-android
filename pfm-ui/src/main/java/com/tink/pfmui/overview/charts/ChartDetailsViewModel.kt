package com.tink.pfmui.overview.charts

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.content.res.Resources
import se.tink.android.categories.CategoryRepository
import com.tink.model.category.Category
import se.tink.commons.extensions.findCategoryByCode
import se.tink.commons.extensions.getCategoryByType
import javax.inject.Inject

internal class ChartDetailsViewModel @Inject constructor(private val categoryRepository: CategoryRepository) : ViewModel() {

    private val categoryType = MutableLiveData<Category.Type>()
    private val categoryId = MutableLiveData<String>()

    val category = MediatorLiveData<Category>().apply {
        fun update() {
            val type = categoryType.value
            val id = categoryId.value
            val categoryTree = categoryRepository.categories.value
            value = when {
                id != null -> categoryTree?.findCategoryByCode(id)
                type != null -> categoryTree?.getCategoryByType(type)
                else -> null
            }
        }

        addSource(categoryRepository.categories) { update() }
        addSource(categoryId) { update() }
        addSource(categoryType) { update() }
    }

    fun getCategoryTitle(resources: Resources, category: Category?, type: ChartType): CharSequence {
        return if (category != null && resources.getText(type.title) != category.name) {
            category.name
        } else {
            resources.getText(type.topCategoryName)
        }
    }

    fun setCategoryId(id: String) {
        categoryId.value = id
    }

    fun setType(type: Category.Type) {
        categoryType.value = type
    }
}
