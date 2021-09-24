package com.tink.moneymanagerui.extensions

import android.content.Context
import com.tink.model.budget.Budget
import com.tink.model.budget.BudgetPeriod
import com.tink.moneymanagerui.R
import se.tink.commons.extensions.toDateTime
import se.tink.utils.DateUtils

// We want to use UTC to get proper period breaks when formatting.
private const val timezoneCode = "UTC"

fun Budget.Periodicity.OneOff.formattedPeriod(dateUtils: DateUtils): String =
    dateUtils.formatDateRange(start.toDateTime(), end.toDateTime(), timezoneCode, true)

fun Budget.Periodicity.Recurring.formattedPeriod(
    budgetPeriod: BudgetPeriod,
    dateUtils: DateUtils
): String =
    when (unit) {
        Budget.Periodicity.Recurring.PeriodUnit.MONTH ->
            dateUtils.getMonthNameOfDate(
                budgetPeriod.getHalfwayPoint(),
                true,
                timezoneCode)

        Budget.Periodicity.Recurring.PeriodUnit.YEAR ->
            dateUtils.formatYearly(
                budgetPeriod.getHalfwayPoint(),
                timezoneCode
            )

        Budget.Periodicity.Recurring.PeriodUnit.WEEK,
        Budget.Periodicity.Recurring.PeriodUnit.UNKNOWN ->
            dateUtils.formatDateRange(
                budgetPeriod.start.toDateTime(),
                budgetPeriod.end.toDateTime(),
                timezoneCode,
                true
            )
    }
