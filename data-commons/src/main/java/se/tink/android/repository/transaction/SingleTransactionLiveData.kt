package se.tink.android.repository.transaction

import androidx.lifecycle.MutableLiveData
import se.tink.core.models.transaction.Transaction
import se.tink.repository.ChangeObserver
import se.tink.repository.MutationHandler
import se.tink.repository.TinkNetworkError
import se.tink.repository.service.StreamingService
import se.tink.repository.service.TransactionService

class SingleTransactionLiveData(
    private val transactionId: String,
    val streamingService: StreamingService,
    val transactionService: TransactionService
) : MutableLiveData<TransactionResult>() {

    private var fetchOnInit = true

    constructor(
        transaction: Transaction,
        streamingService: StreamingService,
        transactionService: TransactionService
    ) : this(transaction.id, streamingService, transactionService) {
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
    }

    init {
        if (fetchOnInit) {
            transactionService.getTransaction(transactionId, object : MutationHandler<Transaction> {
                override fun onNext(item: Transaction) = postValue(
                    TransactionReceived(
                        item
                    )
                )
                override fun onError(error: TinkNetworkError) = postValue(
                    TransactionError(
                        error
                    )
                )
                override fun onCompleted() {}
            })
        }

        transactionService.subscribe(changeObserver)
    }

    fun dispose() {
        streamingService.unsubscribe(changeObserver)
    }

}

sealed class TransactionResult
class TransactionReceived(val transaction: Transaction) : TransactionResult()
class TransactionError(val error: TinkNetworkError) : TransactionResult()
object TransactionDeleted : TransactionResult()
