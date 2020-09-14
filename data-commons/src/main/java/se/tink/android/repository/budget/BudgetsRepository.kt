package se.tink.android.repository.budget

import androidx.lifecycle.LiveData
import com.tink.annotations.PfmScope
import com.tink.model.budget.BudgetCreateOrUpdateDescriptor
import com.tink.model.budget.BudgetSummary
import com.tink.service.budget.BudgetService
import org.threeten.bp.Instant
import se.tink.android.livedata.AutoFetchLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import se.tink.android.repository.service.DataRefreshHandler
import se.tink.android.repository.service.Refreshable
import se.tink.android.repository.transaction.TransactionUpdateEventBus
import javax.inject.Inject

@ExperimentalCoroutinesApi
@PfmScope
class BudgetsRepository @Inject constructor(
    private val budgetService: BudgetService,
    private val transactionUpdateEventBus: TransactionUpdateEventBus,
    dataRefreshHandler: DataRefreshHandler
) : Refreshable {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _budgets: AutoFetchLiveData<List<BudgetSummary>> =
        AutoFetchLiveData {
            scope.launch {
                val budgetSummaries = try {
                    budgetService.listBudgets()
                } catch (t: Throwable) {
                    emptyList<BudgetSummary>()
                }
                it.postValue(budgetSummaries)
            }
        }

    init {
        dataRefreshHandler.registerRefreshable(this)
    }

    override fun refresh() {
        _budgets.update()
    }

    val budgets: LiveData<List<BudgetSummary>> = _budgets

    fun createOrUpdateBudget(descriptor: BudgetCreateOrUpdateDescriptor) {
        scope.launch {
            if (descriptor.id == null) {
                budgetService.createBudget(descriptor)
            } else {
                budgetService.updateBudget(descriptor)
            }
            _budgets.update()
        }
    }

    fun deleteBudget(id: String) {
        scope.launch {
            budgetService.deleteBudget(id)
            _budgets.update()
        }
    }

    fun transactions(
        budgetId: String,
        start: Instant,
        end: Instant
    ) =
        BudgetTransactionsLiveData(budgetService, budgetId, start, end, transactionUpdateEventBus)

    fun budgetPeriodDetails(
        budgetId: String,
        start: Instant,
        end: Instant
    ) {
        scope.launch {
            budgetService.budgetPeriodDetails(budgetId, start, end)
        }
    }
}

