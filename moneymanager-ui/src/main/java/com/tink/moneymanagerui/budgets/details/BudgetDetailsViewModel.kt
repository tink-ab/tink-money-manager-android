package com.tink.moneymanagerui.budgets.details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tink.model.budget.Budget
import com.tink.model.budget.BudgetPeriod
import com.tink.model.budget.BudgetSpecification
import com.tink.model.budget.RecurringPeriodicity
import com.tink.model.misc.Amount
import com.tink.model.misc.ExactNumber
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.budgets.creation.specification.EXACT_NUMBER_ZERO
import com.tink.moneymanagerui.budgets.details.model.BudgetDetailsData
import com.tink.moneymanagerui.budgets.details.model.BudgetDetailsState
import com.tink.moneymanagerui.budgets.details.model.BudgetSelectionData
import com.tink.moneymanagerui.extensions.toStartOfLocalDate
import com.tink.moneymanagerui.extensions.totalMonths
import com.tink.moneymanagerui.overview.charts.map
import com.tink.moneymanagerui.util.extensions.floorAmount
import com.tink.moneymanagerui.util.extensions.formatCurrencyExact
import com.tink.service.network.ErrorState
import com.tink.service.network.LoadingState
import com.tink.service.network.ResponseState
import com.tink.service.network.SuccessState
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.Months
import org.joda.time.Weeks
import org.joda.time.Years
import org.threeten.bp.Instant
import org.threeten.bp.Period
import se.tink.android.di.application.ApplicationScoped
import se.tink.android.livedata.map
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
    private val budgetSelectionControllerNew: BudgetSelectionControllerNew,
    private val dateUtils: DateUtils,
    @ApplicationScoped context: Context
) : ViewModel() {

    private val budgetHeaderText: LiveData<ResponseState<String>> =
        budgetSelectionStateLiveData { successData ->
            getBudgetHeaderText(successData.currentSelectedPeriod)
        }

    private val amountLeft: LiveData<ResponseState<String>> =
        budgetSelectionStateLiveData { successData ->
            (successData.currentSelectedPeriod.budgetAmount - successData.currentSelectedPeriod.spentAmount)
                .formatCurrencyExact()
        }

    private val totalAmount: LiveData<ResponseState<String>> = budgetSelectionStateLiveData { successData ->
        successData.currentSelectedPeriod.budgetAmount.formatCurrencyExact()
    }

    private val amountLeftColor: LiveData<ResponseState<Int>> = budgetSelectionStateLiveData { successData ->
        val amountLeft = (successData.currentSelectedPeriod.budgetAmount - successData.currentSelectedPeriod.spentAmount)
            .value.toBigDecimal().toFloat()
        if (amountLeft < 0) {
            return@budgetSelectionStateLiveData R.attr.tink_warningColor
        } else {
            return@budgetSelectionStateLiveData R.attr.tink_expensesColor
        }
    }

    private val progress: LiveData<ResponseState<Double>> = budgetSelectionStateLiveData { successData ->
        val budgetAmount = successData.currentSelectedPeriod.budgetAmount.value
        if (budgetAmount.isZero()) {
            0.0
        } else {
            budgetAmount
                .subtract(successData.currentSelectedPeriod.spentAmount.value)
                .takeIf { amountLeft -> amountLeft.isBiggerThanOrEqual(EXACT_NUMBER_ZERO) }
                ?.divide(budgetAmount)?.doubleValue() ?: 1.0
        }
    }
    private val budget: LiveData<ResponseState<BudgetSpecification>> = budgetSelectionStateLiveData { successData ->
       successData.budget
    }

    private val budgetPeriodsList: LiveData<ResponseState<List<BudgetPeriod>>> =  budgetSelectionStateLiveData { successData ->
        successData.budgetPeriodsList.takeLast(min(successData.budgetPeriodsList.size, MAX_PERIOD_COUNT))
    }

    private val activeBudgetPeriodsCount = MediatorLiveData<ResponseState<Int>>().apply {
        value = LoadingState

        fun update() {
            val budgetPeriodsListValue = budgetPeriodsList.value
            val budgetValue = budget.value
            value = if (budgetValue is SuccessState && budgetPeriodsListValue is SuccessState) {
                val count = budgetPeriodsListValue.data
                    .filter {budgetValue.data.created.isBefore(it.end)}
                    .size
                SuccessState(count)
            } else if (budget.value is ErrorState || budgetPeriodsList.value is ErrorState) {
                ErrorState("")
            } else {
                LoadingState
            }
        }
        addSource(budget) { update() }
        addSource(budgetPeriodsList) { update() }
    }

    private fun <T> budgetSelectionStateLiveData(onSuccess: (BudgetSelectionData) -> T) =
        Transformations.map(budgetSelectionControllerNew.budgetSelectionState) { budgetSelectionState ->
            budgetSelectionState.map { budgetSelectionData -> onSuccess(budgetSelectionData) }
        }

    private val budgetDetailsData = MediatorLiveData<BudgetDetailsState>().apply {
        value = BudgetDetailsState()

        addSource(budget) {
            value = requireValue.copy(budget = it)
        }

        addSource(budgetHeaderText) {
            value = requireValue.copy(headerText = it)
        }

        addSource(amountLeft) {
            value = requireValue.copy(amountLeft = it)
        }

        addSource(amountLeftColor) {
            value = requireValue.copy(amountLeftColor = it)
        }

        addSource(totalAmount) {
            value = requireValue.copy(totalAmount = it)
        }

        addSource(progress) {
            value = requireValue.copy(progress = it)
        }

        addSource(budgetPeriodsList) {
            value = requireValue.copy(budgetPeriodsList = it)
        }
    }

    internal val budgetDetailsDataState: LiveData<ResponseState<BudgetDetailsData>> = budgetDetailsData.map {
        it.overallState
    }

    private fun getBudgetHeaderText(budgetPeriod: BudgetPeriod): String {
        val startOfToday = Instant.now().toDateTime().toStartOfLocalDate()
        val budgetStart = budgetPeriod.start.toDateTime().toStartOfLocalDate()
        val budgetEnd = budgetPeriod.end.toDateTime().toStartOfLocalDate()

        return when {
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

    val hasNext get() =  budgetSelectionControllerNew.hasNext
    val hasPrevious get() = budgetSelectionControllerNew.hasPrevious

    fun showNextPeriod() = budgetSelectionControllerNew.next()
    fun showPreviousPeriod() = budgetSelectionControllerNew.previous()

    @Deprecated("Use state instead")
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

    @Deprecated("Use state instead")
    val isBarChartShowing = MutableLiveData<Boolean>().apply { value = false }
    @Deprecated("Use state instead")
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

    @Deprecated("Use state instead")
    val error get() = budgetDetailsDataHolder.error
    @Deprecated("Use state instead")
    val errorState: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        value = false
        addSource(error) { event ->
            event.getContentIfNotHandled()?.let { value = true }
        }
    }



    @Deprecated("Use state instead")
    val historicPeriodData: LiveData<List<Float>> =
        Transformations.map(historicPeriodsList) { periodsList ->
            periodsList.map { it.spentAmount.value.toBigDecimal().toFloat() }
        }

    @Deprecated("Use state instead")
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
        addSource(budgetPeriodsList) { update() }
        addSource(budgetDetailsDataHolder.budget) { update() }
    }

    @Deprecated("Use state instead")
    val barChartEnabled: LiveData<Boolean> = Transformations.map(budgetDetailsDataHolder.budget) {
        it.periodicity is Budget.Periodicity.Recurring
    }

    @Deprecated("Use state instead")
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

    @Deprecated("Use state instead")
    val barChartEmptyMessage: LiveData<String> =
        Transformations.map(budgetDetailsDataHolder.budget) {
            context.getString(
                R.string.tink_budget_details_chart_empty_message,
                it.name
            )
        }

    @Deprecated("Use state instead")
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

    @Deprecated("Use state instead")
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

    @Deprecated("Use state instead")
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

    @Deprecated("Use state instead")
    private val barChartStatusMessage: LiveData<String> = MediatorLiveData<String>().apply {
        fun update() {
            if (barChartEnabled.value == true) {
                if (barChartEmpty.value == true) {
                    value = ""
                    return
                }
                whenNonNull(
                    budgetDetailsDataHolder.budgetPeriodsList.value,
                    budgetDetailsDataHolder.budget.value
                ) { periodsList, budget ->
                    val percentage = getBudgetManagedPercentage(periodsList, budget.created)

                    value = context.resources.getQuantityString(
                        R.plurals.tink_budget_details_chart_status_message_last_year, percentage, percentage)
                }
            }
        }
        addSource(barChartEmpty) { update() }
        addSource(budgetDetailsDataHolder.budget) { update() }
        addSource(barChartEnabled) { update() }
        addSource(budgetDetailsDataHolder.budgetPeriodsList) { update() }
    }

    @Deprecated("Use state instead")
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
}

private fun getBudgetManagedPercentage(periodsList: List<BudgetPeriod>, budgetCreated: Instant): Int {
    val periodsForBudget = periodsList.filter { it.end > budgetCreated }
    val budgetManagedCount = periodsForBudget
        .count {
            (it.budgetAmount - it.spentAmount).value.isBiggerThan(EXACT_NUMBER_ZERO)
        }
    return (budgetManagedCount * 100.0f / periodsForBudget.size).roundToInt()
}

// TODO: These guys should probably be somewhere else or be part of the class
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

        val remainingAmountForPeriod: Amount = when {
            remainingYears > 1 -> remainingAmount / remainingYears
            remainingMonths > 1 -> remainingAmount / remainingMonths
            remainingWeeks > 1 -> remainingAmount / remainingWeeks
            remainingDays > 1 -> remainingAmount / remainingDays
            else -> remainingAmount
        }

        when {
            hasNoMoneyLeftPerPeriod(remainingAmountForPeriod) -> context.getString(R.string.tink_budget_details_amount_left_none_message)
            remainingYears > 1 -> {
                context.getString(
                    R.string.tink_budget_details_amount_left_yearly_message,
                    remainingAmountForPeriod.floorAmount()
                )
            }
            remainingMonths > 1 -> {
                context.getString(
                    R.string.tink_budget_details_amount_left_monthly_message, remainingAmountForPeriod.floorAmount()
                )
            }
            remainingWeeks > 1 -> {
                context.getString(
                    R.string.tink_budget_details_amount_left_weekly_message, remainingAmountForPeriod.floorAmount()
                )
            }
            remainingDays > 1 -> {
                context.getString(
                    R.string.tink_budget_details_amount_left_daily_message, remainingAmountForPeriod.floorAmount()
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

private fun hasNoMoneyLeftPerPeriod(remainingAmount: Amount) = remainingAmount.value.toBigDecimal().toInt() == 0

internal fun ExactNumber.isZero() = this.toBigDecimal().signum() == 0
internal fun ExactNumber.isBiggerThanOrEqual(other: ExactNumber) = this.toBigDecimal() >= other.toBigDecimal()

internal data class VisibleState(val isLoading: Boolean, val isError: Boolean) {
    val isVisible = !isLoading && !isError
}
