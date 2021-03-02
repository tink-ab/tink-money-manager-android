package com.tink.moneymanagerui.budgets.details

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.tink.model.budget.BudgetPeriod
import com.tink.model.budget.BudgetSpecification
import com.tink.model.time.Period
import com.tink.model.transaction.Transaction
import com.tink.moneymanagerui.extensions.getInstant
import com.tink.moneymanagerui.extensions.minusMonths
import com.tink.moneymanagerui.repository.StatisticsRepository
import com.tink.service.handler.ResultHandler
import kotlinx.coroutines.Job
import org.joda.time.DateTime
import org.threeten.bp.Instant
import se.tink.android.repository.TinkNetworkError
import se.tink.android.repository.budget.BudgetsRepository
import se.tink.android.repository.transaction.TransactionUpdateEventBus
import se.tink.commons.extensions.toDateTime
import se.tink.commons.extensions.whenNonNull
import se.tink.commons.livedata.Event
import java.util.TreeSet

internal class BudgetSelectionController(
    private val budgetId: String,
    private val budgetsRepository: BudgetsRepository,
    statisticsRepository: StatisticsRepository,
    lifecycle: Lifecycle,
    private val periodStart: DateTime?,
    private val transactionUpdateEventBus: TransactionUpdateEventBus
) : LifecycleObserver {
    private val _budgetSpecification = MutableLiveData<BudgetSpecification>()
    val budgetSpecification: LiveData<BudgetSpecification> = _budgetSpecification
    val budgetPeriods = TreeSet<BudgetPeriod> { first, second ->
        first.start.compareTo(second.start)
    }
    private val _budgetPeriodsList = MutableLiveData<List<BudgetPeriod>>()
    val budgetPeriodsList: LiveData<List<BudgetPeriod>> = _budgetPeriodsList
    private val _error = MutableLiveData<Event<TinkNetworkError>>()
    val error: LiveData<Event<TinkNetworkError>> = _error
    private val periods: LiveData<List<Period>> = statisticsRepository.periods
    private val _currentSelectedPeriod = MutableLiveData<BudgetPeriod>()
    val currentSelectedPeriod: LiveData<BudgetPeriod> = _currentSelectedPeriod

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
            .let { list -> currentSelectedPeriod.value?.let { list.toMutableList() + it } }
            ?.distinct()
            ?.forEach { updateBudgetPeriods(budgetId, it.start, it.end) }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun stopListeningForTransactionUpdates() {
        updateListenerJob?.cancel()
    }

    init {
        updateBudgetPeriods(
            budgetId,
            DateTime.now().minusMonths(12).getInstant(),
            Instant.now()
        )
        lifecycle.addObserver(this)
    }

    private fun updateBudgetPeriods(budgetId: String, start: Instant, end: Instant) {
        budgetsRepository.budgetPeriodDetails(
            start = start, //up for discussion
            end = end, //make sure current period is in
            budgetId = budgetId,
            resultHandler =
                ResultHandler(
                    { periodDetails ->
                        _budgetSpecification.postValue(periodDetails.first)
                        budgetPeriods.removeAll(periodDetails.second)
                        budgetPeriods.addAll(periodDetails.second)
                        _budgetPeriodsList.postValue(budgetPeriods.toList())
                        updateCurrent()
                    },
                    {
                        _error.postValue(Event(TinkNetworkError(it)))
                    }
                )
        )
    }

    private fun updateCurrent() {
        _currentSelectedPeriod.postValue(
            _currentSelectedPeriod.value?.let { current ->
                budgetPeriods.first {
                    it.start == current.start
                }
            } ?: periodStart?.let { preselectedStart ->
                budgetPeriods.firstOrNull {
                    it.start.toDateTime()
                        .isBefore(preselectedStart.toDateTime()) && it.end.toDateTime()
                        .isAfter(preselectedStart)
                }
            } ?: budgetPeriods.firstOrNull {
                it.start.toDateTime().isBeforeNow && it.end.toDateTime().isAfterNow
            } ?: budgetPeriods.last()
        )
    }

    fun previous() {
        _currentSelectedPeriod.value = budgetPeriods.lower(_currentSelectedPeriod.value)
        whenNonNull(
            budgetId,
            currentSelectedPeriod.value,
            periods.value
        ) { budgetId, currentSelectedPeriod, periods ->
            if (shouldFetchMore(currentSelectedPeriod, periods)) {
                val newPeriodRange = backTrackPeriodRange(currentSelectedPeriod, periods)
                updateBudgetPeriods(
                    budgetId,
                    newPeriodRange.first,
                    newPeriodRange.second
                )
            }
        }
    }

    fun next() {
        _currentSelectedPeriod.value = budgetPeriods.higher(_currentSelectedPeriod.value)
        // Fetching more should not be necessary, since we don´t need the user to be able to
        // "see in the future"
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