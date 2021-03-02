package com.tink.pfmui.budgets.creation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tink.model.budget.Budget
import com.tink.pfmui.budgets.creation.BudgetCreationDataHolder
import se.tink.commons.extensions.whenNonNull
import javax.inject.Inject

internal class BudgetCreationSearchViewModel @Inject constructor(
    private val dataHolder: BudgetCreationDataHolder
) : ViewModel() {

    val tags: LiveData<List<String>> = MutableLiveData<List<String>>().apply { value = emptyList() }

    val searchText = MutableLiveData<String>().apply {
        value = ""
    }

    val suggestions = MediatorLiveData<List<String>>().apply {
        fun update() {
            whenNonNull(
                tags.value,
                searchText.value
            ) { tags, searchText ->
                value = tags.filter { it.contains(other = searchText, ignoreCase = true) }
            }
        }
        addSource(tags) { update() }
        addSource(searchText) { update() }
    }

    val selectedTag = MutableLiveData<String?>().also { liveData ->
        // Transform this to a Budget.Specification.Filter and then post it to the dataHolder.
        dataHolder.selectedFilter.addSource(
            Transformations.map(liveData) {
                it?.let { selectedTag ->
                    Budget.Specification.Filter(
                        accounts = listOf(),
                        categories = emptyList(),
                        tags = listOfNotNull(Budget.Specification.Filter.Tag(selectedTag)),
                        freeTextQuery = "" // TODO after MVP
                    )
                }
            }
        ) { filter ->
            dataHolder.selectedFilter.postValue(filter)
        }
    }

    val freeTextQuery = MutableLiveData<String?>().also { liveData ->
        // Transform this to a Budget.Specification.Filter and then post it to the dataHolder.
        dataHolder.selectedFilter.addSource(
            Transformations.map(liveData) {
                it?.let { freeText ->
                    Budget.Specification.Filter(
                        accounts = listOf(),
                        categories = emptyList(),
                        tags = emptyList(),
                        freeTextQuery = freeText
                    )
                }
            }
        ) { filter ->
            dataHolder.selectedFilter.postValue(filter)
        }
    }
}