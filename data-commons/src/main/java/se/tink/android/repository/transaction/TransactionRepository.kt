package se.tink.android.repository.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.tink.annotations.PfmScope
import com.tink.model.category.Category
import com.tink.model.time.Period
import com.tink.model.transaction.Transaction
import com.tink.service.transaction.TransactionService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import se.tink.android.AppExecutors
import se.tink.android.repository.TinkNetworkError
import javax.inject.Inject

@ExperimentalCoroutinesApi
@PfmScope
class TransactionRepository @Inject constructor(
    private val transactionService: TransactionService,
    private val appExecutors: AppExecutors,
    private val transactionUpdateEventBus: TransactionUpdateEventBus
) {

    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    suspend fun fromIdList(ids: List<String>): List<Transaction> =
        coroutineScope { ids.map { async { transactionService.getTransaction(it) } } }.awaitAll()


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
            emptyList<Transaction>()
        }
        emit(transactions)
    }

    fun forAccountId(accountId: String): TransactionPagesLiveData =
        AccountTransactionPagesLiveData(accountId, appExecutors, transactionService, transactionUpdateEventBus)


    fun fetchById(id: String): SingleTransactionLiveData =
        SingleTransactionLiveData(id, transactionService)

    fun subscribeForTransaction(transaction: Transaction): SingleTransactionLiveData =
        SingleTransactionLiveData(transaction, transactionService)

    fun fetchSimilarTransactions(transactionId: String): LiveData<List<Transaction>> =
        liveData {
            val transactions = try {
                transactionService.getSimilarTransactions(transactionId)
            } catch (error: Throwable) {
                emptyList<Transaction>()
            }
            emit(transactions)
        }

    fun categorizeTransactions(
        transactionIds: List<String>,
        newCategoryId: String,
        onError: (TinkNetworkError) -> Unit
    ) {
        scope.launch {
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
            scope.launch {
                try {
                    transactionUpdateEventBus.postUpdate(transactionService.getTransaction(id))
                } catch (error: Throwable) {
                    // Fail silently
                }
            }
        }
    }
}
