package se.tink.utils

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.ReadablePartial
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.lang.RuntimeException
import java.util.*

/**
 * Immutable thread safe date format.
 *
 *
 * Uses the Memento design pattern to easily clone preexisting static thread safe date formatters.
 */
class ThreadSafeDateFormat private constructor(builder: ThreadSafeDateFormatBuilder) {
    private val dateFormat: DateTimeFormatter =
        DateTimeFormat
            .forPattern(builder.pattern)
            .withZone(builder.timezone)
            .withLocale(builder.locale)

    /**
     * A builder used to construct [ThreadSafeDateFormat]s
     */
    class ThreadSafeDateFormatBuilder(pattern: String, locale: Locale, timezone: DateTimeZone) : Cloneable {
        var pattern: String? = null
            private set
        var locale: Locale? = null
            private set
        var timezone: DateTimeZone? = null
            private set

        init {
            setPattern(pattern)
            setLocale(locale)
            setTimezone(timezone)
        }

        private fun setTimezone(timezone: DateTimeZone): ThreadSafeDateFormatBuilder {
            this.timezone = timezone
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

    constructor(pattern: String?, locale: Locale, timezone: DateTimeZone) : this(
        ThreadSafeDateFormatBuilder(pattern!!, locale, timezone)
    )

    fun format(date: DateTime?): String {
        return dateFormat.print(date)
    }

    fun format(date: ReadablePartial?): String {
        return dateFormat.print(date)
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
		fun threadSafeDateFormat(key: String, locale: Locale, timezone: DateTimeZone): ThreadSafeDateFormat {
            return ThreadSafeDateFormat(dateFormatsMap[key], locale, timezone)
        }
    }

}