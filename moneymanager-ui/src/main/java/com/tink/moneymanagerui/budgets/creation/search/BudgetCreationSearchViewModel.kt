package com.tink.moneymanagerui.budgets.creation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.tink.model.budget.Budget
import com.tink.model.transaction.Transaction
import com.tink.moneymanagerui.budgets.creation.BudgetCreationDataHolder
import com.tink.service.transaction.TransactionService
import se.tink.android.AppExecutors
import se.tink.android.livedata.requireValue
import se.tink.android.repository.transaction.AllTransactionPagesLiveData
import se.tink.android.repository.transaction.TransactionUpdateEventBus
import java.util.Locale
import javax.inject.Inject

internal class BudgetCreationSearchViewModel @Inject constructor(
    private val dataHolder: BudgetCreationDataHolder,
    transactionService: TransactionService,
    appExecutors: AppExecutors,
    transactionUpdateEventBus: TransactionUpdateEventBus
) : ViewModel() {

    private val rawTransactions = AllTransactionPagesLiveData(appExecutors, transactionService, transactionUpdateEventBus)

    private val transactions = MediatorLiveData<List<Transaction>?>()

    private val transactionDescriptions: LiveData<List<String>> = transactions.map { transactions ->
        transactions.orEmpty()
            .map { transaction ->
                transaction.description.trim()
            }
            .distinctBy { description ->
                description.toLowerCase(Locale.getDefault())
            }
            .sortedBy { description ->
                description.toLowerCase(Locale.getDefault())
            }
    }

    val searchText = MutableLiveData<String>().apply {
        value = ""
    }

    private val suggestionState: LiveData<SuggestionState> = MediatorLiveData<SuggestionState>().apply {
        value = SuggestionState()
        addSource(transactionDescriptions) { descriptions ->
            value = requireValue.copy(suggestions = descriptions)
        }
        addSource(searchText) { searchText ->
            value = requireValue.copy(searchText = searchText.trim())
        }
    }

    val visibleSuggestions: LiveData<List<String>> = suggestionState.map { suggestionState ->
        suggestionState.suggestions.filter { suggestion ->
            suggestion.contains(suggestionState.searchText, true)
        }
    }

    fun getTransactions() {
        rawTransactions.let { transactionPagesLiveData ->
            transactionPagesLiveData.dispose()
            transactions.removeSource(transactionPagesLiveData)
        }

        transactions.addSource(rawTransactions) { list: List<Transaction> ->
            transactions.value = list
        }
        rawTransactions.loadMoreItems()
    }

    val freeTextQuery = MutableLiveData<String?>().also { freeText ->
        dataHolder.selectedFilter.addSource(
            Transformations.map(freeText) {
                it?.let { freeText ->
                    Budget.Specification.Filter(
                        accounts = emptyList(),
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

data class SuggestionState(val suggestions: List<String> = emptyList(), val searchText: String = "")
