package com.tink.pfmsdk.transaction

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tink.pfmsdk.R
import se.tink.android.categories.CategoryRepository
import se.tink.android.di.application.ApplicationScoped
import se.tink.android.livedata.map
import se.tink.android.repository.transaction.TransactionRepository
import se.tink.commons.transactions.Marked
import se.tink.commons.transactions.SimilarTransactionsAdapter
import se.tink.commons.transactions.TransactionItemFactory
import se.tink.core.extensions.whenNonNull
import se.tink.core.models.category.CategoryTree
import se.tink.core.models.transaction.Transaction
import se.tink.repository.TinkNetworkError
import javax.inject.Inject

class SimilarTransactionsViewModel  @Inject constructor(
    categoryRepository: CategoryRepository,
    private val transactionRepository: TransactionRepository,
    private val transactionItemFactory: TransactionItemFactory,
    @ApplicationScoped private val context: Context
) : ViewModel() {

    private val categoryTree: LiveData<CategoryTree> = categoryRepository.categories

    private val transactions = MutableLiveData<List<Transaction>>()

    private val categoryCode = MutableLiveData<String>()

    fun initialize(transactionList: List<Transaction>, code: String) {
        transactions.value = transactionList
        categoryCode.value = code
    }

    private val markedTransactionsIds = MutableLiveData<List<String>>()

    fun updateMarkedTransactions(marked: List<String>) {
        markedTransactionsIds.value = marked
    }

    private val _markedState = MutableLiveData<Marked>().apply { value = Marked.ALL }
    val markButtonText: LiveData<String?> =
        _markedState.map {
            it?.let { marked ->
                when (marked) {
                    Marked.PARTIALLY,
                    Marked.ALL -> context.getString(R.string.transaction_similar_marker_button_unmark_text)
                    Marked.NONE -> context.getString(R.string.transaction_similar_marker_button_mark_text)
                }
            }
        }

    fun updateMarkedState(markedState: Marked) {
        _markedState.value = markedState
    }

    private val _similarTransactionItems =
        MediatorLiveData<List<SimilarTransactionsAdapter.SimilarTransactionItem>>().apply {
            fun update() {
                whenNonNull(
                    transactions.value,
                    categoryTree.value,
                    categoryCode.value
                ) { transactions, categoryTree, categoryCode ->
                    val transactionItems =
                        transactions
                            .mapNotNull { transaction ->
                                categoryTree.findCategoryByCode(categoryCode)?.let { category ->
                                    transactionItemFactory
                                        .similarTransactionItemFromTransaction(transaction, category)
                                        ?.apply {
                                            selected =
                                                markedTransactionsIds.value?.contains(id) ?: true
                                        }
                                }
                            }
                    postValue(transactionItems)
                }
            }
            addSource(categoryCode) { update() }
            addSource(transactions) { update() }
            addSource(categoryTree) { update() }
            addSource(markedTransactionsIds) { update() }
        }

    val similarTransactionItems: LiveData<List<SimilarTransactionsAdapter.SimilarTransactionItem>> = _similarTransactionItems

    fun updateTransactions(transactionIDs: List<String>, categoryCode: String, onError: (TinkNetworkError) -> Unit) {
        transactionRepository.categorizeTransactions(
            transactionIds = transactionIDs,
            newCategoryCode = categoryCode,
            onError = onError
        )
    }
}