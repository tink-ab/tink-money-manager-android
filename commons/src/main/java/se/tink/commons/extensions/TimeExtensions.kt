package se.tink.commons.extensions

import com.tink.model.time.DayPeriod
import com.tink.model.time.MonthPeriod
import com.tink.model.time.Period
import com.tink.model.time.YearPeriod
import org.joda.time.DateTime
import org.threeten.bp.Instant
import java.util.Date

fun Instant.toDateTime(): DateTime = DateTime(this.toEpochMilli())

fun Period.startsAfterEndOf(other: Period) = start.isAfter(other.end)

fun Period.isInPeriod(dateTime: DateTime): Boolean {

    val instant = Instant.ofEpochMilli(dateTime.millis)

    //Inclusive inside checks.
    val afterStart = instant.isAfter(start) || instant == start
    val beforeStop = instant.isBefore(end) || instant == end

    return afterStart && beforeStop
}

fun Period.toMonthString(): String? {

    fun monthString(year: Int, month: Int) = if (month < 10) "$year-$month" else "$year-0$month"

    return when (this) {
        is YearPeriod -> null
        is DayPeriod -> monthString(year, monthOfYear)
        is MonthPeriod -> monthString(year, monthOfYear)
    }
}

object PeriodUtil {
    @JvmStatic
    fun isAfter(one: Period, other: Period) = one.startsAfterEndOf(other)

    @JvmStatic
    fun isInPeriod(dateTime: DateTime, period: Period) = period.isInPeriod(dateTime)

    @JvmStatic
    fun getLatestStreamingDate(): Date = DateTime.now().toDate()
}
