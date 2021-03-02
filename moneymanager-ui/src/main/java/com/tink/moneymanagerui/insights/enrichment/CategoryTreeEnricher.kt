package com.tink.moneymanagerui.insights.enrichment

import androidx.lifecycle.LiveData
import com.tink.model.category.CategoryTree
import com.tink.model.insights.Insight
import kotlinx.android.parcel.Parcelize
import se.tink.android.categories.CategoryRepository
import se.tink.android.livedata.map
import se.tink.android.livedata.switchMap
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

@Parcelize
data class CategoryTreeViewDetails(val categories: CategoryTree) : Insight.ViewDetails
