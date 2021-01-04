package se.tink.android.repository.budget

import androidx.lifecycle.MediatorLiveData
import com.tink.model.budget.BudgetTransaction
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

    private val updateListenerJob = transactionUpdateEventBus.subscribe { _ ->
        liveData.update()
    }

    init {
        addSource(liveData) { value = it }
    }

    fun dispose() = updateListenerJob.cancel()
}
