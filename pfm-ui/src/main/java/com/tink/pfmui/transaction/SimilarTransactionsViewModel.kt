package com.tink.pfmui.transaction

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tink.model.category.Category
import com.tink.pfmui.R
import se.tink.android.categories.CategoryRepository
import se.tink.android.di.application.ApplicationScoped
import se.tink.android.livedata.map
import se.tink.android.repository.transaction.TransactionRepository
import se.tink.commons.transactions.Marked
import se.tink.commons.transactions.SimilarTransactionsAdapter
import se.tink.commons.transactions.TransactionItemFactory
import se.tink.commons.extensions.whenNonNull
import com.tink.model.category.CategoryTree
import se.tink.commons.extensions.findCategoryByCode
import com.tink.model.transaction.Transaction
import se.tink.android.repository.TinkNetworkError
import se.tink.commons.extensions.findCategoryById
import javax.inject.Inject

internal class SimilarTransactionsViewModel  @Inject constructor(
    categoryRepository: CategoryRepository,
    private val transactionRepository: TransactionRepository,
    private val transactionItemFactory: TransactionItemFactory,
    @ApplicationScoped private val context: Context
) : ViewModel() {

    private val categoryTree: LiveData<CategoryTree> = categoryRepository.categories

    private val transactions = MutableLiveData<List<Transaction>>()

    private val categoryId = MutableLiveData<String>()

    fun initialize(transactionList: List<Transaction>, categoryId: String) {
        transactions.value = transactionList
        this.categoryId.value = categoryId
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
                    Marked.ALL -> context.getString(R.string.tink_transaction_similar_marker_button_unmark_text)
                    Marked.NONE -> context.getString(R.string.tink_transaction_similar_marker_button_mark_text)
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
                    categoryId.value
                ) { transactions, categoryTree, categoryId ->
                    val transactionItems =
                        transactions
                            .mapNotNull { transaction ->
                                categoryTree.findCategoryById(categoryId)?.let { category ->
                                    transactionItemFactory
                                        .similarTransactionItemFromTransaction(
                                            transaction,
                                            category
                                        )
                                        ?.apply {
                                            selected =
                                                markedTransactionsIds.value?.contains(id) ?: true
                                        }
                                }
                            }
                    postValue(transactionItems)
                }
            }
            addSource(categoryId) { update() }
            addSource(transactions) { update() }
            addSource(categoryTree) { update() }
            addSource(markedTransactionsIds) { update() }
        }

    val similarTransactionItems: LiveData<List<SimilarTransactionsAdapter.SimilarTransactionItem>> = _similarTransactionItems

    val category: LiveData<Category> = MediatorLiveData<Category>().apply {
        fun update() = whenNonNull(categoryTree.value, categoryId.value) { tree, id ->
            tree.findCategoryById(id)?.let(::postValue)
        }
        addSource(categoryId) {update()}
        addSource(categoryTree) {update()}
    }

    fun updateTransactions(transactionIDs: List<String>, categoryCode: String, onError: (TinkNetworkError) -> Unit) {
        transactionRepository.categorizeTransactions(
            transactionIds = transactionIDs,
            newCategoryId = categoryCode,
            onError = onError
        )
    }
}