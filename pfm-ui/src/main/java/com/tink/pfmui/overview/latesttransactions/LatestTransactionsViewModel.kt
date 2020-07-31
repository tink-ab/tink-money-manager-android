package com.tink.pfmui.overview.latesttransactions

import com.tink.pfmui.transaction.TransactionListMode
import com.tink.pfmui.transaction.TransactionListViewModel
import se.tink.android.AppExecutors
import se.tink.android.categories.CategoryRepository
import se.tink.android.livedata.map
import se.tink.android.repository.transaction.TransactionRepository
import se.tink.commons.transactions.ListItem
import se.tink.commons.transactions.TransactionItemFactory
import se.tink.android.repository.service.DataRefreshHandler
import se.tink.android.repository.service.Refreshable
import com.tink.service.transaction.TransactionService
import javax.inject.Inject

internal class LatestTransactionsViewModel @Inject constructor(
    transactionRepository: TransactionRepository,
    categoryRepository: CategoryRepository,
    transactionService: TransactionService,
    private val appExecutors: AppExecutors,
    transactionItemFactory: TransactionItemFactory,
    private val dataRefreshHandler: DataRefreshHandler
) : TransactionListViewModel(
    transactionRepository,
    categoryRepository,
    transactionService,
    appExecutors,
    transactionItemFactory
), Refreshable {

    init {
        setListMode(TransactionListMode.All)
        dataRefreshHandler.registerRefreshable(this)
    }

    override fun refresh() =
        appExecutors.mainThreadExecutor.execute { setListMode(TransactionListMode.All) }


    val latestTransactions = transactionItems.map { items ->
        items
            .transactions
            .sortedWith(compareByDescending<ListItem.TransactionItem> { it.date }.thenBy { it.id })
            .take(3)
    }

    override fun onCleared() {
        dataRefreshHandler.unregisterRefreshable(this)
        super.onCleared()
    }
}
