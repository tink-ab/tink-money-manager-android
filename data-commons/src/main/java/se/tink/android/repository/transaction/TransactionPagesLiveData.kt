package se.tink.android.repository.transaction

import androidx.lifecycle.MutableLiveData
import se.tink.android.AppExecutors
import se.tink.android.livedata.createChangeObserver
import com.tink.model.time.Period
import se.tink.core.models.transaction.SearchResultMetadata
import com.tink.model.transaction.Transaction
import com.tink.service.handler.ResultHandler
import com.tink.service.transaction.Pageable
import com.tink.service.transaction.TransactionService
import se.tink.android.extensions.toListChangeObserver

abstract class TransactionPagesLiveData : MutableLiveData<List<Transaction>>() {
    abstract fun dispose()
    abstract fun loadMoreItems()
}

abstract class AbstractTransactionPagesLiveData(
    val appExecutors: AppExecutors,
    val transactionService: TransactionService
) : TransactionPagesLiveData() {

    private var isLoading: Boolean = false

    protected abstract val pageable: Pageable
    protected val listener = createChangeObserver(appExecutors).toListChangeObserver()


    override fun dispose() = transactionService.unsubscribe(listener)

    override fun loadMoreItems() {
        if (!isLoading && pageable.hasMore()) {
            isLoading = true
            pageable.next(ResultHandler({ isLoading = false }, { isLoading = false }))
        }
    }
}

class AccountTransactionPagesLiveData(
    val accountId: String,
    appExecutors: AppExecutors,
    transactionService: TransactionService
) : AbstractTransactionPagesLiveData(appExecutors, transactionService) {

    override val pageable: Pageable =
        transactionService.listAndSubscribeForAccountId(accountId, listener)

}

// TODO: Core setup - do we need it?
//class SearchTransactionPagesLiveData(
//    val query: String,
//    appExecutors: AppExecutors,
//    transactionService: TransactionService,
//    metadataMutationHandler: MutationHandler<SearchResultMetadata>
//) : AbstractTransactionPagesLiveData(appExecutors, transactionService) {
//
//    override val pageable: Pageable =
//        transactionService.search(query, listener, metadataMutationHandler)
//
//}

class LeftToSpendTransactionPagesLiveData(
    appExecutors: AppExecutors,
    transactionService: TransactionService,
    val period: Period?
) : AbstractTransactionPagesLiveData(appExecutors, transactionService) {

    override val pageable: Pageable =
        if (period != null) {
            transactionService.listAndSubscribeForLeftToSpendAndPeriod(period, listener)
        } else {
            transactionService.listAndSubscribeForLatestTransactions(false, listener)
        }

}

class AllTransactionPagesLiveData(
    appExecutors: AppExecutors,
    transactionService: TransactionService
) : AbstractTransactionPagesLiveData(appExecutors, transactionService) {

    override val pageable: Pageable =
        transactionService.listAndSubscribeForLatestTransactions(true, listener)
}

class CategoryTransactionPagesLiveData(
    val categoryCode: String,
    appExecutors: AppExecutors,
    transactionService: TransactionService,
    val period: Period?
) : AbstractTransactionPagesLiveData(appExecutors, transactionService) {

    override val pageable: Pageable =
        if (period != null) {
            transactionService.listAndSubscribeForCategoryCodeAndPeriod(
                categoryCode,
                period,
                listener
            )
        } else {
            transactionService.listAndSubscribeForCategoryCode(categoryCode, listener)
        }

}

