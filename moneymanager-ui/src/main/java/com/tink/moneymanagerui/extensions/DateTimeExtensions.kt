package com.tink.moneymanagerui.extensions

import com.tink.model.budget.Budget
import com.tink.model.time.Period
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import se.tink.commons.extensions.toDateTime
import java.text.DecimalFormat

/**
 * Switches the time to what it would be in UTC timezone, but keeps our timezone.
 *
 * This is used to translate backend dates (received in UTC) to frontend dates with same time.
 */
fun DateTime.withUtcTimeRetainZone(): DateTime {
    val ourTimeZone = zone
    return this.withZone(DateTimeZone.UTC).withZoneRetainFields(ourTimeZone)
}

fun Period.getHalfwayPoint(): DateTime {
    val duration = end.toEpochMilli() - start.toEpochMilli()
    return start.toDateTime().plus(duration / 2)
}

fun DateTime.getHalfwayUntil(end: DateTime): DateTime {
    require(this.isBefore(end)) { "Start has to be before end" }

    val duration = end.millis - this.millis
    return end.minus(duration / 2)
}

fun DateTime.getInstant(): Instant = Instant.ofEpochMilli(this.millis)

fun DateTime.getAbbreviatedMonthName(): String {
    return toString("MMM")
        .take(3)
}

internal fun DateTime.getStartOfMonth(): Instant =
    dayOfMonth().withMinimumValue()
        .hourOfDay().withMinimumValue()
        .minuteOfHour().withMinimumValue()
        .secondOfMinute().withMinimumValue()
        .millisOfSecond().withMinimumValue()
        .getInstant()

internal fun DateTime.getEndOfMonth(): Instant =
    dayOfMonth().withMaximumValue()
        .hourOfDay().withMaximumValue()
        .minuteOfHour().withMaximumValue()
        .secondOfMinute().withMaximumValue()
        .millisOfSecond().withMaximumValue()
        .getInstant()

internal fun DateTime.toPeriodIdentifier(): String {
    val monthDecimalFormat = DecimalFormat("#00")
    return "$year-${monthDecimalFormat.format(monthOfYear)}"
}

fun DateTime.clearTime(): DateTime =
    this.withHourOfDay(0)
        .withMinuteOfHour(0)
        .withSecondOfMinute(0)
        .withMillisOfSecond(0)

fun Budget.Period.getHalfwayPoint(): DateTime =
    start.toDateTime().getHalfwayUntil(end.toDateTime())

val org.threeten.bp.Period.totalMonths: Int
    get() = years * 12 + months

fun DateTime.toStartOfLocalDate(): LocalDate {
    val startOfToday = this.withUtcTimeRetainZone().clearTime()
    return LocalDate.of(startOfToday.year, startOfToday.monthOfYear, startOfToday.dayOfMonth)
}
