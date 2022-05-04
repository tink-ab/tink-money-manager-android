package se.tink.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Immutable thread safe date format.
 *
 *
 * Uses the Memento design pattern to easily clone preexisting static thread safe date formatters.
 */
class ThreadSafeDateFormat private constructor(builder: ThreadSafeDateFormatBuilder) {
    private val dateFormat: DateTimeFormatter =
        DateTimeFormatter
            .ofPattern(builder.pattern)
            .withZone(builder.timeZoneId)
            .withLocale(builder.locale)

    /**
     * A builder used to construct [ThreadSafeDateFormat]s
     */
    class ThreadSafeDateFormatBuilder(pattern: String, locale: Locale, timeZoneId: ZoneId) : Cloneable {
        var pattern: String? = null
            private set
        var locale: Locale? = null
            private set
        var timeZoneId: ZoneId? = null
            private set

        init {
            setPattern(pattern)
            setLocale(locale)
            setTimezone(timeZoneId)
        }

        private fun setTimezone(timeZoneId: ZoneId): ThreadSafeDateFormatBuilder {
            this.timeZoneId = timeZoneId
            return this
        }

        fun setPattern(pattern: String): ThreadSafeDateFormatBuilder {
            this.pattern = pattern
            return this
        }

        fun setLocale(locale: Locale): ThreadSafeDateFormatBuilder {
            this.locale = locale
            return this
        }

        public override fun clone(): ThreadSafeDateFormatBuilder {
            return try {
                super.clone() as ThreadSafeDateFormatBuilder
            } catch (e: CloneNotSupportedException) {
                throw RuntimeException("Could not clone. Unexpected.", e)
            }
        }

        fun build(): ThreadSafeDateFormat {
            // Cloning this to make things immutable.
            return ThreadSafeDateFormat(clone())
        }
    }

    constructor(pattern: String?, locale: Locale, timeZoneId: ZoneId) : this(
        ThreadSafeDateFormatBuilder(pattern!!, locale, timeZoneId)
    )

    fun format(date: LocalDateTime?): String {
        return dateFormat.format(date)
    }

    fun format(date: LocalDate?): String {
        return dateFormat.format(date)
    }

    companion object {
        const val FORMATTER_YEARLY = "FORMATTER_YEARLY"
        const val FORMATTER_MONTHLY_COMPACT = "FORMATTER_MONTHLY_COMPACT"
        const val FORMATTER_MONTH_NAME = "FORMATTER_MONTH_NAME"
        const val FORMATTER_DAILY = "FORMATTER_DAILY"
        const val FORMATTER_DAILY_MONTHLY = "FORMATTER_DAILY_MONTHLY"
        const val FORMATTER_DAILY_MONTHLY_YEARLY = "FORMATTER_DAILY_MONTHLY_YEARLY"
        const val FORMATTER_DAY_OF_WEEK_COMPACT = "FORMATTER_DAY_OF_WEEK_COMPACT"
        const val FORMATTER_DAY_OF_WEEK_FULL = "FORMATTER_DAY_OF_WEEK_FULL"
        const val FORMATTER_MONTH_AND_DAY_OF_WEEK = "FORMATTER_MONTH_AND_DAY_OF_WEEK"
        const val FORMATTER_MONTH_AND_DAY = "FORMATTER_MONTH_AND_DAY"
        const val FORMATTER_MONTH_AND_DAY_AND_YEAR = "FORMATTER_MONTH_AND_DAY_AND_YEAR"
        const val FORMATTER_MONTH_AND_DAY_OF_MONTH = "FORMATTER_MONTH_AND_DAY_OF_MONTH"
        const val FORMATTER_MONTH_AND_YEAR = "FORMATTER_MONTH_AND_YEAR"
        const val FORMATTER_MONTH_AND_YEAR_COMPACT = "FORMATTER_MONTH_AND_YEAR_COMPACT"
        const val FORMATTER_DATE_WITH_YEAR = "FORMATTER_DATE_WITH_YEAR"
        @JvmStatic
        var dateFormatsMap: Map<String, String> = HashMap()
        @JvmStatic
        fun threadSafeDateFormat(key: String, locale: Locale, timeZoneId: ZoneId): ThreadSafeDateFormat {
            return ThreadSafeDateFormat(dateFormatsMap[key], locale, timeZoneId)
        }
    }
}
