package se.tink.utils;

import android.icu.text.MessageFormat;
import androidx.annotation.NonNull;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import se.tink.core.models.misc.Period;

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

	public String getMonthFromDateTime(DateTime date) {
    return getMonthFromDateTime(date, false);
	}

	public String getMonthFromDateTime(DateTime date, boolean includeYearIfNotCurrent) {
		String key;
		if (includeYearIfNotCurrent && !date.year().equals(DateTime.now().year())) {
			key = ThreadSafeDateFormat.FORMATTER_MONTH_AND_YEAR_COMPACT;
		} else {
			key = ThreadSafeDateFormat.FORMATTER_MONTHLY_COMPACT;
		}
		return ThreadSafeDateFormat
				.threadSafeDateFormat(key, defaultLocale, timezoneCode)
				.format(date);
	}

	public String getMonthAndYearFromDateTime(DateTime date) {
		return ThreadSafeDateFormat
			.threadSafeDateFormat(ThreadSafeDateFormat.FORMATTER_MONTH_AND_YEAR, defaultLocale, timezoneCode).format(date);
	}

	public String formatDateString(String date) {
		return ThreadSafeDateFormat
			.threadSafeDateFormat(ThreadSafeDateFormat.FORMATTER_DAILY_MONTHLY, defaultLocale, timezoneCode).format(new DateTime(date));
	}

	public String getTodayAsString() {
		DateTime now = DateTime.now();
		return ThreadSafeDateFormat.threadSafeDateFormat(ThreadSafeDateFormat.FORMATTER_DAILY, defaultLocale, timezoneCode)
			.format(now);
	}

	public boolean isToday(String date) {
		DateTime d1 = DateTime.parse(date);
		DateTime d2 = DateTime.now();
		return (d1.year().get() == d2.year().get() && d1.getDayOfYear() == d2.getDayOfYear());
	}

	public DateTime getSalaryDayFromDate(DateTime date) {
		boolean keepFindSalaryDay = true;
		while (keepFindSalaryDay) {
			if (isBusinessDay(date)) {
				keepFindSalaryDay = false;
			} else {
				date = date.minusDays(1);
			}
		}

		return date;
	}

	public boolean isBusinessDay(DateTime date) {
		int dayOfWeek = date.getDayOfWeek();
		if (dayOfWeek <= 5) {
			return true;
		}
		return false;
	}

	public Calendar getCalendar() {
		return Calendar.getInstance(getDefaultTimezone(), getDefaultLocale());
	}

	public Period getPeriod(final Date date, List<Period> periods) {
		return Iterables.find(periods, new Predicate<Period>() {
			@Override
			public boolean apply(Period period) {
				return period.isDateWithin(date);
			}
		}, null);
	}

	public DateTime getToday() {
		DateTime now = DateTime.now();
		DateTime today = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), 0,
			0, 0, 0);
		return today;
	}

	public DateTime convertDate(long date) {
		return new DateTime(date);
	}

	public String formatDateHuman(long value) {
		if (value == 0) {
			return "";
		}
		return formatDateHuman(convertDate(value));
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

	public DateTime getSalaryDateFromDate(DateTime input) {
		int year;
		int month;

		int salaryDayOfMonth = 25; // TODO get this from UserProfile? MONTHLY or MONTHLY_ADJUSTED
		int salarayDayMinusPossibleWeekendDays = salaryDayOfMonth - 2;

		if (input.getDayOfMonth() < salarayDayMinusPossibleWeekendDays) {
			input = input.minusMonths(1);
		}

		year = input.getYear();
		month = input.getMonthOfYear();
		String date = year + "-" + month + "-" + salaryDayOfMonth;

		DateTime wholeDate = new DateTime(date);
		wholeDate = getSalaryDayFromDate(wholeDate);
		return wholeDate;
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

	public String formatYearly(DateTime date, String timezoneCode) {
		return ThreadSafeDateFormat
			.threadSafeDateFormat(
				ThreadSafeDateFormat.FORMATTER_YEARLY,
				defaultLocale,
				timezoneCode
			)
			.format(date);
	}

	private DateTime getDateTimeFromPeriod(Period period) {
		return period.getStop() == null
			? new DateTime(period.toString())
			: period.getStop();
	}

	public String getMonthNameOfDate(DateTime date, boolean includeYearIfNotCurrent) {
		return getMonthNameOfDate(date, includeYearIfNotCurrent, timezoneCode);
	}

    public String getDailyMonthlyYearly(DateTime dateTime) {
        return ThreadSafeDateFormat.threadSafeDateFormat(
                        ThreadSafeDateFormat.FORMATTER_DAILY_MONTHLY_YEARLY,
                        defaultLocale,
                        timezoneCode)
                .format(dateTime);
    }

    public String getDayOfWeek(DateTime dateTime) {
		return ThreadSafeDateFormat
			.threadSafeDateFormat(ThreadSafeDateFormat.FORMATTER_DAY_OF_WEEK, defaultLocale, timezoneCode).format(dateTime);
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

	public ArrayList<Period> getYearMonthStringFor1YearByEndYearMonth(
		Period endPeriod, Map<String, Period> periodMap, boolean paddToYear) {

		ArrayList<Period> periods = Lists.newArrayList();

		Integer year = endPeriod.getYear();
		Integer month = endPeriod.getMonth();

		int y = year;
		Integer m = month;
		for (int i = 0; i < (paddToYear ? 12 : periodMap.size()); i++) {
			String key = createStringYearMonthDay(y, m, null);

			if (!periodMap.containsKey(key)) {
				periods.add(0, createMissingPeriod(y, m));
			} else {
				periods.add(0, periodMap.get(key));
			}

			m--;
			if (m <= 0) {
				y--;
				m = 12;
			}
		}

		return periods;
	}

	private Period createMissingPeriod(int year, int month) {
		Period newPeriod = new Period();
		newPeriod.setYear(year);
		newPeriod.setMonth(month);

		DateTime date = new DateTime(year, month, 1, 0, 0, 0, 0);

		DateTime start = getSalaryDateFromDate(date);
		newPeriod.setStart(start);

		DateTime stop = getSalaryDateFromDate(date.plusMonths(1));
		newPeriod.setStop(stop.minusDays(1));

		return newPeriod;
	}

	public String createStringYearMonthDay(int year, Integer month, Integer day) {
		StringBuilder sb = new StringBuilder();

		// Year
		sb.append(year);

		// Month
		if (month == null) {
			return sb.toString();
		}
		sb.append("-");
		if (month < 10) {
			sb.append("0");
		}
		sb.append(month);

		// Day
		if (day == null) {
			return sb.toString();
		}
		sb.append("-");
		if (day < 10) {
			sb.append("0");
		}
		sb.append(day);

		return sb.toString();
	}

	public String formatDateHumanShort(DateTime date) {

		String pattern = ThreadSafeDateFormat.getDateFormatsMap()
			.get(ThreadSafeDateFormat.FORMATTER_DAILY_MONTHLY);

		DateTimeFormatter dtf = DateTimeFormat
			.forPattern(pattern)
			.withLocale(getDefaultLocale());

		return date.toString(dtf);
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
	public String getOrdinalDayOfMonth(Integer day) {
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
			MessageFormat msgFmt = new MessageFormat("{0,ordinal}", getDefaultLocale());
			return msgFmt.format(new Object[]{day});
		}
		return day.toString(); //TODO
	}

}
