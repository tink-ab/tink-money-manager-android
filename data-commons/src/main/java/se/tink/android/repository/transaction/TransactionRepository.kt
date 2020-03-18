package se.tink.android.repository.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import com.tink.annotations.PfmScope
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import se.tink.android.AppExecutors
import se.tink.android.extensions.toMutationHandler
import se.tink.android.livedata.ErrorOrValue
import se.tink.android.livedata.createChangeObserver
import se.tink.android.livedata.createMutationHandler
import se.tink.android.livedata.map
import com.tink.model.category.Category
import se.tink.core.models.misc.Period
import se.tink.core.models.transaction.Transaction
import se.tink.repository.ErrorUtils
import se.tink.repository.ExceptionTracker
import se.tink.repository.SimpleMutationHandler
import se.tink.repository.TinkNetworkError
import se.tink.repository.TinkNetworkErrorHandler
import se.tink.repository.service.StreamingService
import se.tink.repository.service.TransactionService
import javax.inject.Inject

@PfmScope
class TransactionRepository @Inject constructor(
    private val transactionService: TransactionService,
    private val streamingService: StreamingService,
    private val appExecutors: AppExecutors,
    private val exceptionTracker: ExceptionTracker,
    private val errorHandler: TinkNetworkErrorHandler
) {

    fun fromIdList(ids: List<String>): Single<List<Transaction>> {
        return Observable.fromIterable(ids).flatMap { id ->
            PublishSubject.create<Transaction>().also {
                transactionService.getTransaction(id, it.toMutationHandler())
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
        private val listener = createChangeObserver(appExecutors)

        override fun onActive() = transactionService.listAllAndSubscribeForCategoryCodeAndPeriod(
            category.code,
            period,
            listener
        )

        override fun onInactive() = transactionService.unsubscribe(listener)
    }

    val tags = TransactionTagsLiveData(streamingService, transactionService, appExecutors)


    fun forAccountId(accountId: String): TransactionPagesLiveData =
        AccountTransactionPagesLiveData(accountId, appExecutors, transactionService)


    fun fetchById(id: String): SingleTransactionLiveData =
        SingleTransactionLiveData(id, streamingService, transactionService)

    fun subscribeForTransaction(transaction: Transaction): SingleTransactionLiveData =
        SingleTransactionLiveData(transaction, streamingService, transactionService)

    fun fetchSimilarTransactions(transactionId: String): LiveData<List<Transaction>?> {

        val liveData = MutableLiveData<ErrorOrValue<List<Transaction>>>()

        transactionService.getSimilarTransactions(
            transactionId,
            liveData.createMutationHandler()
        )

        return liveData.map {
            if (it.error != null) emptyList()
            else it.value
        }
    }

    fun updateTransaction(transaction: Transaction, onError: (TinkNetworkError) -> Unit) {
        transactionService.updateTransaction(
            transaction,
            ErrorUtils.withErrorHandler(
                errorHandler,
                object : SimpleMutationHandler<Transaction>() {
                    override fun onError(error: TinkNetworkError) = onError(error)
                })
        )
    }

    fun categorizeTransactions(
        transactionIds: List<String>,
        newCategoryCode: String,
        onError: (TinkNetworkError) -> Unit
    ) {
        transactionService.categorizeTransactions(
            transactionIds,
            newCategoryCode,
            object : SimpleMutationHandler<List<Transaction>>() {
                override fun onError(error: TinkNetworkError) = onError(error)
            })
    }
}
