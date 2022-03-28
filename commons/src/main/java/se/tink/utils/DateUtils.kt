package se.tink.utils

import com.tink.model.time.Period
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import se.tink.commons.extensions.toDateTime
import se.tink.utils.ThreadSafeDateFormat.Companion.dateFormatsMap
import se.tink.utils.ThreadSafeDateFormat.Companion.threadSafeDateFormat
import java.util.Locale

class DateUtils() {
    private val defaultTimezoneCode = UTC_TIME_ZONE_CODE
    var timezone: DateTimeZone = DateTimeZone.forID(defaultTimezoneCode)
    var formatHumanStrings: Map<String, String> = HashMap()
    var locale: Locale = Locale.getDefault()

    private fun setupTimezone(timezoneCode: String) {
        this.timezone = DateTimeZone.forID(timezoneCode)
    }

    fun isToday(evaluatedDate: DateTime): Boolean {
        val today = DateTime.now()
        return isSameDay(evaluatedDate, today)
    }

    fun isTomorrow(evaluatedDate: DateTime): Boolean {
        val tomorrow = DateTime.now().plusDays(1)
        return isSameDay(evaluatedDate, tomorrow)
    }

    fun isYesterday(evaluatedDate: DateTime): Boolean {
        val yesterday = DateTime.now().minusDays(1)
        return isSameDay(evaluatedDate, yesterday)
    }

    private fun isSameDay(firstDate: DateTime, secondDate: DateTime): Boolean {
        val firstDateWithZone = firstDate.withZone(timezone)
        val secondDateWithZone = secondDate.withZone(timezone)
        return firstDateWithZone.year == secondDateWithZone.year &&
            firstDateWithZone.dayOfYear == secondDateWithZone.dayOfYear
    }

    fun isCurrentYear(evaluatedDate: DateTime): Boolean {
        return evaluatedDate.withZone(timezone).year == DateTime.now(timezone).year
    }

    fun isInPastDays(evaluatedDate: DateTime, numberOfDaysToInclude: Int): Boolean {
        val now = DateTime.now()
        val cutoffTime = now
            .minusDays(numberOfDaysToInclude + 1)
            .withHourOfDay(23)
            .withMinuteOfHour(59)
            .withSecondOfMinute(59)
        return evaluatedDate.isAfter(cutoffTime) && evaluatedDate.isBefore(now)
    }

    fun formatDateHuman(date: String): String {
        return formatDateHuman(DateTime.parse(date))
    }

    fun formatDateHuman(date: DateTime): String {
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
                    date.toString(
                        dateFormatsMap[ThreadSafeDateFormat.FORMATTER_DAY_OF_WEEK_FULL],
                        locale
                    )
                )
            }
            isCurrentYear(date) -> {
                upperFirstChar(
                    date.toString(
                        dateFormatsMap[ThreadSafeDateFormat.FORMATTER_MONTH_AND_DAY],
                        locale
                    )
                )
            }
            else -> {
                upperFirstChar(
                    date.toString(
                        dateFormatsMap[ThreadSafeDateFormat.FORMATTER_MONTH_AND_DAY_AND_YEAR],
                        locale
                    )
                )
            }
        }
    }

    private fun upperFirstChar(string: String): String {
        return string.substring(0, 1).uppercase(Locale.getDefault()) + string.substring(1)
    }

    fun getMonthNameAndMaybeYearOfPeriod(period: Period): String {
        return getMonthNameOfDate(getDateTimeFromPeriod(period), true)
    }

    fun getMonthNameOfPeriod(period: Period): String {
        return getMonthNameOfDate(getDateTimeFromPeriod(period), false)
    }

    fun formatDateRange(
        start: DateTime,
        end: DateTime,
        includeYearIfNotCurrent: Boolean
    ): String {

        val dateInterval: String
        val isSameDayOfMonth = getDayOfMonth(start) == getDayOfMonth(end)
        val isSameMonth = start.monthOfYear() == end.monthOfYear()
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

    fun getWeekCountFromDate(start: DateTime): String {
        return start.weekOfWeekyear().asString
    }

    private fun formatRangeBoundary(dateTime: DateTime, includeYearIfNotCurrent: Boolean): String {
        val key = if (includeYearIfNotCurrent && !isCurrentYear(dateTime)) {
            ThreadSafeDateFormat.FORMATTER_DAILY_MONTHLY_YEARLY
        } else {
            ThreadSafeDateFormat.FORMATTER_DAILY_MONTHLY
        }
        return threadSafeDateFormat(key, locale, timezone)
            .format(dateTime)
    }

    private fun getDateTimeFromPeriod(period: Period): DateTime {
        // TODO: Core setup
        val periodDuration = period.end.toEpochMilli() - period.start.toEpochMilli()
        val middleOfPeriod = Instant.ofEpochMilli(period.start.toEpochMilli() + periodDuration / 2)
        return middleOfPeriod.toDateTime()
    }

    fun getDayOfWeek(dateTime: DateTime): String {
        return threadSafeDateFormat(ThreadSafeDateFormat.FORMATTER_DAY_OF_WEEK_COMPACT, locale, timezone)
            .format(dateTime)
    }

    fun getDayOfMonth(dateTime: DateTime): String {
        return threadSafeDateFormat(ThreadSafeDateFormat.FORMATTER_DAILY, locale, timezone)
            .format(dateTime)
    }

    fun getMonthCompact(dateTime: DateTime): String {
        return threadSafeDateFormat(ThreadSafeDateFormat.FORMATTER_MONTHLY_COMPACT, locale, timezone)
            .format(dateTime)
    }

    fun getDayOfWeek(date: LocalDate): String {
        return getDayOfWeek(DateTime.parse(date.toString()).withZone(timezone))
    }

    fun getMonthNameOfDate(date: DateTime, includeYearIfNotCurrent: Boolean): String {
        return if (isCurrentYear(date) || !includeYearIfNotCurrent) {
            threadSafeDateFormat(ThreadSafeDateFormat.FORMATTER_MONTH_NAME, locale, timezone).format(date)
        } else {
            threadSafeDateFormat(ThreadSafeDateFormat.FORMATTER_MONTH_AND_YEAR, locale, timezone)
                .format(date)
        }
    }

    fun formatDateHumanShort(date: DateTime): String {
        val pattern = dateFormatsMap[ThreadSafeDateFormat.FORMATTER_DAILY_MONTHLY]
        val dtf = DateTimeFormat
            .forPattern(pattern)
            .withLocale(locale)
            .withZone(timezone)
        return date.toString(dtf)
    }

    fun getDateWithYear(dateTime: DateTime): String {
        return upperFirstChar(
            threadSafeDateFormat(ThreadSafeDateFormat.FORMATTER_DATE_WITH_YEAR, locale, timezone)
                .format(dateTime)
        )
    }

    fun getMonthWithDayOfMonth(dateTime: DateTime): String {
        return threadSafeDateFormat(ThreadSafeDateFormat.FORMATTER_MONTH_AND_DAY_OF_MONTH, locale, timezone)
            .format(dateTime)
    }

    fun formatYearly(date: DateTime): String {
        return threadSafeDateFormat(
            ThreadSafeDateFormat.FORMATTER_YEARLY,
            locale,
            timezone
        ).format(date)
    }

    fun getMonthAndYearFromDateTime(date: DateTime): String {
        return threadSafeDateFormat(ThreadSafeDateFormat.FORMATTER_MONTH_AND_YEAR, locale, timezone).format(date)
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
