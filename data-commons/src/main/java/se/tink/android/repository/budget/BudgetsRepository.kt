package se.tink.android.repository.budget

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import org.joda.time.DateTime
import se.tink.android.AppExecutors
import se.tink.android.livedata.AutoFetchLiveData
import se.tink.android.livedata.ErrorOrValue
import se.tink.android.livedata.createMutationHandler
import se.tink.core.models.budgets.Budget.Periodicity.Recurring.PeriodUnit
import se.tink.core.models.budgets.Budget.Specification.Filter
import se.tink.core.models.budgets.BudgetCreateOrUpdateDescriptor
import se.tink.core.models.budgets.BudgetPeriod
import se.tink.core.models.budgets.BudgetSpecification
import se.tink.core.models.budgets.BudgetSummary
import se.tink.core.models.budgets.BudgetTransaction
import se.tink.core.models.budgets.RecurringPeriodicity
import se.tink.core.models.misc.Amount
import se.tink.core.models.misc.ExactNumber
import se.tink.core.models.transaction.Transaction
import se.tink.repository.MutationHandler
import se.tink.repository.SimpleChangeObserver
import se.tink.repository.TinkNetworkError
import se.tink.repository.cache.LiveDataCache
import se.tink.repository.service.BudgetService
import se.tink.repository.service.TransactionService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BudgetsRepository @Inject constructor(
    private val budgetService: BudgetService,
    private val transactionService: TransactionService,
    private val appExecutors: AppExecutors,
    private val cache: LiveDataCache<List<BudgetSummary>>
) {

    private val _budgets = AutoFetchLiveData<ErrorOrValue<List<BudgetSummary>>> {
        budgetService.listBudgets(it.createMutationHandler())
    }

    init {
        _budgets.observeForever { result ->
            result?.value?.let { list ->
                appExecutors.backgroundExecutor.execute {
                    cache.clearAndInsert(list)
                }
            }
        }
        transactionService.subscribe(object : SimpleChangeObserver<Transaction>() {
            override fun onUpdate(items: MutableList<Transaction>?) = _budgets.update()
        })
    }

    val budgets: LiveData<List<BudgetSummary>> =
        MediatorLiveData<List<BudgetSummary>>().apply {
            addSource(cache.read()) { result ->
                result.let { value = it }
            }
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
        mutationHandler: MutationHandler<BudgetSpecification>
    ) {
        if (descriptor.id == null) {
            budgetService.createBudget(descriptor, mutationHandler.alsoDo { _budgets.update() })
        } else {
            budgetService.updateBudget(descriptor, mutationHandler.alsoDo { _budgets.update() })
        }
    }

    fun deleteBudget(id: String, mutationHandler: MutationHandler<Unit>) =
        budgetService.deleteBudget(id, mutationHandler.alsoDo {
            budgets.value
                ?.find { budget -> budget.budgetSpecification.id == id }
                ?.also { budget -> cache.delete(listOf(budget)) }
                ?: _budgets.update()
        })

    fun transactions(
        budgetId: String,
        start: DateTime,
        end: DateTime
    ): LiveData<ErrorOrValue<List<BudgetTransaction>>> {
        return BudgetTransactionsLiveData(transactionService, budgetService, budgetId, start, end)
    }

    fun budgetPeriodDetails(
        budgetId: String,
        start: DateTime,
        end: DateTime,
        mutationHandler: MutationHandler<Pair<BudgetSpecification, List<BudgetPeriod>>>
    ) = budgetService.budgetPeriodDetails(budgetId, start, end, mutationHandler)

    fun <T : Any?> MutationHandler<T>.alsoDo(action: (T?) -> Unit) =
        object : MutationHandler<T> {
            override fun onError(error: TinkNetworkError?) = this@alsoDo.onError(error)
            override fun onCompleted() = this@alsoDo.onCompleted()

            override fun onNext(item: T?) {
                this@alsoDo.onNext(item)
                action.invoke(item)
            }
        }
}

