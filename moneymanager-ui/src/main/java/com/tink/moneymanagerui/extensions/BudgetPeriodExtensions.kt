package com.tink.moneymanagerui.extensions

import android.content.Context
import com.tink.model.budget.Budget
import com.tink.moneymanagerui.R
import se.tink.commons.extensions.toDateTime
import se.tink.utils.DateUtils

fun Budget.Period.toPeriodChartLabel(
    context: Context,
    dateUtils: DateUtils,
    periodicity: Budget.Periodicity.Recurring
): String {
    return when (periodicity.unit) {
        Budget.Periodicity.Recurring.PeriodUnit.WEEK ->
            context.getString(
                R.string.tink_budget_period_chart_week_label,
                start.toDateTime().weekOfWeekyear().get()
            )

        Budget.Periodicity.Recurring.PeriodUnit.YEAR ->
            periodicity.formattedPeriod(this, dateUtils)

        Budget.Periodicity.Recurring.PeriodUnit.MONTH,
        Budget.Periodicity.Recurring.PeriodUnit.UNKNOWN -> {
            start.toDateTime().getHalfwayUntil(end.toDateTime()).getAbbreviatedMonthName()
        }
    }
}

fun Budget.Period.toHistoricIntervalLabel(
    context: Context,
    dateUtils: DateUtils,
    periodicity: Budget.Periodicity.Recurring
): String {
    return when (periodicity.unit) {
        Budget.Periodicity.Recurring.PeriodUnit.WEEK ->
            context.getString(
                R.string.tink_budget_period_chart_week_label,
                getHalfwayPoint().weekOfWeekyear().get()
            )

        Budget.Periodicity.Recurring.PeriodUnit.YEAR ->
            periodicity.formattedPeriod(this, dateUtils)

        Budget.Periodicity.Recurring.PeriodUnit.MONTH,
        Budget.Periodicity.Recurring.PeriodUnit.UNKNOWN ->
            dateUtils.getMonthAndYearFromDateTime(getHalfwayPoint())
    }
}