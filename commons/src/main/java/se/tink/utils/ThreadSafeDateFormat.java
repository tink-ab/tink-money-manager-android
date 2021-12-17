package se.tink.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.ReadablePartial;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import androidx.annotation.NonNull;

/**
 * Immutable thread safe date format.
 * <p>
 * Uses the Memento design pattern to easily clone preexisting static thread safe date formatters.
 */
public class ThreadSafeDateFormat {

	public static final String FORMATTER_YEARLY = "FORMATTER_YEARLY";
	public static final String FORMATTER_MONTHLY_COMPACT = "FORMATTER_MONTHLY_COMPACT";
	public static final String FORMATTER_MONTH_NAME = "FORMATTER_MONTH_NAME";
	public static final String FORMATTER_DAILY = "FORMATTER_DAILY";
	public static final String FORMATTER_DAILY_MONTHLY = "FORMATTER_DAILY_MONTHLY";
	public static final String FORMATTER_DAILY_MONTHLY_YEARLY = "FORMATTER_DAILY_MONTHLY_YEARLY";
	public static final String FORMATTER_DAY_OF_WEEK_COMPACT = "FORMATTER_DAY_OF_WEEK_COMPACT";
	public static final String FORMATTER_DAY_OF_WEEK_FULL = "FORMATTER_DAY_OF_WEEK_FULL";
	public static final String FORMATTER_MONTH_AND_DAY_OF_WEEK = "FORMATTER_MONTH_AND_DAY_OF_WEEK";
	public static final String FORMATTER_MONTH_AND_DAY = "FORMATTER_MONTH_AND_DAY";
	public static final String FORMATTER_MONTH_AND_DAY_AND_YEAR = "FORMATTER_MONTH_AND_DAY_AND_YEAR";
	public static final String FORMATTER_MONTH_AND_DAY_OF_MONTH = "FORMATTER_MONTH_AND_DAY_OF_MONTH";
	public static final String FORMATTER_MONTH_AND_YEAR = "FORMATTER_MONTH_AND_YEAR";
	public static final String FORMATTER_MONTH_AND_YEAR_COMPACT = "FORMATTER_MONTH_AND_YEAR_COMPACT";
	public static final String FORMATTER_DATE_WITH_YEAR = "FORMATTER_DATE_WITH_YEAR";

	private DateTimeFormatter dateFormat;
	private ThreadSafeDateFormatBuilder builder;
	public static Map<String, String> dateFormatsMap = new HashMap<>();

	/**
	 * A builder used to construct {@link ThreadSafeDateFormat}s
	 */
	public static class ThreadSafeDateFormatBuilder implements Cloneable {

		private String pattern;
		private Locale locale;
		private TimeZone timezone;

		public ThreadSafeDateFormatBuilder(@NonNull String pattern, @NonNull Locale locale, @NonNull TimeZone timezone) {
			setPattern(pattern);
			setLocale(locale);
			setTimezone(timezone);
		}

		private ThreadSafeDateFormatBuilder setTimezone(@NonNull TimeZone timezone) {
			this.timezone = timezone;
			return this;
		}

		public String getPattern() {
			return pattern;
		}

		public ThreadSafeDateFormatBuilder setPattern(@NonNull String pattern) {
			this.pattern = pattern;
			return this;
		}

		public Locale getLocale() {
			return locale;
		}

		public ThreadSafeDateFormatBuilder setLocale(@NonNull Locale locale) {
			this.locale = locale;
			return this;
		}

		@Override
		public ThreadSafeDateFormatBuilder clone() {
			try {
				return (ThreadSafeDateFormatBuilder) super.clone();
			} catch (CloneNotSupportedException e) {
				throw new RuntimeException("Could not clone. Unexpected.", e);
			}
		}

		public ThreadSafeDateFormat build() {
			// Cloning this to make things immutable.
			return new ThreadSafeDateFormat(this.clone());
		}

		public TimeZone getTimezone() {
			return timezone;
		}
	}

	public ThreadSafeDateFormat(String pattern, Locale locale, String timezoneCode) {
		this(new ThreadSafeDateFormatBuilder(pattern, locale,
			DateUtils.getInstance(locale, timezoneCode).getDefaultTimezone()));
	}

	private ThreadSafeDateFormat(ThreadSafeDateFormatBuilder builder) {
		this.builder = builder;
		this.dateFormat = DateTimeFormat.forPattern(builder.getPattern())
			.withZone(DateTimeZone.forTimeZone(builder.getTimezone()))
			.withLocale(builder.getLocale());
	}

	public String format(DateTime date) {
		return dateFormat.print(date);
	}

	public String format(ReadablePartial date) {
		return dateFormat.print(date);
	}

	public static Map<String, String> getDateFormatsMap() {
		return dateFormatsMap;
	}

	public static void setDateFormatsMap(Map<String, String> map) {
		dateFormatsMap = map;
	}

	public static ThreadSafeDateFormat threadSafeDateFormat(String key, Locale locale, String timezoneCode) {
		return new ThreadSafeDateFormat(dateFormatsMap.get(key), locale, timezoneCode);
	}
}
