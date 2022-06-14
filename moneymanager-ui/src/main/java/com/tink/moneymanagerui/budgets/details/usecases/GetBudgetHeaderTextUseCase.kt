package com.tink.moneymanagerui.budgets.details.usecases

import com.tink.model.budget.Budget
import com.tink.moneymanagerui.budgets.details.BudgetStatus
import com.tink.moneymanagerui.budgets.details.BudgetStatus.*
import com.tink.moneymanagerui.budgets.details.model.BudgetSelectionData
import se.tink.commons.extensions.toDateTime
import se.tink.commons.extensions.totalMonths
import java.time.Instant
import java.time.LocalDate
import java.time.Period
import java.time.temporal.ChronoUnit
import kotlin.math.absoluteValue

/**
 * This UseCase deals with the calculation of days and months between 2 dates
 * @param formatter
 * @param successData
 * */
internal class GetBudgetHeaderTextUseCase(
    private val formatter: (BudgetStatus, Int, Int) -> String,
    private val successData: BudgetSelectionData
) {
    operator fun invoke(): String {
        val budgetPeriod = successData.currentSelectedPeriod
        val isCustomDuration = successData.budget.periodicity is Budget.Periodicity.OneOff

        val startOfToday = Instant.now().toDateTime().toLocalDate().atStartOfDay().toLocalDate()
        val budgetStart = budgetPeriod.start.toDateTime().toLocalDate().atStartOfDay().toLocalDate()
        val budgetEnd = budgetPeriod.end.toDateTime().toLocalDate().atStartOfDay().toLocalDate()

        return when {
            budgetPeriod.start.toDateTime().isAfter(Instant.now().toDateTime()) -> {
                val between = Period.between(budgetStart, startOfToday)
                getFormatter(NOT_STARTED, budgetStart, budgetEnd, between, isCustomDuration)
            }
            Instant.now().toDateTime().isAfter(budgetPeriod.end.toDateTime()) -> {
                val between = Period.between(budgetEnd, startOfToday)
                getFormatter(ENDED, budgetStart, budgetEnd, between, isCustomDuration)
            }
            else -> {
                val between = Period.between(budgetEnd, startOfToday)
                getFormatter(ACTIVE, budgetStart, budgetEnd, between, isCustomDuration)
            }
        }
    }

    /**
     * @param startDate
     * @param endDate
     * @param between
     * @param isCustomDuration if user has chosen custom dates
     *
     * @return string value for budget detail title
     * */
    private fun getFormatter(
        status: BudgetStatus,
        startDate: LocalDate,
        endDate: LocalDate,
        between: Period,
        isCustomDuration: Boolean
    ) = formatter(
        status,
        getNumberOfDays(startDate, endDate, between, isCustomDuration),
        getNumberOfMonths(between, isCustomDuration)
    )

    /**
     * Return duration in numbers if budget is set by custom dates otherwise return days which are less than a month
     * @param startDate
     * @param endDate
     * @param between
     * @param isCustomDuration if user has chosen custom dates
     *
     * @return number of days
     * */
    private fun getNumberOfDays(
        startDate: LocalDate,
        endDate: LocalDate,
        between: Period,
        isCustomDuration: Boolean
    ) = if (isCustomDuration) {
        ChronoUnit.DAYS.between(startDate, endDate).toInt()
    } else {
        between.days.absoluteValue
    }

    /**
     * Return 0 in case budget duration is custom dates otherwise return number of months between 2 dates
     * @param between
     * @param isCustomDuration if user has chosen custom dates
     *
     * @return number of months
     * */
    private fun getNumberOfMonths(between: Period, isCustomDuration: Boolean) =
        if (isCustomDuration) 0 else between.totalMonths().absoluteValue
}
