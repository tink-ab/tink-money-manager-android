package se.tink.android.repository.transaction

import com.tink.annotations.PfmScope
import com.tink.model.transaction.Transaction
import com.tink.service.util.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@PfmScope
class TransactionUpdateEventBus @Inject constructor(
    private val dispatcher: DispatcherProvider
) {

    private val transactionUpdates = ConflatedBroadcastChannel<Transaction>()

    fun subscribe(onTransactionUpdate: (Transaction) -> Unit): Job {
        val receiveChannel = transactionUpdates.openSubscription()

        return CoroutineScope(dispatcher.default()).launch {
            while (!receiveChannel.isClosedForReceive) {
                val updatedTransaction = receiveChannel.receive()
                onTransactionUpdate(updatedTransaction)
            }
        }
    }

    fun postUpdate(updatedTransaction: Transaction) {
        CoroutineScope(dispatcher.io()).launch {
            transactionUpdates.send(updatedTransaction)
        }
    }
}
