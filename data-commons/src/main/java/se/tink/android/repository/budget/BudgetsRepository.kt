package se.tink.android.repository.budget

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tink.annotations.PfmScope
import com.tink.model.budget.BudgetCreateOrUpdateDescriptor
import com.tink.model.budget.BudgetPeriod
import com.tink.model.budget.BudgetSpecification
import com.tink.model.budget.BudgetSummary
import com.tink.service.budget.BudgetService
import com.tink.service.handler.ResultHandler
import com.tink.service.network.ErrorState
import com.tink.service.network.LoadingState
import com.tink.service.network.ResponseState
import com.tink.service.network.SuccessState
import com.tink.service.util.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import org.threeten.bp.Instant
import se.tink.android.extensions.launchForResult
import se.tink.android.livedata.AutoFetchLiveData
import se.tink.android.repository.service.DataRefreshHandler
import se.tink.android.repository.service.Refreshable
import se.tink.android.repository.transaction.TransactionUpdateEventBus
import javax.inject.Inject

@ExperimentalCoroutinesApi
@PfmScope
class BudgetsRepository @Inject constructor(
    private val budgetService: BudgetService,
    private val transactionUpdateEventBus: TransactionUpdateEventBus,
    private val dispatcher: DispatcherProvider,
    dataRefreshHandler: DataRefreshHandler
) : Refreshable {

    init {
        dataRefreshHandler.registerRefreshable(this)
    }

    private val _budgets: AutoFetchLiveData<List<BudgetSummary>> =
        AutoFetchLiveData {
            CoroutineScope(dispatcher.io()).launch {
                val budgetSummaries = try {
                    budgetService.listBudgets()
                } catch (t: Throwable) {
                    emptyList()
                }
                it.postValue(budgetSummaries)
            }
        }

    val budgets: LiveData<List<BudgetSummary>> = _budgets

    private val _budgetsState: AutoFetchLiveData<ResponseState<List<BudgetSummary>>> =
        AutoFetchLiveData {
            CoroutineScope(dispatcher.io()).launch {
                it.postValue(LoadingState)
                val budgetSummaries = try {
                    SuccessState(budgetService.listBudgets())
                } catch (t: Throwable) {
                    ErrorState(t)
                }
                it.postValue(budgetSummaries)
            }
        }

    val budgetsState: LiveData<ResponseState<List<BudgetSummary>>> = _budgetsState

    override fun refresh() {
        _budgets.update()
    }

    fun createOrUpdateBudget(descriptor: BudgetCreateOrUpdateDescriptor, resultHandler: ResultHandler<BudgetSpecification>) {
        CoroutineScope(dispatcher.io()).launchForResult(resultHandler) {
            val budgetSpecification = if (descriptor.id == null) {
                budgetService.createBudget(descriptor)
            } else {
                budgetService.updateBudget(descriptor)
            }
            _budgets.update()
            return@launchForResult budgetSpecification
        }
    }

    fun archiveBudget(id: String, resultHandler: ResultHandler<Unit>) {
        CoroutineScope(dispatcher.io()).launchForResult(resultHandler) {
            budgetService.archiveBudget(id)
            _budgets.update()
        }
    }

    fun transactions(
        budgetId: String,
        start: Instant,
        end: Instant
    ) = BudgetTransactionsLiveData(budgetService, budgetId, start, end, dispatcher, transactionUpdateEventBus)

    fun budgetPeriodDetails(
        budgetId: String,
        start: Instant,
        end: Instant,
        resultHandler: ResultHandler<Pair<BudgetSpecification, List<BudgetPeriod>>>
    ) {
        CoroutineScope(dispatcher.io()).launchForResult(resultHandler) {
            budgetService.budgetPeriodDetails(budgetId, start, end)
        }
    }


    private val _budgetPeriodDetailsState: MutableLiveData<ResponseState<Pair<BudgetSpecification, List<BudgetPeriod>>>> = MutableLiveData(LoadingState)
    val budgetPeriodDetailsState = _budgetPeriodDetailsState

    fun requestBudgetPeriodDetailsState(
        budgetId: String,
        start: Instant,
        end: Instant
    ) {
        _budgetPeriodDetailsState.postValue(LoadingState)
        CoroutineScope(dispatcher.io()).launch {
            _budgetPeriodDetailsState.postValue(SuccessState(budgetService.budgetPeriodDetails(budgetId, start, end)))
        }
    }
}

