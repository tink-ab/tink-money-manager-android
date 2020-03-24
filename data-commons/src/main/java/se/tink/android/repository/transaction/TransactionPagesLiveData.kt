package se.tink.android.repository.transaction

import androidx.lifecycle.MutableLiveData
import se.tink.android.AppExecutors
import se.tink.android.livedata.createChangeObserver
import com.tink.model.time.Period
import se.tink.core.models.transaction.SearchResultMetadata
import se.tink.core.models.transaction.Transaction
import se.tink.repository.MutationHandler
import se.tink.repository.PagingResult
import se.tink.repository.TinkNetworkError
import se.tink.repository.service.Pageable
import se.tink.repository.service.PagingHandler
import se.tink.repository.service.TransactionService

abstract class TransactionPagesLiveData : MutableLiveData<List<Transaction>>() {
    abstract fun dispose()
    abstract fun loadMoreItems()
}

abstract class AbstractTransactionPagesLiveData(
    val appExecutors: AppExecutors,
    val transactionService: TransactionService
) : TransactionPagesLiveData() {

    private var isLoading: Boolean = false

    protected abstract val pageable: Pageable<Transaction>
    protected val listener = createChangeObserver(appExecutors)


    override fun dispose() = transactionService.unsubscribe(listener)

    override fun loadMoreItems() {
        if (!isLoading && pageable.hasMore) {
            isLoading = true
            pageable.next(
                object : PagingHandler {
                    override fun onError(error: TinkNetworkError) {
                        isLoading = false
                    }

                    override fun onCompleted(result: PagingResult) {
                        isLoading = false
                    }
                })
        }
    }
}

class AccountTransactionPagesLiveData(
    val accountId: String,
    appExecutors: AppExecutors,
    transactionService: TransactionService
) : AbstractTransactionPagesLiveData(appExecutors, transactionService) {

    override val pageable: Pageable<Transaction> =
        transactionService.listAndSubscribeForAccountId(accountId, listener)

}

class SearchTransactionPagesLiveData(
    val query: String,
    appExecutors: AppExecutors,
    transactionService: TransactionService,
    metadataMutationHandler: MutationHandler<SearchResultMetadata>
) : AbstractTransactionPagesLiveData(appExecutors, transactionService) {

    override val pageable: Pageable<Transaction> =
        transactionService.search(query, listener, metadataMutationHandler)

}

class LeftToSpendTransactionPagesLiveData(
    appExecutors: AppExecutors,
    transactionService: TransactionService,
    val period: Period?
) : AbstractTransactionPagesLiveData(appExecutors, transactionService) {

    override val pageable: Pageable<Transaction> =
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

    override val pageable: Pageable<Transaction> =
        transactionService.listAndSubscribeForLatestTransactions(true, listener)
}

class CategoryTransactionPagesLiveData(
    val categoryCode: String,
    appExecutors: AppExecutors,
    transactionService: TransactionService,
    val period: Period?
) : AbstractTransactionPagesLiveData(appExecutors, transactionService) {

    override val pageable: Pageable<Transaction> =
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

