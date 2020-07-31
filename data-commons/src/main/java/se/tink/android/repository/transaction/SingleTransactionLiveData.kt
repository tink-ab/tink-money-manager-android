package se.tink.android.repository.transaction

import androidx.lifecycle.MutableLiveData
import com.tink.model.transaction.Transaction
import com.tink.service.transaction.TransactionService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import se.tink.android.repository.TinkNetworkError

class SingleTransactionLiveData(
    private val transactionId: String,
    val transactionService: TransactionService
) : MutableLiveData<TransactionResult>() {

    private var fetchOnInit = true

    constructor(
        transaction: Transaction,
        transactionService: TransactionService
    ) : this(transaction.id, transactionService) {
        fetchOnInit = false
        postValue(TransactionReceived(transaction))
    }

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

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
        // TODO: How to do subscription
//        transactionService.subscribe(changeObserver)
    }

    fun dispose() {
//        transactionService.unsubscribe(changeObserver)
    }

}

sealed class TransactionResult
class TransactionReceived(val transaction: Transaction) : TransactionResult()
class TransactionError(val error: TinkNetworkError) : TransactionResult()
object TransactionDeleted : TransactionResult()
