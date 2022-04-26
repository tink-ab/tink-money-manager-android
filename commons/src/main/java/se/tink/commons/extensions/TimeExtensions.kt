package se.tink.commons.extensions

// TODO: rename to period extension
import com.tink.model.time.Period
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

// TODO: Move to Instantextension
fun Instant.toDateTime(): LocalDateTime = LocalDateTime.ofEpochSecond(
    this.epochSecond,
    0,
    ZoneOffset.UTC
)

fun Period.isInPeriod(dateTime: LocalDateTime): Boolean {
    val instant = Instant.ofEpochMilli(dateTime.toEpochSecond(ZoneOffset.UTC))

    // Inclusive inside checks.
    val afterStart = instant.isAfter(start) || instant == start
    val beforeStop = instant.isBefore(end) || instant == end

    return afterStart && beforeStop
}

fun java.time.Period.totalMonths(): Int = years * 12 + months