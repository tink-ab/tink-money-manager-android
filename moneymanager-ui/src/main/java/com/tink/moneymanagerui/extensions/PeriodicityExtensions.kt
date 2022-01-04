package com.tink.moneymanagerui.extensions

import com.tink.model.budget.Budget
import com.tink.model.budget.BudgetPeriod
import se.tink.commons.extensions.toDateTime
import se.tink.utils.DateUtils


fun Budget.Periodicity.OneOff.formattedPeriod(dateUtils: DateUtils): String =
    dateUtils.formatDateRange(start.toDateTime(), end.toDateTime(), true)

fun Budget.Periodicity.Recurring.formattedPeriod(
    budgetPeriod: BudgetPeriod,
    dateUtils: DateUtils
): String =
    when (unit) {
        Budget.Periodicity.Recurring.PeriodUnit.MONTH ->
            dateUtils.getMonthNameOfDate(
                budgetPeriod.getHalfwayPoint(),
                true)

        Budget.Periodicity.Recurring.PeriodUnit.YEAR ->
            dateUtils.formatYearly(
                budgetPeriod.getHalfwayPoint()
            )

        Budget.Periodicity.Recurring.PeriodUnit.WEEK,
        Budget.Periodicity.Recurring.PeriodUnit.UNKNOWN ->
            dateUtils.formatDateRange(
                budgetPeriod.start.toDateTime(),
                budgetPeriod.end.toDateTime(),
                true
            )
    }
