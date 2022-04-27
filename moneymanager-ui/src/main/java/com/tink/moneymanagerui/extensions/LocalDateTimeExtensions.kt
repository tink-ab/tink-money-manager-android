package com.tink.moneymanagerui.extensions

import com.tink.model.budget.Budget
import com.tink.model.time.Period
import se.tink.commons.extensions.toDateTime
import java.text.DecimalFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.Calendar

fun LocalDateTime.monthOfYear(): Int = this.month.value

fun LocalDateTime.toInstant(): Instant = Instant.ofEpochSecond(this.toEpochSecond(ZoneOffset.UTC))

fun LocalDateTime.milli(): Long = toInstant().toEpochMilli()

fun LocalDateTime.weekOfWeekyear(): Int = this.toLocalDate().get(WeekFields.ISO.weekOfWeekBasedYear())

fun Budget.Period.getHalfwayPoint(): LocalDateTime =
    start.toDateTime().getHalfwayUntil(end.toDateTime())

fun Period.getHalfwayPoint(): LocalDateTime =
    start.toDateTime().getHalfwayUntil(end.toDateTime())

fun LocalDateTime.getHalfwayUntil(end: LocalDateTime): LocalDateTime {
    require(this.isBefore(end)) { "Start has to be before end" }

    val duration = end.nano - this.nano
    return end.withNano(duration / 2)
}

fun LocalDateTime.toPeriodIdentifier(): String {
    val monthDecimalFormat = DecimalFormat("#00")
    return "$year-${monthDecimalFormat.format(monthValue)}"
}

fun LocalDateTime.getStartOfMonth(): LocalDateTime = with(TemporalAdjusters.firstDayOfMonth())

fun LocalDateTime.getEndOfMonth(): LocalDateTime = with(TemporalAdjusters.lastDayOfMonth())

fun LocalDateTime.millsInLocalTimeZone(): Long {
    // Calendar.getInstance().timeZone stores the local time zone.
    val zonedLatestEndTime = ZonedDateTime.of(this, ZoneId.of(Calendar.getInstance().timeZone.id))
    return zonedLatestEndTime.toInstant().toEpochMilli()
}


