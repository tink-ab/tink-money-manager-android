package se.tink.android.repository.transaction

import com.tink.annotations.PfmScope
import com.tink.model.transaction.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@PfmScope
class TransactionUpdateEventBus @Inject constructor() {

    private val transactionUpdates = ConflatedBroadcastChannel<Transaction>()

    fun subscribe(onTransactionUpdate: (Transaction) -> Unit): Job {
        val receiveChannel = transactionUpdates.openSubscription()

        return CoroutineScope(Dispatchers.Default).launch {
            while (!receiveChannel.isClosedForReceive) {
                val updatedTransaction = receiveChannel.receive()
                onTransactionUpdate(updatedTransaction)
            }
        }
    }

    fun postUpdate(updatedTransaction: Transaction) {
        CoroutineScope(Dispatchers.IO).launch {
            transactionUpdates.send(updatedTransaction)
        }
    }
}
