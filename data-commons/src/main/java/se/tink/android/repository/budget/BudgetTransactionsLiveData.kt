package se.tink.android.repository.budget

import androidx.lifecycle.MediatorLiveData
import com.tink.model.budget.BudgetTransaction
import com.tink.service.budget.BudgetService
import com.tink.service.util.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import se.tink.android.livedata.AutoFetchLiveData
import se.tink.android.livedata.ErrorOrValue
import se.tink.android.repository.TinkNetworkError
import se.tink.android.repository.transaction.TransactionUpdateEventBus
import java.time.Instant

@ExperimentalCoroutinesApi
class BudgetTransactionsLiveData(
    private val budgetService: BudgetService,
    private val budgetId: String,
    private val start: Instant,
    private val end: Instant,
    private val dispatcher: DispatcherProvider,
    transactionUpdateEventBus: TransactionUpdateEventBus
) : MediatorLiveData<ErrorOrValue<List<BudgetTransaction>>>() {

    private val liveData: AutoFetchLiveData<ErrorOrValue<List<BudgetTransaction>>> =
        AutoFetchLiveData {
            CoroutineScope(dispatcher.io()).launch {
                try {
                    it.postValue(
                        ErrorOrValue(budgetService.listTransactionsForBudget(budgetId, start, end))
                    )
                } catch (t: Throwable) {
                    it.postValue(ErrorOrValue(TinkNetworkError(t)))
                }
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
