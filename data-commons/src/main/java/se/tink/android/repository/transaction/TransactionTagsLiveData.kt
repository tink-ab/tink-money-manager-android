package se.tink.android.repository.transaction

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import se.tink.android.AppExecutors
import se.tink.android.livedata.AutoFetchLiveData
import se.tink.android.livedata.createChangeObserver
import se.tink.core.models.transaction.Tag
import se.tink.core.models.transaction.Transaction
import se.tink.repository.SimpleChangeObserver
import se.tink.repository.service.StreamingService
import se.tink.repository.service.TransactionService

class TransactionTagsLiveData(
    private val streamingService: StreamingService,
    private val transactionService: TransactionService,
    private val appExecutors: AppExecutors
) : MediatorLiveData<List<Tag>>() {

    private val liveData = AutoFetchLiveData<List<Transaction>> {
        transactionService.listAndSubscribeForLatestTransactions(
            false,
            200,
            it.createChangeObserver(appExecutors)
        )
    }

    private var subscribed = false

    private val changeObserver = object : SimpleChangeObserver<Transaction>() {
        override fun onUpdate(items: List<Transaction>?) = updateTags(items)
    }

    init {
        addSource(liveData) { updateTags(liveData.value) }
    }

    override fun onActive() {
        super.onActive()
        if (!subscribed) {
            streamingService.subscribeForTransactions(changeObserver)
            subscribed = true
        }
    }

    override fun removeObserver(observer: Observer<in List<Tag>>) {
        super.removeObserver(observer)
        unsubscribeIfNecessary()
    }

    override fun removeObservers(owner: LifecycleOwner) {
        super.removeObservers(owner)
        unsubscribeIfNecessary()
    }

    private fun unsubscribeIfNecessary() {
        if (subscribed && !hasObservers()) {
            streamingService.unsubscribe(changeObserver)
            subscribed = false
        }
    }

    fun updateTags(transactions: List<Transaction>?) {
        val currentTags = value
        transactions
            ?.flatMap { it.tags }
            ?.distinctBy { it.name }
            ?.let { tags ->
                currentTags?.toMutableList()?.apply {
                    removeAll(tags)
                    addAll(tags)
                    postValue(this)
                }
                    ?: postValue(tags)
            }
    }
}