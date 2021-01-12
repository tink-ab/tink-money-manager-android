package se.tink.android.repository.transaction

import androidx.lifecycle.MutableLiveData
import com.tink.model.transaction.Transaction
import com.tink.service.transaction.TransactionService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import se.tink.android.repository.TinkNetworkError

@ExperimentalCoroutinesApi
class SingleTransactionLiveData(
    private val transactionId: String,
    val transactionService: TransactionService,
    val transactionUpdateEventBus: TransactionUpdateEventBus
) : MutableLiveData<TransactionResult>() {

    private var fetchOnInit = true

    constructor(
        transaction: Transaction,
        transactionService: TransactionService,
        transactionUpdateEventBus: TransactionUpdateEventBus
    ) : this(transaction.id, transactionService, transactionUpdateEventBus) {
        fetchOnInit = false
        postValue(TransactionReceived(transaction))
    }

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val updateSubscription: Job

    init {
        if (fetchOnInit) {
            scope.launch {
                try {
                    val transaction = transactionService.getTransaction(transactionId)
                    postValue(TransactionReceived(transaction))
                } catch (error: Throwable) {
                    postValue(TransactionError(TinkNetworkError(error)))
                }
            }
        }
        updateSubscription = transactionUpdateEventBus.subscribe {
            if (it.id == transactionId) postValue(TransactionReceived(it))
        }
    }

    fun dispose() {
        updateSubscription.cancel()
    }
}

sealed class TransactionResult
class TransactionReceived(val transaction: Transaction) : TransactionResult()
class TransactionError(val error: TinkNetworkError) : TransactionResult()
object TransactionDeleted : TransactionResult()
