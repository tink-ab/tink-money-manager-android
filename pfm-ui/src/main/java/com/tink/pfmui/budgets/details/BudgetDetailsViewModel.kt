package com.tink.pfmui.budgets.details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tink.model.budget.Budget
import com.tink.model.budget.BudgetPeriod
import com.tink.model.budget.RecurringPeriodicity
import com.tink.model.misc.Amount
import com.tink.model.misc.ExactNumber
import com.tink.pfmui.R
import com.tink.pfmui.budgets.creation.specification.EXACT_NUMBER_ZERO
import com.tink.pfmui.extensions.periodUnitString
import com.tink.pfmui.extensions.toHistoricIntervalLabel
import com.tink.pfmui.extensions.toPeriodChartLabel
import com.tink.pfmui.util.extensions.formatCurrencyRound
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.Months
import org.joda.time.Weeks
import org.joda.time.Years
import se.tink.android.di.application.ApplicationScoped
import se.tink.commons.extensions.*
import se.tink.utils.DateUtils
import java.math.BigDecimal
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

internal class BudgetDetailsViewModel @Inject constructor(
    private val budgetDetailsDataHolder: BudgetDetailsDataHolder,
    private val dateUtils: DateUtils,
    @ApplicationScoped context: Context
) : ViewModel() {

    val daysLeft: LiveData<String> =
        Transformations.map(budgetDetailsDataHolder.budgetPeriod) { budgetPeriod ->
            daysLeftFormatter(Days.daysBetween(DateTime.now(), budgetPeriod.end.toDateTime()).days)
        }

    private val daysLeftFormatter: (Int) -> String = { daysLeft ->
        val quantityString: String
        if (daysLeft >= 0) {
            quantityString = context.resources.getQuantityString(
                R.plurals.tink_budget_details_days_left,
                daysLeft
            )
            String.format(quantityString, daysLeft)
        } else {
            quantityString = context.resources.getQuantityString(
                R.plurals.tink_budget_details_ended_days_ago,
                daysLeft
            )
            String.format(quantityString, daysLeft.absoluteValue)
        }
    }

    val totalAmount: LiveData<String> =
        Transformations.map(budgetDetailsDataHolder.budgetPeriod) { budgetPeriod ->
            budgetPeriod.budgetAmount.formatCurrencyRound()?.let {
                context.getString(R.string.tink_budget_details_total_amount, it)
            }
        }

    val amountLeft: LiveData<String> = Transformations.map(budgetDetailsDataHolder.budgetPeriod) {
        (it.budgetAmount - it.spentAmount).formatCurrencyRound() ?: ""
    }

    val amountLeftColor: LiveData<Int> =
        Transformations.map(budgetDetailsDataHolder.amountLeft) {
            if (it < 0) {
                context.getColorFromAttr(R.attr.tink_warningColor)
            } else {
               context.getColorFromAttr(R.attr.tink_expensesColor)
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

    private val historicPeriodsList: LiveData<List<BudgetPeriod>> =
        Transformations.map(budgetDetailsDataHolder.budgetPeriodsList) { periodsList ->
            val periodsCount = if (periodsList.size > 12) {
                12
            } else {
                periodsList.size
            }
            periodsList.takeLast(periodsCount)
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

    private val amountSpentInPeriod: LiveData<String> =
        Transformations.map(budgetDetailsDataHolder.budgetPeriod) { budgetPeriod ->
            budgetPeriod.spentAmount.formatCurrencyRound()?.let {
                context.getString(R.string.tink_budget_details_amount_spent, it)
            }
        }

    private val amountSpentOnAverage = MediatorLiveData<String>().apply {
        fun update() {
            if (barChartEnabled.value == true) {
                whenNonNull(
                    historicPeriodsList.value,
                    budgetDetailsDataHolder.budget.value?.periodicity as? RecurringPeriodicity
                ) { periodsList, periodicity ->
                    val averageSpent = periodsList
                        .dropLast(1)
                        .map { it.spentAmount.value.doubleValue() }
                        .average()
                        .roundToInt()
                    val averageSpentAmount = Amount(
                        ExactNumber(BigDecimal(abs(averageSpent))),
                        periodsList.first().spentAmount.currencyCode
                    ).formatCurrencyRound()
                    value = context.getString(
                        R.string.tink_budget_details_spent_average,
                        averageSpentAmount,
                        periodicity.periodUnitString(context)
                    )
                }
            }
        }
        addSource(budgetDetailsDataHolder.budget) { update() }
        addSource(barChartEnabled) { update() }
        addSource(historicPeriodsList) { update() }
    }

    val amountSpent: LiveData<String> = MediatorLiveData<String>().apply {
        fun update() {
            value = if (isBarChartShowing.value == true && amountSpentOnAverage.value != null) {
                amountSpentOnAverage.value
            } else {
                amountSpentInPeriod.value
            }
        }
        addSource(amountSpentInPeriod) { update() }
        addSource(amountSpentOnAverage) { update() }
        addSource(isBarChartShowing) { update() }
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
                    budgetDetailsDataHolder.budget.value?.periodicity as? RecurringPeriodicity
                ) { periodsList, periodicity ->
                    val percentageStr = getBudgetManagedPercentageString(periodsList)
                    value = if (periodicity.unit == Budget.Periodicity.Recurring.PeriodUnit.MONTH) {
                        context.getString(
                            R.string.tink_budget_details_chart_status_message_last_year,
                            percentageStr
                        )
                    } else {
                        val formattedStartPeriodLabel = periodsList.first().toHistoricIntervalLabel(
                            context,
                            dateUtils,
                            periodicity
                        )
                        context.getString(
                            R.string.tink_budget_details_chart_status_message_since,
                            percentageStr,
                            formattedStartPeriodLabel
                        )
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

private fun getBudgetManagedPercentageString(historicPeriodsList: List<BudgetPeriod>): String {
    val budgetManagedCount = historicPeriodsList.count {
        (it.budgetAmount - it.spentAmount).value.isBiggerThan(EXACT_NUMBER_ZERO)
    }
    val budgetManagedPercentage =
        (budgetManagedCount * 100.0f / historicPeriodsList.size).roundToInt()
    return "$budgetManagedPercentage%"
}

private fun composeRemainingBudgetStatusString(
    remainingAmount: Amount,
    start: DateTime,
    end: DateTime,
    context: Context
): String {
    val now = DateTime.now()

    // Inside a budget period
    if (now.isAfter(start) && now.isBefore(end)) {

        val remainingYears = Years.yearsBetween(now, end).years
        val remainingMonths = Months.monthsBetween(now, end).months
        val remainingWeeks = Weeks.weeksBetween(now, end).weeks
        val remainingDays = Days.daysBetween(now, end).days

        val averageAmount: Amount
        val unitString: String?

        when {
            remainingYears > 1 -> {
                averageAmount = remainingAmount / remainingYears.toDouble()
                unitString = context.getString(R.string.tink_budget_period_year)
            }
            remainingMonths > 1 -> {
                averageAmount = remainingAmount / remainingMonths.toDouble()
                unitString = context.getString(R.string.tink_budget_period_month)
            }
            remainingWeeks > 1 -> {
                averageAmount = remainingAmount / remainingWeeks.toDouble()
                unitString = context.getString(R.string.tink_budget_period_week)
            }
            remainingDays > 1 -> {
                averageAmount = remainingAmount / remainingDays.toDouble()
                unitString = context.getString(R.string.tink_budget_period_day)
            }
            else -> {
                averageAmount = remainingAmount
                unitString = null
            }
        }

        val amountString = averageAmount.formatCurrencyRound()
        return unitString?.let {
            context.getString(
                R.string.tink_budget_details_amount_left_message, amountString, it
            )
        } ?: context.getString(R.string.tink_budget_details_amount_left_message_plain, amountString)

    } else if (now.isAfter(end)) { // Budget period in the past
        return context.getString(R.string.tink_budget_details_managed_budget_message)
    } else if (now.isBefore(start)) { // Budget period not started yet
        return context.getString(R.string.tink_budget_details_not_started)
    }
    return context.getString(
        R.string.tink_budget_details_amount_left_message_plain,
        remainingAmount.formatCurrencyRound()
    )
}

internal fun ExactNumber.isZero() = this.toBigDecimal().signum() == 0
internal fun ExactNumber.isBiggerThanOrEqual(other: ExactNumber) = this.toBigDecimal() >= other.toBigDecimal()

