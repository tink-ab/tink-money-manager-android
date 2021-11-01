package se.tink.android.repository.transaction

import androidx.lifecycle.MutableLiveData
import se.tink.android.AppExecutors
import se.tink.android.livedata.createChangeObserver
import com.tink.model.time.Period
import com.tink.model.transaction.Transaction
import com.tink.service.handler.ResultHandler
import com.tink.service.transaction.Pageable
import com.tink.service.transaction.TransactionService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

abstract class TransactionPagesLiveData : MutableLiveData<List<Transaction>>() {
    abstract fun dispose()
    abstract fun loadMoreItems()
}

class TransactionsPageable(
    private val transactionService: TransactionService,
    transactionUpdateEventBus: TransactionUpdateEventBus,
    private val liveData: MutableLiveData<List<Transaction>>,
    private val accountId: String? = null,
    private val categoryId: String? = null,
    private val period: Period? = null
) : Pageable {

    private var currentOffset = 0

    private val currentTransactions = mutableListOf<Transaction>()

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private var hasMore: Boolean = true

    private val createdTime = System.currentTimeMillis()

    private val updateListenerJob = transactionUpdateEventBus.subscribe { updatedTransaction ->
        // Ignore stale data at creation time in transaction update event bus, we are only interested in updates
        // TODO: Should be fixed to a better more stable solution long term
        if (System.currentTimeMillis() - createdTime > 100) {
            updateWith(listOf(updatedTransaction))
        }
    }

    override fun hasMore(): Boolean {
        return hasMore
    }

    override fun next(resultHandler: ResultHandler<Unit>) {
        scope.launch {
            try {
                val transactions = transactionService.listTransactions(
                    accountId, categoryId, period, currentOffset
                )
                currentOffset += transactions.size

                updateWith(transactions)

                if (transactions.isEmpty()) hasMore = false

                resultHandler.onSuccess(Unit)
            } catch (error: Throwable) {
                resultHandler.onError(error)
            }
        }
    }

    private fun updateWith(transactions: List<Transaction>) {
        currentTransactions.removeAll { transactionToRemove ->
            transactions.map { it.id }.contains(transactionToRemove.id)
        }
        // TODO: Should also filter by categoryId and period
        currentTransactions += transactions.filter { accountId?.equals(it.accountId) ?: true }
        liveData.postValue(currentTransactions)
    }

    fun dispose() = updateListenerJob.cancel()
}

abstract class AbstractTransactionPagesLiveData(
    val appExecutors: AppExecutors,
    val transactionService: TransactionService
) : TransactionPagesLiveData() {

    private var isLoading: Boolean = false

    protected abstract val pageable: TransactionsPageable
    protected val listener = createChangeObserver(appExecutors)

    override fun dispose() {
        pageable.dispose()
    }

    override fun loadMoreItems() {
        if (!isLoading && pageable.hasMore()) {
            isLoading = true
            pageable.next(ResultHandler({ isLoading = false }, { isLoading = false }))
        }
    }
}

class AccountTransactionPagesLiveData(
    accountId: String,
    appExecutors: AppExecutors,
    transactionService: TransactionService,
    transactionUpdateEventBus: TransactionUpdateEventBus
) : AbstractTransactionPagesLiveData(appExecutors, transactionService) {
    override val pageable: TransactionsPageable =
        TransactionsPageable(
            transactionService,
            transactionUpdateEventBus,
            this,
            accountId
        )
}

class LeftToSpendTransactionPagesLiveData(
    appExecutors: AppExecutors,
    transactionService: TransactionService,
    val period: Period?
) : AbstractTransactionPagesLiveData(appExecutors, transactionService) {

    override val pageable: TransactionsPageable = TODO()
}

class AllTransactionPagesLiveData (
    appExecutors: AppExecutors,
    transactionService: TransactionService,
    transactionUpdateEventBus: TransactionUpdateEventBus
) : AbstractTransactionPagesLiveData(appExecutors, transactionService) {
    override val pageable: TransactionsPageable =
        TransactionsPageable(transactionService, transactionUpdateEventBus, this)
}

class CategoryTransactionPagesLiveData(
    categoryId: String,
    appExecutors: AppExecutors,
    transactionService: TransactionService,
    transactionUpdateEventBus: TransactionUpdateEventBus,
    val period: Period?
) : AbstractTransactionPagesLiveData(appExecutors, transactionService) {

    override val pageable: TransactionsPageable =
        TransactionsPageable(
            transactionService = transactionService,
            transactionUpdateEventBus = transactionUpdateEventBus,
            liveData = this,
            categoryId = categoryId,
            period = period
        )
}
