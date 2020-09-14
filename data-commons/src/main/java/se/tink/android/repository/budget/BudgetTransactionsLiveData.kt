package se.tink.android.repository.budget

import androidx.lifecycle.MediatorLiveData
import com.tink.model.budget.BudgetTransaction
import com.tink.model.transaction.Transaction
import com.tink.service.budget.BudgetService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.threeten.bp.Instant
import se.tink.android.livedata.AutoFetchLiveData
import se.tink.android.repository.transaction.TransactionUpdateEventBus

@ExperimentalCoroutinesApi
class BudgetTransactionsLiveData(
    private val budgetService: BudgetService,
    private val budgetId: String,
    private val start: Instant,
    private val end: Instant,
    transactionUpdateEventBus: TransactionUpdateEventBus
) : MediatorLiveData<List<BudgetTransaction>>() {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val liveData: AutoFetchLiveData<List<BudgetTransaction>> =
        AutoFetchLiveData {
            scope.launch {
                val transactions = try {
                    budgetService.listTransactionsForBudget(budgetId, start, end)
                } catch (t: Throwable) {
                    emptyList<BudgetTransaction>()
                }
                it.postValue(transactions)
            }
        }

    private val updateListenerJob = transactionUpdateEventBus.subscribe { updatedTransaction ->
        updateWith(listOf(updatedTransaction))
    }

    init {
        addSource(liveData) { value = it }
    }

    fun dispose() = updateListenerJob.cancel()

    private fun updateWith(transactions: List<Transaction>) {
        val currentTransactions = value
        transactions
            .map {
                BudgetTransaction(
                    it.id,
                    it.accountId,
                    it.amount,
                    it.dispensableAmount,
                    it.categoryId,
                    it.description,
                    it.date
                )
            }
            .filter { currentTransactions?.contains(it) ?: false }
            .takeIf { it.isNotEmpty() }
            ?.asIterable()
            ?.let { updatedTransactions ->
                currentTransactions?.toMutableList()?.apply {
                    removeAll(updatedTransactions)
                    addAll(updatedTransactions)
                }.also {
                    postValue(requireNotNull(it))
                }
            }
    }
}
