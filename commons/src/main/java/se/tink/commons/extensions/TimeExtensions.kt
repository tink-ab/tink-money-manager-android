package se.tink.commons.extensions

import com.tink.model.time.Period
import org.joda.time.DateTime
import org.threeten.bp.Instant

fun Instant.toDateTime(): DateTime = DateTime(this.toEpochMilli())

fun Period.isInPeriod(dateTime: DateTime): Boolean {

    val instant = Instant.ofEpochMilli(dateTime.millis)

    // Inclusive inside checks.
    val afterStart = instant.isAfter(start) || instant == start
    val beforeStop = instant.isBefore(end) || instant == end

    return afterStart && beforeStop
}
