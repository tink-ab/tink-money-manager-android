package com.tink.moneymanagerui.transaction

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tink.model.transaction.Transaction
import com.tink.service.transaction.TransactionService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import se.tink.android.AppExecutors
import se.tink.android.categories.CategoryRepository
import se.tink.android.livedata.map
import se.tink.android.repository.TinkNetworkError
import se.tink.android.repository.transaction.AllTransactionPagesLiveData
import se.tink.android.repository.transaction.CategoryTransactionPagesLiveData
import se.tink.android.repository.transaction.LeftToSpendTransactionPagesLiveData
import se.tink.android.repository.transaction.TransactionPagesLiveData
import se.tink.android.repository.transaction.TransactionRepository
import se.tink.android.repository.transaction.TransactionUpdateEventBus
import se.tink.commons.extensions.findCategoryById
import se.tink.commons.extensions.whenNonNull
import se.tink.commons.livedata.Event
import se.tink.commons.transactions.ListItem
import se.tink.commons.transactions.TransactionItemFactory
import javax.inject.Inject


internal open class TransactionListViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    categoryRepository: CategoryRepository,
    private val transactionService: TransactionService,
    private val appExecutors: AppExecutors,
    private val transactionItemFactory: TransactionItemFactory,
    private val transactionUpdateEventBus: TransactionUpdateEventBus
) : ViewModel() {


    private val _errors = MutableLiveData<Event<TinkNetworkError>>()
    val errors: LiveData<Event<TinkNetworkError>> = _errors

    @SuppressLint("CheckResult")
    private fun presetTransactions(ids: List<String>): TransactionPagesLiveData {

        return object : TransactionPagesLiveData() {

            val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

            override fun dispose() = scope.cancel()

            override fun loadMoreItems() {}

            init {
                scope.launch {
                    val transactions = try {
                        transactionRepository.fromIdList(ids)
                    } catch (error: Throwable) {
                        emptyList<Transaction>()
                    }
                    postValue(transactions)
                }
            }
        }
    }

    private val categories = categoryRepository.categories

    private var rawTransactions: TransactionPagesLiveData? = null

    private var _transactions = MediatorLiveData<List<Transaction>?>()

    private val _loading = MediatorLiveData<Boolean>().apply {
        addSource(_transactions) { if (it != null) postValue(false) }
    }
    val loading: LiveData<Boolean> = _loading

    val transactionItems: LiveData<TransactionItems> =
        MediatorLiveData<TransactionItems>().apply {
            fun update() = whenNonNull(
                _transactions.value, categories.value
            ) { transactions, categories ->
                fun Transaction.toItem(): ListItem.TransactionItem? {
                    val category = categories.findCategoryById(categoryId) ?: return null
                    return transactionItemFactory.fromTransaction(this, category)
                }
                val transactionsList = mutableListOf<Transaction>().apply { addAll(transactions) }
                value = TransactionItems(
                    transactions = transactionsList.mapNotNull { it.toItem() }
                )
            }
            addSource(_transactions) { update() }
            addSource(categories) { update() }
        }

    fun setListMode(mode: TransactionListMode) {
        rawTransactions?.dispose()
        rawTransactions?.let { _transactions.removeSource(it) }

        _loading.postValue(true)
        rawTransactions = when (mode) {
            is TransactionListMode.PresetIds -> presetTransactions(mode.ids)

            is TransactionListMode.LeftToSpend -> LeftToSpendTransactionPagesLiveData(
                appExecutors,
                transactionService,
                mode.period
            )

            is TransactionListMode.All -> AllTransactionPagesLiveData(
                appExecutors,
                transactionService,
                transactionUpdateEventBus
            )

            is TransactionListMode.Category -> CategoryTransactionPagesLiveData(
                mode.categoryId,
                appExecutors,
                transactionService,
                transactionUpdateEventBus,
                mode.period
            )

            is TransactionListMode.Account -> transactionRepository.forAccountId(mode.accountId)

            is TransactionListMode.Invalid -> return //No correct values passed
        }

        rawTransactions?.let {
            _transactions.apply { addSource(it) { list -> value = list } }
        }

        loadMoreItems()
    }


    val hasTransactions = transactionItems.map { it.transactions.isNotEmpty() }

    fun loadMoreItems() = rawTransactions?.loadMoreItems()

    override fun onCleared() {
        rawTransactions?.dispose()
    }

    data class TransactionItems(val transactions: List<ListItem.TransactionItem>)
}
