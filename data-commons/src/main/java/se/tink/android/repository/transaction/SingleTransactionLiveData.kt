package se.tink.android.repository.transaction

import androidx.lifecycle.MutableLiveData
import com.tink.model.transaction.Transaction
import com.tink.service.transaction.TransactionService
import com.tink.service.util.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import se.tink.android.repository.TinkNetworkError

@ExperimentalCoroutinesApi
class SingleTransactionLiveData(
    private val transactionId: String,
    val transactionService: TransactionService,
    val transactionUpdateEventBus: TransactionUpdateEventBus,
    private val dispatcher: DispatcherProvider,
) : MutableLiveData<TransactionResult>() {

    private var fetchOnInit = true

    constructor(
        transaction: Transaction,
        transactionService: TransactionService,
        transactionUpdateEventBus: TransactionUpdateEventBus,
        dispatcher: DispatcherProvider,
    ) : this(transaction.id, transactionService, transactionUpdateEventBus, dispatcher) {
        fetchOnInit = false
        postValue(TransactionReceived(transaction))
    }

    private val updateSubscription: Job

    init {
        if (fetchOnInit) {
            CoroutineScope(dispatcher.io()).launch {
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
