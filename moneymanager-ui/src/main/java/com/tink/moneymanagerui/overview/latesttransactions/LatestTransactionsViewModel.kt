package com.tink.moneymanagerui.overview.latesttransactions

import com.tink.moneymanagerui.transaction.TransactionListMode
import com.tink.moneymanagerui.transaction.TransactionListViewModel
import com.tink.service.transaction.TransactionService
import com.tink.service.util.DispatcherProvider
import se.tink.android.AppExecutors
import se.tink.android.categories.CategoryRepository
import se.tink.android.livedata.map
import se.tink.android.repository.service.DataRefreshHandler
import se.tink.android.repository.service.Refreshable
import se.tink.android.repository.transaction.TransactionRepository
import se.tink.android.repository.transaction.TransactionUpdateEventBus
import se.tink.commons.transactions.ListItem
import se.tink.commons.transactions.TransactionItemFactory
import javax.inject.Inject

internal class LatestTransactionsViewModel @Inject constructor(
    transactionRepository: TransactionRepository,
    categoryRepository: CategoryRepository,
    transactionService: TransactionService,
    private val appExecutors: AppExecutors,
    transactionItemFactory: TransactionItemFactory,
    transactionUpdateEventBus: TransactionUpdateEventBus,
    dispatcher: DispatcherProvider,
    private val dataRefreshHandler: DataRefreshHandler
) : TransactionListViewModel(
    transactionRepository,
    categoryRepository,
    transactionService,
    appExecutors,
    transactionItemFactory,
    transactionUpdateEventBus,
    dispatcher
), Refreshable {

    init {
        setListMode(TransactionListMode.All)
        dataRefreshHandler.registerRefreshable(this)
    }

    val categoriesState = categoryRepository.categoriesState

    override fun refresh() =
        appExecutors.mainThreadExecutor.execute { setListMode(TransactionListMode.All) }


    val latestTransactions = transactionItems.map { items ->
        items
            .transactions.map { item ->
                transactionItemFactory.latestTransactionItemFromTransactionItem(item)
            }
            .sortedWith(compareByDescending<ListItem.TransactionItem> { it.date }
                .thenBy { it.id }
            )
            .take(3)
    }

    override fun onCleared() {
        dataRefreshHandler.unregisterRefreshable(this)
        super.onCleared()
    }
}
