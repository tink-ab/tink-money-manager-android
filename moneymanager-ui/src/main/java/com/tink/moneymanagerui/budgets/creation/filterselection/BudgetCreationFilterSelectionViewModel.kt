package com.tink.moneymanagerui.budgets.creation.filterselection

import android.content.Context
import androidx.lifecycle.*
import com.tink.model.budget.Budget
import com.tink.moneymanagerui.budgets.creation.BudgetCreationDataHolder
import com.tink.moneymanagerui.util.findChildByCategoryId
import com.tink.moneymanagerui.util.toTreeListSelectionItem
import com.tink.moneymanagerui.view.TreeListSelectionItem
import se.tink.android.di.application.ApplicationScoped
import se.tink.android.categories.CategoryRepository
import se.tink.commons.extensions.findCategoryByCode
import se.tink.commons.livedata.Event
import com.tink.moneymanagerui.R
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
                add(
                    TreeListSelectionItem.ActionItem(
                        id = context.getString(R.string.tink_filter_search_item),
                        label = context.getString(R.string.tink_budget_create_with_keyword),
                        iconRes = R.attr.tink_icon_category_search,
                        action = { _searchClicked.postValue(Event(true)) }
                    )
                )
            }
        }

    val selectedTreeListItems = MediatorLiveData<List<TreeListSelectionItem>?>().also { liveData ->
        // Transform this to a Budget.Specification.Filter and then post it to the dataHolder.
        dataHolder.selectedFilter.addSource(
            Transformations.map(liveData) {
                it?.let { selectionItems ->
                    val selectedCategories = mutableListOf<Budget.Specification.Filter.Category>()
                    for (selectionItem in selectionItems) {
                        categoryRepository.categories.value
                            ?.expenses
                            ?.findChildByCategoryId(selectionItem.id)
                            ?.let { category ->
                                selectedCategories.add(Budget.Specification.Filter.Category(category.code))
                            }

                    }


                    Budget.Specification.Filter(
                        accounts = listOf(),
                        categories = selectedCategories,
                        tags = emptyList(), // Send empty list of tags until feature is implemented
                        freeTextQuery = "" // Send empty query until feature is implemented
                    )
                }
            }
        ) { data ->
            dataHolder.selectedFilter.postValue(data)
        }
    }

    fun selectionFilterItems(context: Context): List<TreeListSelectionItem> {
        return categoryRepository.categories.value?.let { categoryTree ->
            selectedFilter?.categories
                ?.map { it.code }
                ?.mapNotNull { code ->
                    categoryTree.findCategoryByCode(code)?.toTreeListSelectionItem(context)
                }
        }
            .orEmpty()
    }

    val isEditing get() = dataHolder.id.value != null
    val selectedFilter get() = dataHolder.selectedFilter.value
}