package se.tink.utils

import com.tink.model.time.Period
import se.tink.commons.extensions.toDateTime
import se.tink.utils.ThreadSafeDateFormat.Companion.threadSafeDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Locale

/**
 * Uses dates of the class LocalDateTime with a ZoneId for the current user to calcluate the time
 * in the users time zone.
 */
class DateUtils {
    private val defaultTimezoneCode = UTC_TIME_ZONE_CODE

    var timeZoneId = ZoneId.of(defaultTimezoneCode)
    var formatHumanStrings: Map<String, String> = HashMap()
    var locale: Locale = Locale.getDefault()

    private fun setupTimezone(timezoneCode: String) {
        this.timeZoneId = ZoneId.of(defaultTimezoneCode)
    }

    fun isToday(evaluatedDate: LocalDateTime): Boolean {
        val today = LocalDateTime.now()
        return isSameDay(evaluatedDate, today)
    }

    fun isTomorrow(evaluatedDate: LocalDateTime): Boolean {
        val tomorrow = LocalDateTime.now().plusDays(1)
        return isSameDay(evaluatedDate, tomorrow)
    }

    fun isYesterday(evaluatedDate: LocalDateTime): Boolean {
        val yesterday = LocalDateTime.now().minusDays(1)
        return isSameDay(evaluatedDate, yesterday)
    }

    private fun isSameDay(firstDate: LocalDateTime, secondDate: LocalDateTime): Boolean {
        val firstDateWithZone = firstDate.atZone(timeZoneId)
        val secondDateWithZone = secondDate.atZone(timeZoneId)
        return firstDateWithZone.year == secondDateWithZone.year &&
            firstDateWithZone.dayOfYear == secondDateWithZone.dayOfYear
    }

    fun isCurrentYear(evaluatedDate: LocalDateTime): Boolean {
        return evaluatedDate.atZone(timeZoneId).year == LocalDateTime.now(timeZoneId).year
    }

    fun isInPastDays(evaluatedDate: LocalDateTime, numberOfDaysToInclude: Int): Boolean {
        val now = LocalDateTime.now()
        val cutoffTime = now
            .minusDays((numberOfDaysToInclude + 1).toLong())
            .withHour(23)
            .withMinute(59)
            .withSecond(59)
        return evaluatedDate.isAfter(cutoffTime) && evaluatedDate.isBefore(now)
    }

    fun formatDateHuman(date: String): String {
        return formatDateHuman(LocalDateTime.parse(date))
    }

    fun formatDateHuman(date: LocalDateTime): String {
        return when {
            isTomorrow(date) -> {
                formatHumanStrings[KEY_TOMORROW]!!
            }
            isToday(date) -> {
                formatHumanStrings[KEY_TODAY]!!
            }
            isYesterday(date) -> {
                formatHumanStrings[KEY_YESTERDAY]!!
            }
            isInPastDays(date, NUMBER_OF_DAYS_TO_SHOW_ONLY_WEEKDAY) -> {
                upperFirstChar(
                    threadSafeDateFormat(
                        ThreadSafeDateFormat.FORMATTER_DAY_OF_WEEK_FULL,
                        locale,
                        timeZoneId
                    ).format(date)
                )
            }
            isCurrentYear(date) -> {
                upperFirstChar(
                    threadSafeDateFormat(
                        ThreadSafeDateFormat.FORMATTER_MONTH_AND_DAY,
                        locale,
                        timeZoneId
                    ).format(date)
                )
            }
            else -> {
                upperFirstChar(
                    threadSafeDateFormat(
                        ThreadSafeDateFormat.FORMATTER_MONTH_AND_DAY_AND_YEAR,
                        locale,
                        timeZoneId
                    ).format(date)
                )
            }
        }
    }

    private fun upperFirstChar(string: String): String {
        return string.substring(0, 1).uppercase(Locale.getDefault()) + string.substring(1)
    }


    fun getMonthNameAndMaybeYearOfPeriod(period: Period): String {
        // TODO: add zone
        return getMonthNameOfDate(getDateTimeFromPeriod(period), true)
    }

    fun getMonthNameOfPeriod(period: Period): String {
        // TODO: add zone
        return getMonthNameOfDate(getDateTimeFromPeriod(period), false)
    }

    fun formatDateRange(
        start: LocalDateTime,
        end: LocalDateTime,
        includeYearIfNotCurrent: Boolean
    ): String {

        val dateInterval: String
        val isSameDayOfMonth = getDayOfMonth(start) == getDayOfMonth(end)
        val isSameMonth = start.month.value == end.month.value
        val isThisYear = isCurrentYear(start) && isCurrentYear(end)

        dateInterval = if (isToday(start)) {
            val todayText = formatHumanStrings[KEY_TODAY]!!
            val endHumanForm = formatDateHumanShort(end)
            String.format("%s - %s", todayText, endHumanForm)
        } else if (isToday(end)) {
            val startHumanForm = formatDateHumanShort(start)
            val todayText = formatHumanStrings[KEY_TODAY]!!
            String.format("%s - %s", startHumanForm, todayText)
        } else if (isSameDayOfMonth && isSameMonth && isThisYear) {
            val day = getDayOfMonth(start)
            val month = getMonthCompact(start)
            String.format("%s %s", day, month)
        } else if (isSameMonth && isThisYear) {
            val startDay = getDayOfMonth(start)
            val endDay = getDayOfMonth(end)
            val month = getMonthCompact(start)
            String.format("%s - %s %s", startDay, endDay, month)
        } else {
            val startRange = formatRangeBoundary(start, includeYearIfNotCurrent)
            val endRange = formatRangeBoundary(end, includeYearIfNotCurrent)
            String.format("%s - %s", startRange, endRange)
        }
        return dateInterval
    }

//    fun getWeekCountFromDate(start: LocalDateTime): String {
//        return start.weekOfWeekyear().asString
//    }

    private fun formatRangeBoundary(dateTime: LocalDateTime, includeYearIfNotCurrent: Boolean): String {
        val key = if (includeYearIfNotCurrent && !isCurrentYear(dateTime)) {
            ThreadSafeDateFormat.FORMATTER_DAILY_MONTHLY_YEARLY
        } else {
            ThreadSafeDateFormat.FORMATTER_DAILY_MONTHLY
        }
        return threadSafeDateFormat(key, locale, timeZoneId)
            .format(dateTime)
    }

    private fun getDateTimeFromPeriod(period: Period): LocalDateTime {
        // TODO: Core setup
        val periodDuration = period.end.toEpochMilli() - period.start.toEpochMilli()
        val middleOfPeriod = Instant.ofEpochMilli(period.start.toEpochMilli() + periodDuration / 2)
        return middleOfPeriod.toDateTime()
    }

    fun getDayOfWeek(dateTime: LocalDate): String {
        return threadSafeDateFormat(
            ThreadSafeDateFormat.FORMATTER_DAY_OF_WEEK_COMPACT,
            locale,
            timeZoneId)
            .format(dateTime)
    }

    fun getDayOfMonth(dateTime: LocalDateTime): String {
        return threadSafeDateFormat(
            ThreadSafeDateFormat.FORMATTER_DAILY,
            locale,
            timeZoneId)
            .format(dateTime)
    }

    fun getMonthCompact(dateTime: LocalDateTime): String {
        return threadSafeDateFormat(
            ThreadSafeDateFormat.FORMATTER_MONTHLY_COMPACT,
            locale,
            timeZoneId)
            .format(dateTime)
    }

    fun getMonthNameOfDate(date: LocalDateTime, includeYearIfNotCurrent: Boolean): String {
        return if (isCurrentYear(date) || !includeYearIfNotCurrent) {
            threadSafeDateFormat(ThreadSafeDateFormat.FORMATTER_MONTH_NAME, locale, timeZoneId).format(date)
        } else {
            threadSafeDateFormat(ThreadSafeDateFormat.FORMATTER_MONTH_AND_YEAR, locale, timeZoneId)
                .format(date)
        }
    }

    fun formatDateHumanShort(date: LocalDateTime): String {
        return threadSafeDateFormat(
            ThreadSafeDateFormat.FORMATTER_DAILY_MONTHLY,
            locale,
            timeZoneId).
        format(date)
    }

    fun getDateWithYear(dateTime: LocalDateTime): String {
        return upperFirstChar(
            threadSafeDateFormat(ThreadSafeDateFormat.FORMATTER_DATE_WITH_YEAR, locale, timeZoneId)
                .format(dateTime)
        )
    }

    fun getMonthWithDayOfMonth(dateTime: LocalDateTime): String {
        return threadSafeDateFormat(
            ThreadSafeDateFormat.FORMATTER_MONTH_AND_DAY_OF_MONTH,
            locale,
            timeZoneId)
            .format(dateTime)
    }

    fun formatYearly(date: LocalDateTime): String {
        return threadSafeDateFormat(
            ThreadSafeDateFormat.FORMATTER_YEARLY,
            locale,
            timeZoneId
        ).format(date)
    }

    fun getMonthAndYearFromDateTime(date: LocalDateTime): String {
        return threadSafeDateFormat(
            ThreadSafeDateFormat.FORMATTER_MONTH_AND_YEAR,
            locale,
            timeZoneId).format(date)
    }

    companion object {
        const val KEY_TODAY = "today"
        const val KEY_TOMORROW = "tomorrow"
        const val KEY_YESTERDAY = "yesterday"

        const val UTC_TIME_ZONE_CODE = "UTC"

        private const val NUMBER_OF_DAYS_TO_SHOW_ONLY_WEEKDAY = 6

        private val instance: DateUtils = DateUtils()

        fun getInstance(locale: Locale, timezoneCode: String): DateUtils {
            instance.locale = locale
            instance.setupTimezone(timezoneCode)
            return instance
        }

        fun getInstance(): DateUtils {
            return instance
        }
    }
}
