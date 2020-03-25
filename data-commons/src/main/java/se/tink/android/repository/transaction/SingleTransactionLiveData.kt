package se.tink.android.repository.transaction

import androidx.lifecycle.MutableLiveData
import com.tink.model.transaction.Transaction
import com.tink.service.handler.ResultHandler
import com.tink.service.transaction.TransactionService
import se.tink.android.extensions.toListChangeObserver
import se.tink.repository.ChangeObserver
import se.tink.repository.TinkNetworkError

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

    private val changeObserver = object : ChangeObserver<Transaction> {

        override fun onRead(items: List<Transaction>) {
            items.find { it.id == transactionId }?.let { postValue(
                TransactionReceived(
                    it
                )
            ) }
        }

        override fun onCreate(items: List<Transaction>) {
            items.find { it.id == transactionId }?.let { postValue(
                TransactionReceived(
                    it
                )
            ) }
        }

        override fun onUpdate(items: List<Transaction>) {
            items.find { it.id == transactionId }?.let { postValue(
                TransactionReceived(
                    it
                )
            ) }
        }

        override fun onDelete(items: List<Transaction>) {
            if (items.any { it.id == transactionId }) postValue(TransactionDeleted)
        }
    }.toListChangeObserver()

    init {
        if (fetchOnInit) {
            transactionService.getTransaction(transactionId,
                ResultHandler(
                    onSuccess = { postValue(TransactionReceived(it)) },
                    onError = { postValue(TransactionError(TinkNetworkError(it))) }
                )
            )
        }

        transactionService.subscribe(changeObserver)
    }

    fun dispose() {
        transactionService.unsubscribe(changeObserver)
    }

}

sealed class TransactionResult
class TransactionReceived(val transaction: Transaction) : TransactionResult()
class TransactionError(val error: TinkNetworkError) : TransactionResult()
object TransactionDeleted : TransactionResult()
