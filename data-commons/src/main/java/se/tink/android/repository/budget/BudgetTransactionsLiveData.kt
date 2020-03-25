package se.tink.android.repository.budget

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.tink.model.budget.BudgetTransaction
import com.tink.model.transaction.Transaction
import com.tink.service.budget.BudgetService
import com.tink.service.transaction.TransactionService
import org.threeten.bp.Instant
import se.tink.android.extensions.toListChangeObserver
import se.tink.android.livedata.AutoFetchLiveData
import se.tink.android.livedata.ErrorOrValue
import se.tink.android.livedata.createResultHandler
import se.tink.repository.SimpleChangeObserver


class BudgetTransactionsLiveData(
    private val transactionService: TransactionService,
    private val budgetService: BudgetService,
    private val budgetId: String,
    private val start: Instant,
    private val end: Instant
) : MediatorLiveData<ErrorOrValue<List<BudgetTransaction>>>() {

    private val liveData = AutoFetchLiveData<ErrorOrValue<List<BudgetTransaction>>> {
        budgetService.listTransactionsForBudget(
            budgetId, start, end,
            it.createResultHandler()
        )
    }

    private var subscribed = false

    private val changeObserver = object : SimpleChangeObserver<Transaction>() {
        override fun onUpdate(items: List<Transaction>?) = applyUpdate(items)
    }.toListChangeObserver()

    init {
        addSource(liveData) { value = it }
    }

    override fun onActive() {
        super.onActive()
        if (!subscribed) {
            transactionService.subscribe(changeObserver)
            subscribed = true
        }
    }

    override fun removeObserver(observer: Observer<in ErrorOrValue<List<BudgetTransaction>>>) {
        super.removeObserver(observer)
        unsubscribeIfNecessary()
    }

    override fun removeObservers(owner: LifecycleOwner) {
        super.removeObservers(owner)
        unsubscribeIfNecessary()
    }

    private fun unsubscribeIfNecessary() {
        if (subscribed && !hasObservers()) {
            transactionService.unsubscribe(changeObserver)
            subscribed = false
        }
    }

    fun applyUpdate(transactions: List<Transaction>?) {
        val currentTransactions = value
        transactions
            ?.map {
                BudgetTransaction(
                    it.id,
                    it.accountId,
                    it.amount,
                    it.dispensableAmount,
                    it.categoryCode,
                    it.description,
                    it.date
                )
            }
            ?.filter { currentTransactions?.value?.contains(it) ?: false }
            ?.takeIf { it.isNotEmpty() }
            ?.let { updatedTransactions ->
                currentTransactions?.value?.toMutableList()?.apply {
                    removeAll(updatedTransactions)
                    addAll(updatedTransactions)
                }.also {
                    postValue(ErrorOrValue(requireNotNull(it)))
                }
            }
    }
}
