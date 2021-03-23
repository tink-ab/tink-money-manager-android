package com.tink.moneymanagerui.extensions

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.threeten.bp.Instant


/**
 * Switches the time to what it would be in UTC timezone, but keeps our timezone.
 *
 * This is used to translate backend dates (received in UTC) to frontend dates with same time.
 */
fun DateTime.withUtcTimeRetainZone(): DateTime {
    val ourTimeZone = zone
    return this.withZone(DateTimeZone.UTC).withZoneRetainFields(ourTimeZone)
}

fun DateTime.getHalfwayUntil(end: DateTime): DateTime {
    require(this.isBefore(end)) { "Start has to be before end" }

    val duration = end.millis - this.millis
    return end.minus(duration/2)
}

fun DateTime.getInstant(): Instant = Instant.ofEpochMilli(this.millis)