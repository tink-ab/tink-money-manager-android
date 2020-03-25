package com.tink.pfmui.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import se.tink.android.categories.CategoryRepository
import se.tink.android.livedata.switchMap
import se.tink.android.repository.transaction.SingleTransactionLiveData
import se.tink.android.repository.transaction.TransactionError
import se.tink.android.repository.transaction.TransactionReceived
import se.tink.android.repository.transaction.TransactionRepository
import se.tink.commons.livedata.Event
import se.tink.commons.extensions.whenNonNull
import com.tink.model.category.Category
import se.tink.commons.extensions.findCategoryByCode
import com.tink.model.transaction.Transaction
import se.tink.android.repository.TinkNetworkError
import javax.inject.Inject

internal class CategorizationFlowViewModel @Inject constructor(
    categoryRepository: CategoryRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private var transactionLiveData: SingleTransactionLiveData? = null


    fun setTransactionId(id: String) =
        setTransactionSource(transactionRepository.fetchById(id))

    private fun setTransactionSource(liveData: SingleTransactionLiveData) {
        transactionLiveData?.dispose()
        transactionLiveData?.let {
            _transaction.removeSource(it)
            _errors.removeSource(it)
        }

        transactionLiveData = liveData.also {
            _transaction.addSource(it) { result ->
                if (result is TransactionReceived) _transaction.value = result.transaction
            }

            _errors.addSource(it) { result ->
                if (result is TransactionError) _errors.value = Event(result.error)
            }
        }
    }

    private val _errors = MediatorLiveData<Event<TinkNetworkError>>()
    val errors: LiveData<Event<TinkNetworkError>> = _errors

    private val _transaction = MediatorLiveData<Transaction>()
    val transaction: LiveData<Transaction> = _transaction

    val categories = categoryRepository.categories

    val transactionCategory: LiveData<Category> = MediatorLiveData<Category>().apply {
        fun update() =
            whenNonNull(
                categories.value,
                transaction.value?.categoryCode
            ) { tree, code ->
                tree.findCategoryByCode(code)?.let { value = it }
            }
        addSource(transaction) { update() }
        addSource(categories) { update() }
    }


    val similarTransactions: LiveData<List<Transaction>?> = transaction.switchMap {
        transactionRepository.fetchSimilarTransactions(it.id)
    }

    fun categorizeTransactions(
        transactionIds: List<String>,
        newCategoryCode: String
    ) {
        transactionRepository.categorizeTransactions(transactionIds, newCategoryCode) {
            _errors.postValue(Event(it))
        }
    }


    private val _state: MediatorLiveData<State> = MediatorLiveData<State>().apply {

        addSource(transaction) {
            if (value == State.LoadingTransaction) value = State.CategorySelection(it)
        }

        value = State.LoadingTransaction
    }
    val state: LiveData<State> = _state


    fun categorySelected(categoryCode: String) {

        val transaction = transaction.value ?: return

        if (categoryCode != transaction.categoryCode) {
            _state.value = State.SimilarTransactions(categoryCode)
            categorizeTransactions(listOf(transaction.id), categoryCode)
        } else {
           setStatusToDone()
        }
    }

    fun similarTransactionsDone() = setStatusToDone()
    fun categorySelectionCancelled() = setStatusToDone()

    private fun setStatusToDone() {
        _state.value = State.Done
    }

    override fun onCleared() {
        super.onCleared()
        transactionLiveData?.dispose()
    }

    sealed class State {
        object LoadingTransaction : State()
        class CategorySelection(val transaction: Transaction) : State()
        class SimilarTransactions(val updatedCategoryCode: String) : State()
        object Done : State()
    }
}
