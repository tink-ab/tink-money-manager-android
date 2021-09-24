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

	private static final int NUMBER_OF_DAYS_TO_SHOW_ONLY_WEEKDAY = 6;

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
		DateTime evaluatedDate = DateTime.parse(date);
		DateTime currentDate = DateTime.now();
		return (evaluatedDate.year().get() == currentDate.year().get() && evaluatedDate.getDayOfYear() == currentDate.getDayOfYear());
	}

	public boolean isToday(DateTime evaluatedDate) {
		DateTime today = DateTime.now();
		return evaluatedDate.getYear() == today.getYear() && evaluatedDate.getDayOfYear() == today.getDayOfYear();
	}

	public boolean isTomorrow(DateTime evaluatedDate) {
		DateTime tomorrow = DateTime.now().plusDays(1);
		return evaluatedDate.getYear() == tomorrow.getYear() && evaluatedDate.getDayOfYear() == tomorrow.getDayOfYear();
	}

	public boolean isYesterday(DateTime evaluatedDate) {
		DateTime yesterday = DateTime.now().minusDays(1);
		return evaluatedDate.getYear() == yesterday.getYear() && evaluatedDate.getDayOfYear() == yesterday.getDayOfYear();
	}

	public boolean isCurrentYear(DateTime evaluatedDate) {
		return evaluatedDate.getYear() == DateTime.now().getYear();
	}

	public boolean isInPastDays(DateTime evaluatedDate, int numberOfDaysToInclude) {
		DateTime now = DateTime.now();
		DateTime cutoffTime = now
				.minusDays(numberOfDaysToInclude + 1)
				.withHourOfDay(23)
				.withMinuteOfHour(59)
				.withSecondOfMinute(59);
		return evaluatedDate.isAfter(cutoffTime) && evaluatedDate.isBefore(now);
	}

	public String formatDateHuman(@NonNull String date) {
		return formatDateHuman(DateTime.parse(date));
	}

	public String formatDateHuman(@NonNull DateTime date) {
		if (isTomorrow(date)) {
			return formatHumanStrings.get(KEY_TOMORROW);
		} else if (isToday(date)) {
			return formatHumanStrings.get(KEY_TODAY);
		} else if (isYesterday(date)) {
			return formatHumanStrings.get(KEY_YESTERDAY);
		} else if (isInPastDays(date, NUMBER_OF_DAYS_TO_SHOW_ONLY_WEEKDAY)) {
			return upperFirstChar(ThreadSafeDateFormat
				.threadSafeDateFormat(ThreadSafeDateFormat.FORMATTER_DAY_OF_WEEK_FULL, defaultLocale, timezoneCode)
				.format(date));
		} else if (isCurrentYear(date)) {
			return upperFirstChar(ThreadSafeDateFormat
				.threadSafeDateFormat(ThreadSafeDateFormat.FORMATTER_MONTH_AND_DAY, defaultLocale, timezoneCode)
				.format(date));
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

	public String getDateWithYear(DateTime dateTime) {
		return upperFirstChar(ThreadSafeDateFormat
				.threadSafeDateFormat(ThreadSafeDateFormat.FORMATTER_DATE_WITH_YEAR, defaultLocale, timezoneCode)
				.format(dateTime));
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
