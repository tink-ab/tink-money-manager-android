package se.tink.utils;

import com.google.common.collect.Maps;
import com.tink.model.time.Period;

import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;

import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import se.tink.commons.extensions.TimeExtensionsKt;

public class DateUtils {
	public static final String KEY_TODAY = "today";
	public static final String KEY_TOMORROW = "tomorrow";
	public static final String KEY_YESTERDAY = "yesterday";

	private Locale defaultLocale;
	private TimeZone defaultTimezone;
	private String timezoneCode;

	private Map<String, String> formatHumanStrings = Maps.newHashMap();

	private static DateUtils instance;

	public static DateUtils getInstance(Locale locale, String timezoneCode) {
		if (instance == null) {
			instance = new DateUtils();
		}
		instance.setupLocale(locale);
		instance.setupTimezoneCode(timezoneCode);

		return instance;
	}

	private void setupTimezoneCode(String timezoneCode) {
		this.timezoneCode = timezoneCode;
		defaultTimezone = TimeZone.getTimeZone(timezoneCode);
	}

	private void setupLocale(Locale upLocale) {
		this.defaultLocale = upLocale;
	}

	public boolean isToday(String date) {
		DateTime d1 = DateTime.parse(date);
		DateTime d2 = DateTime.now();
		return (d1.year().get() == d2.year().get() && d1.getDayOfYear() == d2.getDayOfYear());
	}

	public String formatDateHuman(String date) {
		return formatDateHuman(DateTime.parse(date));
	}

	public String formatDateHuman(@NonNull DateTime date) {
		DateTime now = DateTime.now();
		DateTime today = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), 0,
			0);

		long value = date.getMillis();

		if (date.isBefore(today)) {

			if (!today.minusDays(1).isAfter(value)) {
				return formatHumanStrings.get(KEY_YESTERDAY);
			} else if (!today.minusMonths(1).isAfter(value)) {
				return upperFirstChar(ThreadSafeDateFormat
					.threadSafeDateFormat(ThreadSafeDateFormat.FORMATTER_MONTH_AND_DAY_OF_WEEK, defaultLocale, timezoneCode)
					.format(date));
			}
		} else {

			if (today.plusDays(1).isAfter(value)) {
				return formatHumanStrings.get(KEY_TODAY);
			} else if (today.plusDays(2).isAfter(value)) {
				return formatHumanStrings.get(KEY_TOMORROW);
			} else if (today.plusMonths(1).isAfter(value)) {
				return ThreadSafeDateFormat
					.threadSafeDateFormat(ThreadSafeDateFormat.FORMATTER_MONTH_AND_DAY_OF_WEEK, defaultLocale, timezoneCode)
					.format(date);
			}
		}

		if (today.getYear() == date.getYear()) {
			return ThreadSafeDateFormat
				.threadSafeDateFormat(ThreadSafeDateFormat.FORMATTER_MONTH_AND_DAY, defaultLocale, timezoneCode)
				.format(date);
		} else {
			return upperFirstChar(ThreadSafeDateFormat
				.threadSafeDateFormat(ThreadSafeDateFormat.FORMATTER_MONTH_AND_DAY_AND_YEAR, defaultLocale, timezoneCode)
				.format(date));
		}
	}

	private String upperFirstChar(String string) {
		return string.substring(0, 1).toUpperCase() + string.substring(1);
	}

	public String getMonthNameAndMaybeYearOfPeriod(Period period) {
		return getMonthNameOfDate(getDateTimeFromPeriod(period), true);
	}

	public String getMonthNameOfPeriod(Period period) {
		return getMonthNameOfDate(getDateTimeFromPeriod(period), false);
	}

	public String formatDateRange(DateTime start, DateTime end, String timezoneCode,
		boolean includeYearIfNotCurrent) {
		String rangeStart = formatRangeBoundary(start, timezoneCode, includeYearIfNotCurrent);
		String rangeEnd = formatRangeBoundary(end, timezoneCode, includeYearIfNotCurrent);
		return String.format("%s - %s", rangeStart, rangeEnd);
	}

	public String getWeekCountFromDate(DateTime start) {
		return start.weekOfWeekyear().getAsString();
	}

	private String formatRangeBoundary(DateTime dateTime, String timezoneCode, boolean includeYearIfNotCurrent) {
		String key;
		if (includeYearIfNotCurrent && !dateTime.year().equals(DateTime.now().year())) {
			key = ThreadSafeDateFormat.FORMATTER_DAILY_MONTHLY_YEARLY;
		} else {
			key = ThreadSafeDateFormat.FORMATTER_DAILY_MONTHLY;
		}
		return ThreadSafeDateFormat
				.threadSafeDateFormat(key, defaultLocale, timezoneCode)
				.format(dateTime);
	}

	private DateTime getDateTimeFromPeriod(Period period) {
		//TODO: Core setup
		final long periodDuration = period.getEnd().toEpochMilli() - period.getStart().toEpochMilli();
		final Instant middleOfPeriod = Instant.ofEpochMilli(period.getStart().toEpochMilli() + periodDuration / 2);
		return TimeExtensionsKt.toDateTime(middleOfPeriod);
	}

	public String getMonthNameOfDate(DateTime date, boolean includeYearIfNotCurrent) {
		return getMonthNameOfDate(date, includeYearIfNotCurrent, timezoneCode);
	}

    public String getDayOfWeek(DateTime dateTime) {
		return ThreadSafeDateFormat
			.threadSafeDateFormat(ThreadSafeDateFormat.FORMATTER_DAY_OF_WEEK_COMPACT, defaultLocale, timezoneCode).format(dateTime);
	}

	public String getDayOfWeek(@NotNull LocalDate date) {
		return getDayOfWeek(DateTime.parse(date.toString()));
	}

	public String getMonthNameOfDate(DateTime date, boolean includeYearIfNotCurrent, String timezoneCode) {
		if (date.getYear() == DateTime.now().getYear() || !includeYearIfNotCurrent) {
			return ThreadSafeDateFormat
				.threadSafeDateFormat(ThreadSafeDateFormat.FORMATTER_MONTH_NAME, defaultLocale, timezoneCode).format(date);
		} else {
			return ThreadSafeDateFormat
				.threadSafeDateFormat(ThreadSafeDateFormat.FORMATTER_MONTH_AND_YEAR, defaultLocale, timezoneCode)
				.format(date);
		}
	}

	public String formatDateHumanShort(DateTime date) {

		String pattern = ThreadSafeDateFormat.getDateFormatsMap()
			.get(ThreadSafeDateFormat.FORMATTER_DAILY_MONTHLY);

		DateTimeFormatter dtf = DateTimeFormat
			.forPattern(pattern)
			.withLocale(getDefaultLocale());

		return date.toString(dtf);
	}

	public String getDailyMonthlyYearly(DateTime dateTime) {
		return ThreadSafeDateFormat.threadSafeDateFormat(
			ThreadSafeDateFormat.FORMATTER_DAILY_MONTHLY_YEARLY,
			defaultLocale,
			timezoneCode)
			.format(dateTime);
	}

	public String formatYearly(DateTime date, String timezoneCode) {
		return ThreadSafeDateFormat
				.threadSafeDateFormat(
						ThreadSafeDateFormat.FORMATTER_YEARLY,
						defaultLocale,
						timezoneCode
				)
				.format(date);
	}

	public String getMonthAndYearFromDateTime(DateTime date) {
		return ThreadSafeDateFormat
				.threadSafeDateFormat(ThreadSafeDateFormat.FORMATTER_MONTH_AND_YEAR, defaultLocale, timezoneCode).format(date);
	}

	public Locale getDefaultLocale() {
		return defaultLocale;
	}

	public TimeZone getDefaultTimezone() {
		return defaultTimezone;
	}

	public void setFormatHumanStrings(Map<String, String> formatHumanStrings) {
		this.formatHumanStrings = formatHumanStrings;
	}

	public Map<String, String> getFormatHumanStrings() {
		return formatHumanStrings;
	}
}
