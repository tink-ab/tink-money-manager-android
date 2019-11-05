package com.tink.pfmsdk.overview.latesttransactions

import android.content.Context
import com.tink.pfmsdk.transaction.TransactionListMode
import com.tink.pfmsdk.transaction.TransactionListViewModel
import se.tink.android.AppExecutors
import se.tink.android.categories.CategoryRepository
import se.tink.android.di.application.ApplicationScoped
import se.tink.android.livedata.map
import se.tink.android.repository.transaction.TransactionRepository
import se.tink.commons.transactions.TransactionItemFactory
import se.tink.repository.ExceptionTracker
import se.tink.repository.service.TransactionService
import javax.inject.Inject

class LatestTransactionsViewModel @Inject constructor(
    transactionRepository: TransactionRepository,
    exceptionTracker: ExceptionTracker,
    categoryRepository: CategoryRepository,
    transactionService: TransactionService,
    appExecutors: AppExecutors,
    transactionItemFactory: TransactionItemFactory,
    @ApplicationScoped private val context: Context
) : TransactionListViewModel(
    transactionRepository,
    exceptionTracker,
    categoryRepository,
    transactionService,
    appExecutors,
    transactionItemFactory,
    context
) {

    init {
        setListMode(TransactionListMode.All)
    }

    val latestTransactions = transactionItems.map {
        it.transactions.take(3)
    }

}