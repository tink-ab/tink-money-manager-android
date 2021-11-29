package com.tink.moneymanagerui.budgets.details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tink.model.budget.Budget
import com.tink.model.budget.BudgetPeriod
import com.tink.model.budget.BudgetPeriodicity
import com.tink.model.budget.RecurringPeriodicity
import com.tink.model.misc.Amount
import com.tink.model.misc.ExactNumber
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.budgets.creation.specification.EXACT_NUMBER_ZERO
import com.tink.moneymanagerui.extensions.toHistoricIntervalLabel
import com.tink.moneymanagerui.extensions.toPeriodChartLabel
import com.tink.moneymanagerui.extensions.toStartOfLocalDate
import com.tink.moneymanagerui.extensions.totalMonths
import com.tink.moneymanagerui.util.extensions.floorAmount
import com.tink.moneymanagerui.util.extensions.formatCurrencyExact
import com.tink.moneymanagerui.util.extensions.formatCurrencyExactIfNotIntegerWithSign
import com.tink.moneymanagerui.util.extensions.formatCurrencyRound
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.Months
import org.joda.time.Weeks
import org.joda.time.Years
import org.threeten.bp.Instant
import org.threeten.bp.Period
import se.tink.android.di.application.ApplicationScoped
import se.tink.android.livedata.requireValue
import se.tink.commons.extensions.*
import se.tink.utils.DateUtils
import javax.inject.Inject
import kotlin.math.absoluteValue
import kotlin.math.min
import kotlin.math.roundToInt

private const val MAX_PERIOD_COUNT = 12

internal class BudgetDetailsViewModel @Inject constructor(
    private val budgetDetailsDataHolder: BudgetDetailsDataHolder,
    private val dateUtils: DateUtils,
    @ApplicationScoped context: Context
) : ViewModel() {

    val budgetHeaderText: LiveData<String> =
        Transformations.map(budgetDetailsDataHolder.budgetPeriod) { budgetPeriod ->

            val startOfToday = Instant.now().toDateTime().toStartOfLocalDate()
            val budgetStart = budgetPeriod.start.toDateTime().toStartOfLocalDate()
            val budgetEnd = budgetPeriod.end.toDateTime().toStartOfLocalDate()

            when {
                budgetPeriod.start.toDateTime().isAfter(Instant.now().toDateTime()) -> {
                    val period = Period.between(budgetStart, startOfToday)
                    budgetHeaderTextFormatter(
                        BudgetStatus.NOT_STARTED,
                        period.days.absoluteValue,
                        period.totalMonths.absoluteValue
                    )
                }
                Instant.now().toDateTime().isAfter(budgetPeriod.end.toDateTime()) -> {
                    val between = Period.between(budgetEnd, startOfToday)
                    budgetHeaderTextFormatter(
                        BudgetStatus.ENDED,
                        between.days.absoluteValue,
                        between.totalMonths.absoluteValue
                    )
                }
                else -> {
                    val between = Period.between(budgetEnd, startOfToday)
                    budgetHeaderTextFormatter(
                        BudgetStatus.ACTIVE,
                        between.days.absoluteValue,
                        between.totalMonths.absoluteValue
                    )
                }
            }
        }

    private val budgetHeaderTextFormatter: (BudgetStatus, Int, Int) -> String = { status, daysDifference, monthsDifference ->
        val roundedMonthsDifference = if (monthsDifference > 0 && daysDifference > 0) {
            monthsDifference + 1
        } else {
            monthsDifference
        }
        val monthsRemainingMessage =
            context.resources.getQuantityString(
                R.plurals.tink_budget_months_plural,
                roundedMonthsDifference,
                roundedMonthsDifference
            )
        val daysRemainingMessage = context.resources.getQuantityString(
            R.plurals.tink_budget_days_plural,
            daysDifference,
            daysDifference
        )
        val timeUnitsUsed = if (roundedMonthsDifference > 0) {
            monthsRemainingMessage
        } else {
            daysRemainingMessage
        }
        when (status) {
            BudgetStatus.ACTIVE -> {
                context.getString(R.string.tink_budget_details_remaining, timeUnitsUsed)
            }
            BudgetStatus.ENDED -> {
                context.getString(R.string.tink_budget_details_ended_months_days_ago, timeUnitsUsed)
            }
            BudgetStatus.NOT_STARTED -> {
                context.getString(R.string.tink_budget_details_starts_in, timeUnitsUsed)
            }
        }
    }

    val totalAmount: LiveData<String> =
        Transformations.map(budgetDetailsDataHolder.budgetPeriod) { budgetPeriod ->
            context.getString(R.string.tink_budget_details_total_amount, budgetPeriod.budgetAmount.formatCurrencyExact())
        }

    val amountLeft: LiveData<String> = Transformations.map(budgetDetailsDataHolder.budgetPeriod) {
        (it.budgetAmount - it.spentAmount).formatCurrencyExact()
    }

    val amountLeftColor: LiveData<Int> =
        Transformations.map(budgetDetailsDataHolder.amountLeft) {
            if (it < 0) {
                R.attr.tink_warningColor
            } else {
                R.attr.tink_expensesColor
            }
        }

    val progress: LiveData<Double> = Transformations.map(budgetDetailsDataHolder.budgetPeriod) {
        val budgetAmount = it.budgetAmount.value
        if (budgetAmount.isZero()) {
            0.0
        } else {
            budgetAmount
                .subtract(it.spentAmount.value)
                .takeIf { amountLeft -> amountLeft.isBiggerThanOrEqual(EXACT_NUMBER_ZERO) }
                ?.divide(budgetAmount)?.doubleValue() ?: 1.0
        }
    }

    val loading: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        fun updateLoadingState() {
            whenNonNull(
                budgetDetailsDataHolder.budget.value,
                budgetDetailsDataHolder.budgetPeriod.value
            ) { _, _ ->
                value = false
            }
        }
        value = true
        addSource(budgetDetailsDataHolder.budget) { updateLoadingState() }
        addSource(budgetDetailsDataHolder.budgetPeriod) { updateLoadingState() }
        addSource(budgetDetailsDataHolder.error) { event ->
            event.getContentIfNotHandled()?.let { value = false }
        }
    }

    val isBarChartShowing = MutableLiveData<Boolean>().apply { value = false }

    val showPickerButtons: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        value = false
        fun updatePickerState() {
            whenNonNull(
                budgetDetailsDataHolder.budget.value,
                loading.value,
                isBarChartShowing.value
            ) { budget, loading, isBarChartShowing ->
                value = budget.periodicity is Budget.Periodicity.Recurring && !loading && !isBarChartShowing
            }
        }
        addSource(budgetDetailsDataHolder.budget) { updatePickerState() }
        addSource(loading) { updatePickerState() }
        addSource(isBarChartShowing) { updatePickerState() }
    }

    val error get() = budgetDetailsDataHolder.error
    val errorState: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        value = false
        addSource(error) { event ->
            event.getContentIfNotHandled()?.let { value = true }
        }
    }
    val budget get() = budgetDetailsDataHolder.budget
    val budgetName get() = budgetDetailsDataHolder.budgetName
    val hasNext get() = budgetDetailsDataHolder.hasNext
    val hasPrevious get() = budgetDetailsDataHolder.hasPrevious

    val visibilityState: LiveData<VisibleState> = MediatorLiveData<VisibleState>().apply {
        value = VisibleState(isLoading = false, isError = false)
        addSource(loading) { isLoading ->
            value = requireValue.copy(isLoading = isLoading)
        }
        addSource(errorState) { isError ->
            value = requireValue.copy(isError = isError)
        }
    }

    private val historicPeriodsList: LiveData<List<BudgetPeriod>> =
        Transformations.map(budgetDetailsDataHolder.budgetPeriodsList) { periodsList ->
            periodsList.takeLast(min(periodsList.size, MAX_PERIOD_COUNT))
        }

    val activeBudgetPeriodsCount = MediatorLiveData<Int>().apply {
        fun update() {
            whenNonNull(
                historicPeriodsList.value,
                budgetDetailsDataHolder.budget.value
            ) { historicPeriodsList, budget ->
                postValue(
                    historicPeriodsList
                        .filter { budget.created.isBefore(it.end) }
                        .size
                )
            }
        }
        addSource(budgetDetailsDataHolder.budget) { update() }
        addSource(historicPeriodsList) { update() }
    }

    val historicPeriodData: LiveData<List<Float>> =
        Transformations.map(historicPeriodsList) { periodsList ->
            periodsList.map { it.spentAmount.value.toBigDecimal().toFloat() }
        }

    val periodChartLabels: LiveData<List<String>> = MediatorLiveData<List<String>>().apply {
        fun update() {
            whenNonNull(
                historicPeriodsList.value,
                budgetDetailsDataHolder.budget.value?.periodicity as? RecurringPeriodicity
            ) { periodsList, periodicity ->
                value = periodsList.map { period ->
                    period.toPeriodChartLabel(
                        context,
                        dateUtils,
                        periodicity
                    )
                }
            }
        }
        addSource(historicPeriodsList) { update() }
        addSource(budgetDetailsDataHolder.budget) { update() }
    }

    val periodChartThreshold: LiveData<Float> = Transformations.map(budget) {
        it.amount.value.toBigDecimal().toFloat()
    }

    val budgetPeriodicity: LiveData<BudgetPeriodicity> = Transformations.map(budget) { budgetSpecification ->
        budgetSpecification.periodicity
    }

    val periodChartThresholdLabel: LiveData<String> = Transformations.map(budget) {
        it.amount.formatCurrencyRound()
    }

    val barChartEnabled: LiveData<Boolean> = Transformations.map(budgetDetailsDataHolder.budget) {
        it.periodicity is Budget.Periodicity.Recurring
    }

    val barChartEmpty: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        value = false
        addSource(historicPeriodsList) { periodsList ->
            periodsList?.let {
                value = it.all { period ->
                    period.spentAmount.value.isZero()
                }
            }
        }
    }

    val barChartEmptyMessage: LiveData<String> =
        Transformations.map(budgetDetailsDataHolder.budget) {
            context.getString(
                R.string.tink_budget_details_chart_empty_message,
                it.name
            )
        }

    private val historicPeriodInterval = MediatorLiveData<String>().apply {
        fun update() {
            if (barChartEnabled.value == true) {
                whenNonNull(
                    historicPeriodsList.value,
                    budgetDetailsDataHolder.budget.value?.periodicity as? RecurringPeriodicity
                ) { periodsList, periodicity ->
                    val formattedStartPeriodLabel = periodsList.first().toHistoricIntervalLabel(
                        context,
                        dateUtils,
                        periodicity
                    )
                    val formattedEndPeriodLabel = periodsList.last().toHistoricIntervalLabel(
                        context,
                        dateUtils,
                        periodicity
                    )
                    value = "$formattedStartPeriodLabel - $formattedEndPeriodLabel"
                }
            }
        }
        addSource(budgetDetailsDataHolder.budget) { update() }
        addSource(barChartEnabled) { update() }
        addSource(historicPeriodsList) { update() }
    }

    val budgetPeriodInterval: LiveData<String> = MediatorLiveData<String>().apply {
        fun update() {
            value = if (isBarChartShowing.value == true && historicPeriodInterval.value != null) {
                historicPeriodInterval.value
            } else {
                budgetDetailsDataHolder.budgetPeriodInterval.value
            }
        }
        addSource(budgetDetailsDataHolder.budgetPeriodInterval) { update() }
        addSource(historicPeriodInterval) { update() }
        addSource(isBarChartShowing) { update() }
    }

    private val progressStatusMessage: LiveData<String> = MediatorLiveData<String>().apply {
        fun updateText() {
            whenNonNull(
                budgetDetailsDataHolder.budget.value,
                budgetDetailsDataHolder.budgetPeriod.value
            ) { budget, budgetPeriod ->
                val amountLeft = budgetPeriod.budgetAmount - budgetPeriod.spentAmount
                value = amountLeft.takeIf { it.value.isBiggerThan(EXACT_NUMBER_ZERO) }
                    ?.let {
                        val startEnd = when (val periodicity = budget.periodicity) {
                            is Budget.Periodicity.OneOff -> periodicity.start to periodicity.end
                            else -> budgetPeriod.start to budgetPeriod.end
                        }
                        composeRemainingBudgetStatusString(
                            it,
                            startEnd.first.toDateTime(),
                            startEnd.second.toDateTime(),
                            context
                        )
                    } ?: context.getString(R.string.tink_budget_details_over_budget_message)
            }
        }
        addSource(budgetDetailsDataHolder.budget) { updateText() }
        addSource(budgetDetailsDataHolder.budgetPeriod) { updateText() }
    }

    private val barChartStatusMessage: LiveData<String> = MediatorLiveData<String>().apply {
        fun update() {
            if (barChartEnabled.value == true) {
                if (barChartEmpty.value == true) {
                    value = ""
                    return
                }
                whenNonNull(
                    historicPeriodsList.value,
                    budgetDetailsDataHolder.budget.value
                ) { periodsList, budget ->
                    val periodicity = budget.periodicity as? RecurringPeriodicity ?: return
                    val lastPeriod = periodsList.last()
                    if (budget.created in lastPeriod.start..lastPeriod.end) {
                        // Set current period status message
                        val amountLeft = lastPeriod.budgetAmount - lastPeriod.spentAmount
                        value = when (periodicity.unit) {
                            Budget.Periodicity.Recurring.PeriodUnit.WEEK -> {
                                amountLeft
                                    .takeIf { it.value.isBiggerThan(EXACT_NUMBER_ZERO) }
                                    ?.let { amount ->
                                        context.getString(
                                            R.string.tink_budget_details_chart_status_message_current_week,
                                            amount.formatCurrencyExactIfNotIntegerWithSign()
                                        )
                                    }
                                    ?: context.getString(R.string.tink_budget_details_chart_over_budget_status_message_current_week)
                            }

                            Budget.Periodicity.Recurring.PeriodUnit.MONTH -> {
                                amountLeft
                                    .takeIf { it.value.isBiggerThan(EXACT_NUMBER_ZERO) }
                                    ?.let { amount ->
                                        context.getString(
                                            R.string.tink_budget_details_chart_status_message_current_month,
                                            amount.formatCurrencyExactIfNotIntegerWithSign()
                                        )
                                    }
                                    ?: context.getString(R.string.tink_budget_details_chart_over_budget_status_message_current_month)
                            }

                            Budget.Periodicity.Recurring.PeriodUnit.YEAR -> {
                                amountLeft
                                    .takeIf { it.value.isBiggerThan(EXACT_NUMBER_ZERO) }
                                    ?.let { amount ->
                                        context.getString(
                                            R.string.tink_budget_details_chart_status_message_current_year,
                                            amount.formatCurrencyExactIfNotIntegerWithSign()
                                        )
                                    }
                                    ?: context.getString(R.string.tink_budget_details_chart_over_budget_status_message_current_year)
                            }
                            else -> ""
                        }
                    } else {
                        // Set historical status message
                        val percentage = getBudgetManagedPercentage(periodsList)
                        value = if (periodicity.unit == Budget.Periodicity.Recurring.PeriodUnit.MONTH) {
                            context.resources.getQuantityString(R.plurals.tink_budget_details_chart_status_message_last_year, percentage, percentage)
                        } else {
                            val formattedStartPeriodLabel = periodsList.first().toHistoricIntervalLabel(
                                context,
                                dateUtils,
                                periodicity
                            )
                            context.resources.getQuantityString(R.plurals.tink_budget_details_chart_status_message_since, percentage, percentage, formattedStartPeriodLabel)
                        }
                    }
                }
            }
        }
        addSource(barChartEmpty) { update() }
        addSource(budgetDetailsDataHolder.budget) { update() }
        addSource(barChartEnabled) { update() }
        addSource(historicPeriodsList) { update() }
    }

    val statusMessage: LiveData<String> = MediatorLiveData<String>().apply {
        fun update() {
            value = if (isBarChartShowing.value == true && barChartStatusMessage.value != null) {
                barChartStatusMessage.value
            } else {
                progressStatusMessage.value
            }
        }
        addSource(progressStatusMessage) { update() }
        addSource(barChartStatusMessage) { update() }
        addSource(isBarChartShowing) { update() }
    }

    fun showNextPeriod() = budgetDetailsDataHolder.nextPeriod()
    fun showPreviousPeriod() = budgetDetailsDataHolder.previousPeriod()
}

private fun getBudgetManagedPercentage(historicPeriodsList: List<BudgetPeriod>): Int {
    val budgetManagedCount = historicPeriodsList.count {
        (it.budgetAmount - it.spentAmount).value.isBiggerThan(EXACT_NUMBER_ZERO)
    }
    return (budgetManagedCount * 100.0f / historicPeriodsList.size).roundToInt()
}

private fun composeRemainingBudgetStatusString(
    remainingAmount: Amount,
    start: DateTime,
    end: DateTime,
    context: Context
): String {
    val now = DateTime.now()

    // Inside a budget period
    return if (now.isAfter(start) && now.isBefore(end)) {

        val remainingYears = Years.yearsBetween(now, end).years
        val remainingMonths = Months.monthsBetween(now, end).months
        val remainingWeeks = Weeks.weeksBetween(now, end).weeks
        val remainingDays = Days.daysBetween(now, end).days

        val averageAmount: Amount

        when {
            remainingYears > 1 -> {
                averageAmount = remainingAmount / remainingYears
                context.getString(
                    R.string.tink_budget_details_amount_left_yearly_message, averageAmount.floorAmount()
                )
            }
            remainingMonths > 1 -> {
                averageAmount = remainingAmount / remainingMonths
                context.getString(
                    R.string.tink_budget_details_amount_left_monthly_message, averageAmount.floorAmount()
                )
            }
            remainingWeeks > 1 -> {
                averageAmount = remainingAmount / remainingWeeks
                context.getString(
                    R.string.tink_budget_details_amount_left_weekly_message, averageAmount.floorAmount()
                )
            }
            remainingDays > 1 -> {
                averageAmount = remainingAmount / remainingDays
                context.getString(
                    R.string.tink_budget_details_amount_left_daily_message, averageAmount.floorAmount()
                )
            }
            else -> {
                context.getString(R.string.tink_budget_details_amount_left_message_plain, remainingAmount.floorAmount())
            }
        }
    } else if (now.isAfter(end)) { // Budget period in the past
        context.getString(R.string.tink_budget_details_managed_budget_message)
    } else if (now.isBefore(start)) { // Budget period not started yet
        context.getString(R.string.tink_budget_details_not_started)
    } else {
        context.getString(
            R.string.tink_budget_details_amount_left_message_plain,
            remainingAmount.floorAmount()
        )
    }
}

internal fun ExactNumber.isZero() = this.toBigDecimal().signum() == 0
internal fun ExactNumber.isBiggerThanOrEqual(other: ExactNumber) = this.toBigDecimal() >= other.toBigDecimal()

internal data class VisibleState(val isLoading: Boolean, val isError: Boolean) {
    val isVisible = !isLoading && !isError
}