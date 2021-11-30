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
import se.tink.commons.categories.enums.CategoryExpenseType
import se.tink.commons.extensions.whenNonNull
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

    val selectedFilterToItems = MediatorLiveData<List<TreeListSelectionItem>>().apply {
        fun update() {
            whenNonNull(
                categoryRepository.categories.value,
                dataHolder.selectedFilter.value
            ) { categoryTree, selectedFilter ->
                postValue(
                    selectedFilter.categories
                        .map { it.code }
                        .mapNotNull { code ->
                            categoryTree.findCategoryByCode(code)?.toTreeListSelectionItem(context)
                        }
                )
            }
        }
        addSource(categoryRepository.categories) { update() }
        addSource(dataHolder.selectedFilter) { update() }
    }

    private val _searchClicked = MutableLiveData<Event<Boolean>>()
    val searchClicked: LiveData<Event<Boolean>> = _searchClicked

    private val _allExpensesClicked = MutableLiveData<Event<Boolean>>()
    val allExpensesClicked: LiveData<Event<Boolean>> = _allExpensesClicked

    internal val filterItems = MediatorLiveData<List<TreeListSelectionItem>>().apply {
        fun update() {
            treeListSelectionItems.value
                ?.takeIf { it.isNotEmpty() }
                ?.let { categories ->
                    listOf(TreeListSelectionItem.ActionItem(
                        id = context.getString(R.string.tink_filter_all_expenses_item),
                        label = context.getString(R.string.tink_budget_create_all_expenses),
                        iconRes = R.attr.tink_icon_category_all_expenses,
                        action = { _allExpensesClicked.postValue(Event(true)) }
                    ))
                        .plus(categories)
                        .plus(TreeListSelectionItem.ActionItem(
                            id = context.getString(R.string.tink_filter_search_item),
                            label = context.getString(R.string.tink_budget_create_with_keyword),
                            iconRes = R.attr.tink_icon_category_search,
                            action = { _searchClicked.postValue(Event(true)) }))
                        .also {
                            postValue(it.updateBy(selectedFilterToItems.value))
                        }
                }
        }
        addSource(treeListSelectionItems) { update() }
        addSource(selectedFilterToItems) { update() }
    }

    val selectedTreeListItems = MutableLiveData<List<TreeListSelectionItem>?>()

    fun onSelectionDone() {
        selectedTreeListItems.value?.let { selectionItems ->
            val selectedCategories = selectionItems.mapNotNull { selectionItem ->
                categoryRepository.categories.value
                    ?.expenses
                    ?.findChildByCategoryId(selectionItem.id)
                    ?.let { category ->
                        Budget.Specification.Filter.Category(category.code)
                    }
            }
            dataHolder.selectedFilter.postValue(
                Budget.Specification.Filter(
                    accounts = listOf(),
                    categories = selectedCategories,
                    tags = emptyList(), // Send empty list of tags until feature is implemented
                    freeTextQuery = "" // Send empty query until feature is implemented
                )
            )
        }
    }

    fun onAllExpensesSelected() {
        dataHolder.selectedFilter.postValue(
            Budget.Specification.Filter(
                accounts = emptyList(),
                categories = listOf(Budget.Specification.Filter.Category(CategoryExpenseType.EXPENSES_ALL.code)),
                tags = emptyList(),
                freeTextQuery = ""
            )
        )
    }

    val showActionButton = MediatorLiveData<Boolean>().apply {
        value = false
        fun update() {
            postValue(
                !selectedTreeListItems.value.isNullOrEmpty() || !selectedFilterToItems.value.isNullOrEmpty()
            )
        }
        addSource(selectedTreeListItems) { update() }
        addSource(selectedFilterToItems) { update() }
    }

    val isEditing get() = dataHolder.id.value != null
}

private fun List<TreeListSelectionItem>.updateBy(other: List<TreeListSelectionItem>?): List<TreeListSelectionItem> {
    if (other != null && other.isNotEmpty()) {
        forEach { thisItem ->
            other.forEach { otherItem ->
                otherItem.takeIf {
                    when (thisItem) {
                        is TreeListSelectionItem.ChildItem -> thisItem.id == otherItem.id
                        is TreeListSelectionItem.TopLevelItem ->
                            thisItem.id == otherItem.id || thisItem.children.any { it.id == otherItem.id }
                        else -> false
                    }
                }?.let { matchingOtherItem ->
                    when (thisItem) {
                        is TreeListSelectionItem.ChildItem -> thisItem.isSelected = true
                        is TreeListSelectionItem.TopLevelItem -> {
                            if (thisItem.id == matchingOtherItem.id) {
                                thisItem.isSelected = true
                            } else {
                                thisItem.children.firstOrNull { it.id == matchingOtherItem.id }
                                    ?.let {
                                        it.isSelected = true
                                        thisItem.isExpanded = true
                                    }
                            }
                        }
                        else -> {}
                    }
                }
            }
        }
    }
    return this
}