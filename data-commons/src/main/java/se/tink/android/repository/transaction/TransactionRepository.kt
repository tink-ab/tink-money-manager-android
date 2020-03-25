package se.tink.android.repository.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import com.tink.annotations.PfmScope
import com.tink.model.category.Category
import com.tink.model.time.Period
import com.tink.model.transaction.Transaction
import com.tink.service.handler.ResultHandler
import com.tink.service.transaction.TransactionService
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import se.tink.android.AppExecutors
import se.tink.android.extensions.toListChangeObserver
import se.tink.android.extensions.toResultHandler
import se.tink.android.livedata.ErrorOrValue
import se.tink.android.livedata.createChangeObserver
import se.tink.android.livedata.createResultHandler
import se.tink.android.livedata.map
import se.tink.repository.ExceptionTracker
import se.tink.repository.TinkNetworkError
import se.tink.repository.TinkNetworkErrorHandler
import javax.inject.Inject

@PfmScope
class TransactionRepository @Inject constructor(
    private val transactionService: TransactionService,
    private val appExecutors: AppExecutors,
    private val exceptionTracker: ExceptionTracker,
    private val errorHandler: TinkNetworkErrorHandler
) {

    fun fromIdList(ids: List<String>): Single<List<Transaction>> {
        return Observable.fromIterable(ids).flatMap { id ->
            PublishSubject.create<Transaction>().also {
                transactionService.getTransaction(id, it.toResultHandler())
            }.onErrorResumeNext { error: Throwable ->
                exceptionTracker.exceptionThrown(Exception(error))
                Observable.empty<Transaction>()
            }
        }.toList()
    }

    fun fromIdsAsLiveData(ids: List<String>): LiveData<List<Transaction>> {
        return LiveDataReactiveStreams.fromPublisher(fromIdList(ids).toFlowable())
    }

    fun allTransactionsForCategoryAndPeriod(
        category: Category,
        period: Period
    ): LiveData<List<Transaction>> = object : MutableLiveData<List<Transaction>>() {
        private val listener = createChangeObserver(appExecutors).toListChangeObserver()

        override fun onActive() = transactionService.listAllAndSubscribeForCategoryCodeAndPeriod(
            category.code,
            period,
            listener
        )

        override fun onInactive() = transactionService.unsubscribe(listener)
    }

    val tags = TransactionTagsLiveData(transactionService, appExecutors)


    fun forAccountId(accountId: String): TransactionPagesLiveData =
        AccountTransactionPagesLiveData(accountId, appExecutors, transactionService)


    fun fetchById(id: String): SingleTransactionLiveData =
        SingleTransactionLiveData(id, transactionService)

    fun subscribeForTransaction(transaction: Transaction): SingleTransactionLiveData =
        SingleTransactionLiveData(transaction, transactionService)

    fun fetchSimilarTransactions(transactionId: String): LiveData<List<Transaction>?> {

        val liveData = MutableLiveData<ErrorOrValue<List<Transaction>>>()

        transactionService.getSimilarTransactions(
            transactionId,
            liveData.createResultHandler()
        )

        return liveData.map {
            if (it.error != null) emptyList()
            else it.value
        }
    }

// TODO: Core setup
//
//    fun updateTransaction(transaction: Transaction, onError: (TinkNetworkError) -> Unit) {
//        transactionService.updateTransaction(
//            transaction,
//            ErrorUtils.withErrorHandler(
//                errorHandler,
//                object : SimpleMutationHandler<Transaction>() {
//                    override fun onError(error: TinkNetworkError) = onError(error)
//                })
//        )
//    }

    fun categorizeTransactions(
        transactionIds: List<String>,
        newCategoryCode: String,
        onError: (TinkNetworkError) -> Unit
    ) {
        transactionService.categorizeTransactions(
            transactionIds,
            newCategoryCode,
            ResultHandler(onSuccess = {}, onError = { onError(TinkNetworkError(it)) })
        )
    }
}
