package com.tink.moneymanagerui.budgets.details

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.tink.model.budget.BudgetPeriod
import com.tink.model.budget.BudgetSpecification
import com.tink.model.time.Period
import com.tink.model.transaction.Transaction
import com.tink.moneymanagerui.budgets.details.model.BudgetSelectionData
import com.tink.moneymanagerui.budgets.details.model.BudgetSelectionState
import com.tink.moneymanagerui.extensions.getInstant
import com.tink.moneymanagerui.extensions.minusMonths
import com.tink.moneymanagerui.repository.StatisticsRepository
import com.tink.service.network.LoadingState
import com.tink.service.network.ResponseState
import com.tink.service.network.SuccessState
import com.tink.service.network.map
import kotlinx.coroutines.Job
import org.joda.time.DateTime
import org.threeten.bp.Instant
import se.tink.android.livedata.map
import se.tink.android.livedata.requireValue
import se.tink.android.repository.budget.BudgetsRepository
import se.tink.android.repository.transaction.TransactionUpdateEventBus
import se.tink.commons.extensions.toDateTime
import se.tink.commons.extensions.whenNonNull
import java.util.TreeSet

/**
 * TODO: Rename to BudgetSelectionController when BudgetTransactionListViewModel is refactored
 */
internal class BudgetSelectionControllerState(
    private val budgetId: String,
    private val budgetsRepository: BudgetsRepository,
    statisticsRepository: StatisticsRepository,
    lifecycle: Lifecycle,
    private val periodStart: DateTime?,
    private val transactionUpdateEventBus: TransactionUpdateEventBus
) : LifecycleObserver {

    private val budgetPeriods = TreeSet<BudgetPeriod> { first, second ->
        first.start.compareTo(second.start)
    }

    init {
        budgetsRepository.requestBudgetPeriodDetailsState(budgetId,
            DateTime.now().minusMonths(12).getInstant(),
            Instant.now())

        lifecycle.addObserver(this)
    }

    private val _budgetPeriodsState = budgetsRepository.budgetPeriodDetailsState

    private val _budgetSpecificationState = MediatorLiveData<ResponseState<BudgetSpecification>>().apply {
        value = LoadingState

        addSource(_budgetPeriodsState) { state ->
            value = state.map { periodDetails -> periodDetails.first }
        }
    }

    private val _budgetPeriodsListState = MediatorLiveData<ResponseState<List<BudgetPeriod>>>().apply {
        value = LoadingState

        addSource(_budgetPeriodsState) { state ->
            value = state.map { periodDetails ->
                calculateBudgetPeriodsTreeSet(periodDetails.second).toList()
            }
        }
    }

    private fun calculateBudgetPeriodsTreeSet(periods: List<BudgetPeriod>): TreeSet<BudgetPeriod> {
        budgetPeriods.removeAll(periods.toSet())
        budgetPeriods.addAll(periods)
        return budgetPeriods
    }

    private val _currentSelectedPeriodMutable = MutableLiveData<BudgetPeriod>()

    private val _currentSelectedPeriodState = MediatorLiveData<ResponseState<BudgetPeriod>>().apply {
        value = LoadingState

        addSource(_budgetPeriodsState) { state ->
            value = state.map { periodDetails ->
                val currentValue = value
                if (currentValue is SuccessState) {
                    updateCurrentPeriod(currentValue.data, calculateBudgetPeriodsTreeSet(periodDetails.second))
                } else {
                    updateCurrentPeriod(null, calculateBudgetPeriodsTreeSet(periodDetails.second))
                }
            }
        }

        addSource(_currentSelectedPeriodMutable) {value = SuccessState(it)}
    }

    private fun updateCurrentPeriod(currentValue: BudgetPeriod?, allPeriods: TreeSet<BudgetPeriod>): BudgetPeriod {
        return currentValue?.let { current ->
            allPeriods.first {
                it.start == current.start
            }
        } ?: periodStart?.let { preselectedStart ->
            allPeriods.firstOrNull {
                it.start.toDateTime()
                    .isBefore(preselectedStart.toDateTime()) && it.end.toDateTime()
                    .isAfter(preselectedStart)
            }
        } ?: allPeriods.firstOrNull {
            it.start.toDateTime().isBeforeNow && it.end.toDateTime().isAfterNow
        } ?: allPeriods.last()
    }

    private val budgetSelectionData = MediatorLiveData<BudgetSelectionState>().apply {
        value = BudgetSelectionState()

        addSource(_budgetSpecificationState) {
            value = requireValue.copy(budget = it)
        }

        addSource(_budgetPeriodsListState) {
            value = requireValue.copy(budgetPeriodsList = it)
        }

        addSource(_currentSelectedPeriodState) {
            value = requireValue.copy(currentSelectedPeriod = it)
        }
    }
    internal val budgetSelectionState: LiveData<ResponseState<BudgetSelectionData>> = budgetSelectionData.map {
        it.overallState
    }

    private val periods: LiveData<List<Period>> = statisticsRepository.periods

    private var updateListenerJob: Job? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun startListeningForTransactionUpdates() {
        updateListenerJob = transactionUpdateEventBus.subscribe { updatedTransaction ->
            updateWith(listOf(updatedTransaction))
        }
    }

    private fun updateWith(transactions: List<Transaction>) {
        transactions
            .map { it.date }
            .mapNotNull { date ->
                budgetPeriods.find {
                    it.start.isBefore(date) && it.end.isAfter(date)
                }
            }
            .let { list ->
                val currentPeriodState = _currentSelectedPeriodState.value
                if (currentPeriodState is SuccessState) {
                    list.toMutableList() + currentPeriodState.data
                }
                list
            }
            .distinct()
            .forEach {
                budgetsRepository.requestBudgetPeriodDetailsState(budgetId, it.start, it.end)
            }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun stopListeningForTransactionUpdates() {
        updateListenerJob?.cancel()
    }

    fun previous() {
        (_currentSelectedPeriodState.value as? SuccessState)?.data?.let { currentSelectedPeriodData ->
            _currentSelectedPeriodMutable.value = budgetPeriods.lower(currentSelectedPeriodData)
            whenNonNull(
                currentSelectedPeriodData,
                periods.value
            ) { currentSelectedPeriod, periods ->
                if (shouldFetchMore(currentSelectedPeriod, periods)) {
                    val newPeriodRange = backTrackPeriodRange(currentSelectedPeriod, periods)
                    budgetsRepository.requestBudgetPeriodDetailsState(
                        budgetId,
                        newPeriodRange.first,
                        newPeriodRange.second
                    )
                }
            }
        }
    }

    fun next() {
        (_currentSelectedPeriodState.value as? SuccessState)?.data?.let { currentSelectedPeriod ->
            _currentSelectedPeriodMutable.value = budgetPeriods.higher(currentSelectedPeriod)
        }

        // Fetching more should not be necessary, since we don´t need the user to be able to
        // "see in the future"
    }

    val hasNext: LiveData<Boolean> = createPeriodPickerStateLiveData { currentPeriod ->
        currentPeriod != budgetPeriods.last()
    }
    val hasPrevious: LiveData<Boolean> = createPeriodPickerStateLiveData { currentPeriod ->
        currentPeriod != budgetPeriods.first()
    }

    private inline fun createPeriodPickerStateLiveData(
        crossinline isVisible: (BudgetPeriod) -> Boolean
    ): LiveData<Boolean> {
        return MediatorLiveData<Boolean>().apply {
            addSource(_currentSelectedPeriodState) { currentPeriodState ->
                value = if (currentPeriodState is SuccessState) {
                    isVisible(currentPeriodState.data)
                } else {
                    false
                }

            }
        }
    }

    private fun shouldFetchMore(currentSelectedPeriod: BudgetPeriod, periods: List<Period>) =
        currentSelectedPeriod == budgetPeriods.first()
                && currentSelectedPeriod.start.isAfter(periods.first().start)

    private fun backTrackPeriodRange(
        currentPeriod: BudgetPeriod,
        periods: List<Period>
    ): Pair<Instant, Instant> {
        val currentStartMinus12Months = currentPeriod.start.minusMonths(12)
        return if (currentStartMinus12Months.isAfter(periods.first().start)) {
            currentStartMinus12Months to currentPeriod.start
        } else {
            periods.first().start to currentPeriod.start
        }
    }
}