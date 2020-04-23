package se.tink.utils;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.ReadablePartial;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Immutable thread safe date format.
 * <p>
 * Uses the Memento design pattern to easily clone preexisting static thread safe date formatters.
 */
public class ThreadSafeDateFormat {

	private static final CharMatcher TRIMMER = CharMatcher.whitespace();

	public static final String FORMATTER_YEARLY = "FORMATTER_YEARLY";
	public static final String FORMATTER_WEEK_YEARLY = "FORMATTER_WEEK_YEARLY";
	public static final String FORMATTER_MONTHLY = "FORMATTER_MONTHLY";
	public static final String FORMATTER_INTEGER_DATE = "FORMATTER_INTEGER_DATE";
	public static final String FORMATTER_INTEGER_DATE_COMPACT = "FORMATTER_INTEGER_DATE_COMPACT";
	public static final String FORMATTER_MONTHLY_ONLY = "FORMATTER_MONTHLY_ONLY";
	public static final String FORMATTER_MONTHLY_COMPACT = "FORMATTER_MONTHLY_COMPACT";
	public static final String FORMATTER_MONTH_NAME = "FORMATTER_MONTH_NAME";
	public static final String FORMATTER_WEEKLY = "FORMATTER_WEEKLY"; // weekyear
	public static final String FORMATTER_DAILY = "FORMATTER_DAILY";
	public static final String FORMATTER_DAILY_COMPACT = "FORMATTER_DAILY_COMPACT";
	public static final String FORMATTER_DAILY_PRETTY = "FORMATTER_DAILY_PRETTY";
	public static final String FORMATTER_DAILY_MONTHLY = "FORMATTER_DAILY_MONTHLY";
	public static final String FORMATTER_DAILY_MONTHLY_YEARLY = "FORMATTER_DAILY_MONTHLY_YEARLY";
	public static final String FORMATTER_MINUTES = "FORMATTER_MINUTES";
	public static final String FORMATTER_SECONDS = "FORMATTER_SECONDS";
	public static final String FORMATTER_SECONDS_DASHES = "FORMATTER_SECONDS_DASHES";
	public static final String FORMATTER_SECONDS_T = "FORMATTER_SECONDS_T";
	public static final String FORMATTER_SECONDS_WITH_TIMEZONE = "FORMATTER_SECONDS_WITH_TIMEZONE";
	public static final String FORMATTER_MILLISECONDS_WITH_TIMEZONE = "FORMATTER_MILLISECONDS_WITH_TIMEZONE";
	public static final String FORMATTER_TIME_MILLIS = "FORMATTER_TIME_MILLIS";
	public static final String FORMATTER_FILENAME_SAFE = "FORMATTER_FILENAME_SAFE";
	public static final String FORMATTER_LOGGING = "FORMATTER_LOGGING";
	public static final String FORMATTER_DAY_OF_WEEK = "FORMATTER_DAY_OF_WEEK";
	public static final String FORMATTER_DAY_OF_WEEK_COMPACT = "FORMATTER_DAY_OF_WEEK_COMPACT";
	public static final String FORMATTER_MONTH_AND_DAY_OF_WEEK = "FORMATTER_MONTH_AND_DAY_OF_WEEK";
	public static final String FORMATTER_MONTH_AND_DAY = "FORMATTER_MONTH_AND_DAY";
	public static final String FORMATTER_MONTH_AND_DAY_AND_YEAR = "FORMATTER_MONTH_AND_DAY_AND_YEAR";
	public static final String FORMATTER_MONTH_AND_YEAR = "FORMATTER_MONTH_AND_YEAR";
	public static final String FORMATTER_MONTH_AND_YEAR_COMPACT = "FORMATTER_MONTH_AND_YEAR_COMPACT";

	private DateTimeFormatter dateFormat;
	private ThreadSafeDateFormatBuilder builder;
	public static Map<String, String> dateFormatsMap = Maps.newHashMap();

	/**
	 * A builder used to construct {@link ThreadSafeDateFormat}s. Mostly used to create variations
	 * of a preexisting {@link ThreadSafeDateFormat} by calling {@link
	 * ThreadSafeDateFormat#toBuilder()}.
	 */
	public static class ThreadSafeDateFormatBuilder implements Cloneable {

		private String pattern;
		private Locale locale;
		private TimeZone timezone;

		public ThreadSafeDateFormatBuilder(String pattern, Locale locale, TimeZone timezone) {
			setPattern(pattern);
			setLocale(locale);
			setTimezone(timezone);
		}

		private ThreadSafeDateFormatBuilder setTimezone(TimeZone timezone) {
			this.timezone = Preconditions.checkNotNull(timezone);
			return this;
		}

		public String getPattern() {
			return pattern;
		}

		public ThreadSafeDateFormatBuilder setPattern(String pattern) {
			this.pattern = Preconditions.checkNotNull(pattern);
			return this;
		}

		public Locale getLocale() {
			return locale;
		}

		public ThreadSafeDateFormatBuilder setLocale(Locale locale) {
			this.locale = Preconditions.checkNotNull(locale);
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

	public Date parse(String string) throws ParseException {
		Date date = null;

		try {
			date = dateFormat.parseDateTime(TRIMMER.trimFrom(string)).toDate();
		} catch (Exception e) {
			throw new ParseException(
				"could not parse date: " + string + " (with pattern: " + builder.getPattern()
					+ ")",
				0);
		}

		return date;
	}

	public ThreadSafeDateFormatBuilder toBuilder() {
		// Cloning since we don't allow internal builder to be mutable.
		return builder.clone();
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
