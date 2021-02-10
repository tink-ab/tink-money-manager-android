package com.tink.pfmui.budgets.creation.filterselection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import android.content.Context
import com.tink.model.budget.Budget
import com.tink.pfmui.R
import com.tink.pfmui.budgets.creation.BudgetCreationDataHolder
import com.tink.pfmui.util.findChildByCategoryId
import com.tink.pfmui.util.toTreeListSelectionItem
import com.tink.pfmui.view.TreeListSelectionItem
import se.tink.android.di.application.ApplicationScoped
import se.tink.android.categories.CategoryRepository
import se.tink.commons.livedata.Event
import javax.inject.Inject

internal class BudgetCreationFilterSelectionViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val dataHolder: BudgetCreationDataHolder,
    @ApplicationScoped context: Context
) : ViewModel() {

    private val treeListSelectionItems: LiveData<List<TreeListSelectionItem>> =
        Transformations.map(categoryRepository.categories) {
            it?.expenses?.children
                ?.sortedBy { item -> item.sortOrder }
                ?.map { child -> child.toTreeListSelectionItem(context) }
        }

    private val _searchClicked = MutableLiveData<Event<Boolean>>()
    val searchClicked: LiveData<Event<Boolean>> = _searchClicked

    internal val filterItems: LiveData<List<TreeListSelectionItem>> =
        Transformations.map(treeListSelectionItems) { treeListSelectionItems ->
            mutableListOf<TreeListSelectionItem>().apply {
                addAll(treeListSelectionItems)
                // Hide create by keyword or tag option until implemented
//                add(
//                    TreeListSelectionItem.ActionItem(
//                        id = context.getString(R.string.tink_filter_search_item),
//                        label = context.getString(R.string.tink_budget_create_search_label),
//                        iconRes = R.drawable.tink_search,
//                        action = { _searchClicked.postValue(Event(true)) }
//                    )
//                )
            }
        }

    val selectedTreeListItem = MutableLiveData<TreeListSelectionItem?>().also { liveData ->
        // Transform this to a Budget.Specification.Filter and then post it to the dataHolder.
        dataHolder.selectedFilter.addSource(
            Transformations.map(liveData) {
                it?.let { selectionItem ->
                    val selectedCategory = categoryRepository.categories.value
                        ?.expenses
                        ?.findChildByCategoryId(selectionItem.id)
                        ?.let { category -> Budget.Specification.Filter.Category(category.code) }

                    Budget.Specification.Filter(
                        accounts = listOf(),
                        categories = listOfNotNull(selectedCategory),
                        tags = emptyList(), // Send empty list of tags until feature is implemented
                        freeTextQuery = "" // Send empty query until feature is implemented
                    )
                }
            }
        ) { data ->
            dataHolder.selectedFilter.postValue(data)
        }
    }

    val isEditing get() = dataHolder.id.value != null
    val selectedFilter get() = dataHolder.selectedFilter.value
}