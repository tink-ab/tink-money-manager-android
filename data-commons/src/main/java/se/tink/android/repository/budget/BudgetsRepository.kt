package se.tink.android.repository.budget

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.tink.annotations.PfmScope
import com.tink.model.budget.Budget.Periodicity.Recurring.PeriodUnit
import com.tink.model.budget.Budget.Specification.Filter
import com.tink.model.budget.BudgetCreateOrUpdateDescriptor
import com.tink.model.budget.BudgetPeriod
import com.tink.model.budget.BudgetSpecification
import com.tink.model.budget.BudgetSummary
import com.tink.model.budget.BudgetTransaction
import com.tink.model.budget.RecurringPeriodicity
import com.tink.model.misc.Amount
import com.tink.model.misc.ExactNumber
import com.tink.service.budget.BudgetService
import com.tink.service.handler.ResultHandler
import org.threeten.bp.Instant
import se.tink.android.AppExecutors
import se.tink.android.livedata.AutoFetchLiveData
import se.tink.android.livedata.ErrorOrValue
import se.tink.android.livedata.createResultHandler
import com.tink.model.transaction.Transaction
import se.tink.repository.SimpleChangeObserver
import se.tink.repository.cache.LiveDataCache
import com.tink.service.transaction.TransactionService
import se.tink.android.extensions.toListChangeObserver
import javax.inject.Inject

@PfmScope
class BudgetsRepository @Inject constructor(
    private val budgetService: BudgetService,
    private val transactionService: TransactionService,
    private val appExecutors: AppExecutors
//TODO: CoreSetup    private val cache: LiveDataCache<List<BudgetSummary>>
) {

    private val _budgets = AutoFetchLiveData<ErrorOrValue<List<BudgetSummary>>> {
        budgetService.listBudgets(it.createResultHandler())
    }

    init {
        _budgets.observeForever { result ->
            result?.value?.let { list ->
                appExecutors.backgroundExecutor.execute {
//               TODO     cache.clearAndInsert(list)
                }
            }
        }
        transactionService.subscribe(object : SimpleChangeObserver<Transaction>() {
            override fun onUpdate(items: MutableList<Transaction>?) = _budgets.update()
        }.toListChangeObserver())
    }

    val budgets: LiveData<List<BudgetSummary>> =
        MediatorLiveData<List<BudgetSummary>>().apply {
//     TODO       addSource(cache.read()) { result ->
//                result.let { value = it }
//            }
        }

    private val mockBudgetSpecification = BudgetSpecification(
        //id = "Mock ID",
        id = "0f174f4ec07b44b1bb946e238f8859e8",
        name = "Mock Name",
        description = "Mock Description",
        amount = Amount(ExactNumber(500L, 0), "SEK"),
        archived = false,
        filter = Filter(listOf(), listOf(), listOf(), ""),
        periodicity = RecurringPeriodicity(
            unit = PeriodUnit.WEEK,
            quantity = 1
        )
    )

    fun getBudgetSpecification(id: String): BudgetSpecification {
        return mockBudgetSpecification
    }

    fun createOrUpdateBudget(
        descriptor: BudgetCreateOrUpdateDescriptor,
        resulthandler: ResultHandler<BudgetSpecification>
    ) {
        if (descriptor.id == null) {
            budgetService.createBudget(descriptor, resulthandler.alsoDo { _budgets.update() })
        } else {
            budgetService.updateBudget(descriptor, resulthandler.alsoDo { _budgets.update() })
        }
    }

    fun deleteBudget(id: String, resulthandler: ResultHandler<Unit>) =
        budgetService.deleteBudget(id, resulthandler.alsoDo {
            budgets.value
                ?.find { budget -> budget.budgetSpecification.id == id }
//       TODO         ?.also { budget -> cache.delete(listOf(budget)) }
                ?: _budgets.update()
        })

    fun transactions(
        budgetId: String,
        start: Instant,
        end: Instant
    ): LiveData<ErrorOrValue<List<BudgetTransaction>>> {
        return BudgetTransactionsLiveData(transactionService, budgetService, budgetId, start, end)
    }

    fun budgetPeriodDetails(
        budgetId: String,
        start: Instant,
        end: Instant,
        resultHandler: ResultHandler<Pair<BudgetSpecification, List<BudgetPeriod>>>
    ) = budgetService.budgetPeriodDetails(budgetId, start, end, resultHandler)

    fun <T : Any?> ResultHandler<T>.alsoDo(action: (T?) -> Unit) =
        ResultHandler<T>(
            onSuccess = {
                this.onSuccess(it)
                action(it)
            },
            onError = onError
        )

}

