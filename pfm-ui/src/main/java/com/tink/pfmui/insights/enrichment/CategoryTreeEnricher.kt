package com.tink.pfmui.insights.enrichment

import androidx.lifecycle.LiveData
import se.tink.android.categories.CategoryRepository
import se.tink.android.livedata.map
import se.tink.android.livedata.switchMap
import se.tink.core.models.category.CategoryTree
import se.tink.core.models.insights.Insight
import javax.inject.Inject

class CategoryTreeEnricher @Inject constructor(
    private val categoryRepository: CategoryRepository
) : InsightsEnricher {

    override fun enrich(input: LiveData<List<Insight>>): LiveData<List<Insight>> {
        return input.switchMap { insightList ->
            categoryRepository.categories.map { categoryTree ->
                insightList.onEach { it.viewDetails = CategoryTreeViewDetails(categoryTree) }
            }
        }
    }
}

data class CategoryTreeViewDetails(val categories: CategoryTree) : Insight.ViewDetails
