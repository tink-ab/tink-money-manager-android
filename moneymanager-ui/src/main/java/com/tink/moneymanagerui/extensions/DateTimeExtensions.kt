package com.tink.moneymanagerui.extensions

/**
 * Switches the time to what it would be in UTC timezone, but keeps our timezone.
 *
 * This is used to translate backend dates (received in UTC) to frontend dates with same time.

fun ZonedDateTime.withUtcTimeRetainZone(): DateTime {
    val ourTimeZone = zone
    return this.withZone(DateTimeZone.UTC).withZoneRetainFields(ourTimeZone)
}

fun Period.getHalfwayPoint(): LocalDateTime {
    val duration = end.toEpochMilli() - start.toEpochMilli()
    return start.toDateTime().plus(duration / 2)
}

fun LocalDateTime.getHalfwayUntil(end: LocalDateTime): LocalDateTime {
    require(this.isBefore(end)) { "Start has to be before end" }

    val duration = end.millis - this.millis
    return end.minus(duration / 2)
}

fun LocalDateTime.getInstant(): Instant = Instant.ofEpochSecond(this.toEpochSecond(ZoneOffset.UTC))



fun LocalDateTime.weekOfWeekyear(): Int = this.wee.value

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

//val org.threeten.bp.Period.totalMonths: Int
//    get() = years * 12 + months

fun DateTime.toStartOfLocalDate(): LocalDate {
    val startOfToday = this.withUtcTimeRetainZone().clearTime()
    return LocalDate.of(startOfToday.year, startOfToday.monthOfYear, startOfToday.dayOfMonth)
}
 */
