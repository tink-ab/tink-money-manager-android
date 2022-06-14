package com.tink.moneymanagerui.budgets.details

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tink.model.budget.Budget
import com.tink.model.budget.BudgetPeriod
import com.tink.model.budget.BudgetSpecification
import com.tink.model.budget.RecurringPeriodicity
import com.tink.model.misc.Amount
import com.tink.model.misc.ExactNumber
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.budgets.creation.specification.EXACT_NUMBER_ZERO
import com.tink.moneymanagerui.budgets.details.model.BudgetSelectionData
import com.tink.moneymanagerui.budgets.details.usecases.GetBudgetHeaderTextUseCase
import com.tink.moneymanagerui.extensions.formattedPeriod
import com.tink.moneymanagerui.extensions.toHistoricIntervalLabel
import com.tink.moneymanagerui.extensions.toPeriodChartLabel
import com.tink.moneymanagerui.util.extensions.floorAmount
import com.tink.moneymanagerui.util.extensions.formatCurrencyExact
import com.tink.service.network.LoadingState
import com.tink.service.network.ResponseState
import com.tink.service.network.map
import se.tink.android.di.application.ApplicationScoped
import se.tink.commons.extensions.div
import se.tink.commons.extensions.divide
import se.tink.commons.extensions.doubleValue
import se.tink.commons.extensions.isBiggerThan
import se.tink.commons.extensions.minus
import se.tink.commons.extensions.subtract
import se.tink.commons.extensions.toDateTime
import se.tink.commons.extensions.totalMonths
import se.tink.commons.extensions.whenNonNull
import se.tink.utils.DateUtils
import java.time.Instant
import java.time.LocalDateTime
import java.time.Period
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import kotlin.math.absoluteValue
import kotlin.math.min
import kotlin.math.roundToInt

data class BudgetDetailsData(
    val budget: BudgetSpecification,
    val headerText: String,
    val amountLeft: String,
    val amountLeftColor: Int,
    val totalAmount: String,
    val progress: Double,
    val budgetPeriodsList: List<BudgetPeriod>,
    val activeBudgetPeriodsCount: Int,
    val budgetPeriodsData: List<Float>,
    val periodChartLabels: List<String>,
    val barChartEnabled: Boolean,
    val barChartEmpty: Boolean,
    val barChartEmptyMessage: String,
    val budgetPeriodIntervalText: String,
    val statusMessage: String,
    val showPickerButtons: Boolean
)

private const val MAX_PERIOD_COUNT = 12

internal class BudgetDetailsViewModel @Inject constructor(
    private val budgetSelectionControllerState: BudgetSelectionControllerState,
    private val dateUtils: DateUtils,
    @ApplicationScoped context: Context
) : ViewModel() {

    val isBarChartShowing = MutableLiveData<Boolean>().apply { value = false }

    val budgetDetailsData = MediatorLiveData<ResponseState<BudgetDetailsData>>().apply {
        value = LoadingState

        fun update() = whenNonNull(
            budgetSelectionControllerState.budgetSelectionState.value,
            isBarChartShowing.value
        ) { state, isBarChartShowing ->
            value = state.map { data ->
                val budgetPeriodList = getBudgetPeriodsList(data)
                val isBarChartEnabled = isBarChartEnabled(data)

                BudgetDetailsData(
                    budget = data.budget,
                    headerText = GetBudgetHeaderTextUseCase(budgetHeaderTextFormatter, data).invoke(),
                    amountLeft = getAmountLeft(data),
                    amountLeftColor = getAmountLeftColor(data),
                    totalAmount = getTotalAmount(data),
                    progress = getProgress(data),
                    budgetPeriodsList = budgetPeriodList,
                    activeBudgetPeriodsCount = getActiveBudgetPeriodsCount(data, budgetPeriodList),
                    budgetPeriodsData = getBudgetPeriodData(budgetPeriodList),
                    periodChartLabels = getPeriodChartLabels(data, budgetPeriodList, context),
                    barChartEnabled = isBarChartEnabled,
                    barChartEmpty = getBarChartEmpty(budgetPeriodList),
                    barChartEmptyMessage = getBarChartEmptyMessage(data, context),
                    budgetPeriodIntervalText = getBudgetPeriodIntervalText(data, budgetPeriodList, isBarChartEnabled, isBarChartShowing, context),
                    statusMessage = getStatusMessage(data, budgetPeriodList, isBarChartShowing, context),
                    showPickerButtons = getShowPickerButtons(data, isBarChartShowing)
                )
            }
        }

        addSource(budgetSelectionControllerState.budgetSelectionState) { update() }
        addSource(isBarChartShowing) { update() }
    }

    private fun getAmountLeft(successData: BudgetSelectionData) =
        (successData.currentSelectedPeriod.budgetAmount - successData.currentSelectedPeriod.spentAmount)
            .formatCurrencyExact()

    private fun getTotalAmount(successData: BudgetSelectionData) =
        successData.currentSelectedPeriod.budgetAmount.formatCurrencyExact()

    private fun getAmountLeftColor(successData: BudgetSelectionData): Int {
        val amountLeft = (successData.currentSelectedPeriod.budgetAmount - successData.currentSelectedPeriod.spentAmount)
            .value.toBigDecimal().toFloat()
        return if (amountLeft < 0) {
            R.attr.tink_warningColor
        } else {
            R.attr.tink_expensesColor
        }
    }

    private fun getProgress(successData: BudgetSelectionData): Double {
        val budgetAmount = successData.currentSelectedPeriod.budgetAmount.value
        return if (budgetAmount.isZero()) {
            0.0
        } else {
            budgetAmount
                .subtract(successData.currentSelectedPeriod.spentAmount.value)
                .takeIf { amountLeft -> amountLeft.isBiggerThanOrEqual(EXACT_NUMBER_ZERO) }
                ?.divide(budgetAmount)?.doubleValue() ?: 1.0
        }
    }

    private fun getBudgetPeriodsList(successData: BudgetSelectionData) =
        successData.budgetPeriodsList.takeLast(min(successData.budgetPeriodsList.size, MAX_PERIOD_COUNT))

    private fun getActiveBudgetPeriodsCount(successData: BudgetSelectionData, budgetPeriodsList: List<BudgetPeriod>): Int =
        budgetPeriodsList
            .filter { successData.budget.created.isBefore(it.end) }
            .size

    private fun getBudgetHeaderText(successData: BudgetSelectionData): String {
        val budgetPeriod = successData.currentSelectedPeriod

        val startOfToday = Instant.now().toDateTime().toLocalDate().atStartOfDay()
        val budgetStart = budgetPeriod.start.toDateTime().toLocalDate().atStartOfDay()
        val budgetEnd = budgetPeriod.end.toDateTime().toLocalDate().atStartOfDay()

        return when {
            budgetPeriod.start.toDateTime().isAfter(Instant.now().toDateTime()) -> {
                val period = Period.between(budgetStart.toLocalDate(), startOfToday.toLocalDate())
                budgetHeaderTextFormatter(
                    BudgetStatus.NOT_STARTED,
                    period.days.absoluteValue,
                    period.totalMonths().absoluteValue
                )
            }
            Instant.now().toDateTime().isAfter(budgetPeriod.end.toDateTime()) -> {
                val between = Period.between(budgetEnd.toLocalDate(), startOfToday.toLocalDate())
                budgetHeaderTextFormatter(
                    BudgetStatus.ENDED,
                    between.days.absoluteValue,
                    between.totalMonths().absoluteValue
                )
            }
            else -> {
                val between = Period.between(budgetEnd.toLocalDate(), startOfToday.toLocalDate())
                budgetHeaderTextFormatter(
                    BudgetStatus.ACTIVE,
                    between.days.absoluteValue,
                    between.totalMonths().absoluteValue
                )
            }
        }
    }

    private fun getBudgetPeriodData(budgetPeriods: List<BudgetPeriod>): List<Float> {
        return budgetPeriods.map { it.spentAmount.value.toBigDecimal().toFloat() }
    }

    private fun getPeriodChartLabels(
        successData: BudgetSelectionData,
        budgetPeriodsList: List<BudgetPeriod>,
        context: Context
    ): List<String> {
        if (successData.budget.periodicity !is RecurringPeriodicity) {
            return listOf()
        }

        return budgetPeriodsList.map { period ->
            period.toPeriodChartLabel(context, dateUtils, successData.budget.periodicity as RecurringPeriodicity)
        }
    }

    private fun isBarChartEnabled(successData: BudgetSelectionData): Boolean {
        return successData.budget.periodicity is Budget.Periodicity.Recurring
    }

    private fun getBarChartEmpty(budgetPeriodsList: List<BudgetPeriod>): Boolean {
        return budgetPeriodsList.all { period -> period.spentAmount.value.isZero() }
    }

    private fun getBarChartEmptyMessage(successData: BudgetSelectionData, context: Context): String {
        return context.getString(
            R.string.tink_budget_details_chart_empty_message,
            successData.budget.name
        )
    }

    private fun getBudgetPeriodIntervalText(
        successData: BudgetSelectionData,
        budgetPeriodList: List<BudgetPeriod>,
        barChartEnabled: Boolean,
        isBarChartShowing: Boolean,
        context: Context
    ): String {
        return if (!isBarChartShowing) {
            getProgressChartBudgetPeriodIntervalText(successData)
        } else {
            getBarChartBudgetPeriodIntervalText(successData, budgetPeriodList, barChartEnabled, context)
        }
    }

    private fun getProgressChartBudgetPeriodIntervalText(
        successData: BudgetSelectionData
    ): String {
        return when (val periodicity = successData.budget.periodicity) {
            is Budget.Periodicity.OneOff -> periodicity.formattedPeriod(dateUtils)
            is Budget.Periodicity.Recurring -> periodicity.formattedPeriod(successData.currentSelectedPeriod, dateUtils)
        }
    }

    private fun getBarChartBudgetPeriodIntervalText(
        successData: BudgetSelectionData,
        budgetPeriodList: List<BudgetPeriod>,
        barChartEnabled: Boolean,
        context: Context
    ): String {
        if (!barChartEnabled || successData.budget.periodicity !is RecurringPeriodicity) {
            return ""
        }
        val formattedStartPeriodLabel = budgetPeriodList.first().toHistoricIntervalLabel(
            context,
            dateUtils,
            successData.budget.periodicity as RecurringPeriodicity
        )
        val formattedEndPeriodLabel = budgetPeriodList.last().toHistoricIntervalLabel(
            context,
            dateUtils,
            successData.budget.periodicity as RecurringPeriodicity
        )
        return "$formattedStartPeriodLabel - $formattedEndPeriodLabel"
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

    private fun getStatusMessage(
        successData: BudgetSelectionData,
        budgetPeriodsList: List<BudgetPeriod>,
        isBarChartShowing: Boolean,
        context: Context
    ): String {
        return if (isBarChartShowing) {
            getChartStatusMessage(successData, budgetPeriodsList, context)
        } else {
            getProgressStatusMessage(successData, context)
        }
    }

    private fun getChartStatusMessage(
        successData: BudgetSelectionData,
        budgetPeriodsList: List<BudgetPeriod>,
        context: Context
    ): String {
        val percentage = getBudgetManagedPercentage(budgetPeriodsList, successData.budget.created)

        return context.resources.getQuantityString(
            R.plurals.tink_budget_details_chart_status_message_last_year, percentage, percentage
        )
    }

    private fun getProgressStatusMessage(
        successData: BudgetSelectionData,
        context: Context
    ): String {
        val amountLeft = successData.currentSelectedPeriod.budgetAmount - successData.currentSelectedPeriod.spentAmount
        return amountLeft.takeIf { it.value.isBiggerThan(EXACT_NUMBER_ZERO) }
            ?.let {
                val startEnd = when (val periodicity = successData.budget.periodicity) {
                    is Budget.Periodicity.OneOff -> periodicity.start to periodicity.end
                    else -> successData.currentSelectedPeriod.start to successData.currentSelectedPeriod.end
                }
                composeRemainingBudgetStatusString(
                    it,
                    startEnd.first.toDateTime(),
                    startEnd.second.toDateTime(),
                    context
                )
            } ?: context.getString(R.string.tink_budget_details_over_budget_message)
    }

    private fun getShowPickerButtons(
        successData: BudgetSelectionData,
        isBarChartShowing: Boolean
    ): Boolean {
        return successData.budget.periodicity is Budget.Periodicity.Recurring && !isBarChartShowing
    }

    val hasNext get() = budgetSelectionControllerState.hasNext
    val hasPrevious get() = budgetSelectionControllerState.hasPrevious

    fun showNextPeriod() = budgetSelectionControllerState.next()
    fun showPreviousPeriod() = budgetSelectionControllerState.previous()
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
    start: LocalDateTime,
    end: LocalDateTime,
    context: Context
): String {
    val now = LocalDateTime.now()

    // Inside a budget period
    return if (now.isAfter(start) && now.isBefore(end)) {

        val remainingYears = ChronoUnit.YEARS.between(now, end).toInt()
        val remainingMonths = ChronoUnit.MONTHS.between(now, end).toInt()
        val remainingWeeks = ChronoUnit.WEEKS.between(now, end).toInt()
        val remainingDays = ChronoUnit.DAYS.between(now, end).toInt()

        val remainingAmountForPeriod: Amount = when {
            remainingYears > 1 -> remainingAmount / remainingYears
            remainingMonths > 1 -> remainingAmount / remainingMonths
            remainingWeeks > 1 -> remainingAmount / remainingWeeks
            remainingDays > 1 -> remainingAmount / remainingDays
            else -> remainingAmount
        }

        when {
            hasNoMoneyLeftPerPeriod(remainingAmountForPeriod) -> context.getString(
                R.string.tink_budget_details_amount_left_none_message,
                remainingAmount.formatCurrencyExact()
            )
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
