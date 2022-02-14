package se.tink.android.repository.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.tink.annotations.PfmScope
import com.tink.model.category.Category
import com.tink.model.time.Period
import com.tink.model.transaction.Transaction
import com.tink.service.transaction.TransactionService
import com.tink.service.util.DispatcherProvider
import kotlinx.coroutines.*
import se.tink.android.AppExecutors
import se.tink.android.repository.TinkNetworkError
import javax.inject.Inject

@ExperimentalCoroutinesApi
@PfmScope
class TransactionRepository @Inject constructor(
    private val transactionService: TransactionService,
    private val appExecutors: AppExecutors,
    private val transactionUpdateEventBus: TransactionUpdateEventBus,
    private val dispatcher: DispatcherProvider
) {
    suspend fun fromIdList(ids: List<String>): List<Transaction> =
        withContext(CoroutineScope(dispatcher.io()).coroutineContext) {
            ids.map { async { transactionService.getTransaction(it) } }
        }.awaitAll()

    fun fromIdsAsLiveData(ids: List<String>): LiveData<List<Transaction>> {
        return liveData { emit(fromIdList(ids)) }
    }

    fun allTransactionsForCategoryAndPeriod(
        category: Category,
        period: Period
    ): LiveData<List<Transaction>> = liveData {
        val transactions = try {
            transactionService.fetchAllTransactions(period, category.id)
        } catch (error: Throwable) {
            emptyList()
        }
        emit(transactions)
    }

    fun forAccountId(accountId: String): TransactionPagesLiveData =
        AccountTransactionPagesLiveData(accountId, appExecutors, transactionService, transactionUpdateEventBus, dispatcher)


    fun fetchById(id: String): SingleTransactionLiveData =
        SingleTransactionLiveData(id, transactionService, transactionUpdateEventBus, dispatcher)

    fun fetchSimilarTransactions(transactionId: String): LiveData<List<Transaction>> =
        liveData {
            val transactions = try {
                transactionService.getSimilarTransactions(transactionId)
            } catch (error: Throwable) {
                emptyList()
            }
            emit(transactions)
        }

    fun categorizeTransactions(
        transactionIds: List<String>,
        newCategoryId: String,
        onError: (TinkNetworkError) -> Unit
    ) {
        CoroutineScope(dispatcher.io()).launch {
            try {
                transactionService.categorizeTransactions(transactionIds, newCategoryId)
                fetchAndPostUpdates(transactionIds)
            } catch (error: Throwable) {
                onError(TinkNetworkError(error))
            }
        }
    }

    private fun fetchAndPostUpdates(transactionIds: List<String>) {
        for (id in transactionIds) {
            CoroutineScope(dispatcher.io()).launch {
                try {
                    transactionUpdateEventBus.postUpdate(transactionService.getTransaction(id))
                } catch (error: Throwable) {
                    // Fail silently
                }
            }
        }
    }
}
