package com.tink.moneymanagerui.extensions

import se.tink.commons.extensions.toDateTime
import java.time.Instant
import java.time.LocalDateTime

fun Instant.minusMonths(months: Int) = toDateTime().minusMonths(months.toLong()).toInstant()

fun Instant.toEpochMilliSafe(): Long =
    try {
        this.toEpochMilli()
    } catch (ignore: ArithmeticException) {
        val now = LocalDateTime.now()
        if (this.epochSecond < 0) {
            now.getStartOfYear().millsInLocalTimeZone()
        } else {
            now.getEndOfYear().millsInLocalTimeZone()
        }
    }
