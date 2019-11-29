package se.tink.core.models.misc;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;
import org.joda.time.DateTime;

public class Period implements Comparable<Period>, Parcelable {

	private Integer year;
	private Integer month;
	private Integer week;
	private Integer day;
	private DateTime start;
	private DateTime stop;

	public Period() {
	}

	protected Period(Parcel in) {
		year = in.readInt();
		month = in.readInt();
		week = in.readInt();
		day = in.readInt();
		start = new DateTime(in.readLong());
		stop = new DateTime(in.readLong());
	}

	public static final Creator<Period> CREATOR = new Creator<Period>() {
		@Override
		public Period createFromParcel(Parcel in) {
			return new Period(in);
		}

		@Override
		public Period[] newArray(int size) {
			return new Period[size];
		}
	};

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getWeek() {
		return week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public DateTime getStart() {
		return start;
	}

	public void setStart(DateTime start) {
		this.start = start;
	}

	public DateTime getStop() {
		return stop;
	}

	public void setStop(DateTime stop) {
		this.stop = stop;
	}

	public boolean isYearPeriod() {
		return month == null && week == null;
	}

	public boolean isMonthPeriod() {
		return (month != null && month != 0) && (day == null || day == 0);
	}

	public boolean isWeekPeriod() {
		return week != null && day == null;
	}

	private boolean isDayPeriod() {
		return day != null;
	}

	@Override
	public int compareTo(Period o) {
		if (start.isAfter(o.start)) {
			return -1;
		}
		if (start.isBefore(o.start)) {
			return 1;
		}
		if (stop.isAfter(o.stop)) {
			return -1;
		}
		if (stop.isBefore(o.stop)) {
			return 1;
		}
		return 0;
	}

	public boolean isInPeriod(DateTime timeStamp) {
		//Inclusive inside checks.
		boolean afterStart = timeStamp.isAfter(start) || timeStamp.isEqual(start);
		boolean beforeStop = timeStamp.isBefore(stop) || timeStamp.isEqual(stop);

		return afterStart && beforeStop;
	}

	public static Period createDayPeriod(DateTime timeStamp) {
		timeStamp = timeStamp.minus(timeStamp.getMillisOfDay());
		Period period = new Period();
		period.setYear(timeStamp.getYear());
		period.setMonth(timeStamp.getMonthOfYear());
		period.setDay(timeStamp.getDayOfMonth());
		period.setStart(timeStamp);

		DateTime stopDateTime = timeStamp.plusDays(1);
		stopDateTime = stopDateTime.minus(1);
		period.setStop(stopDateTime);

		return period;
	}

	public static Period createPeriodWithWholeMonths(DateTime earliest, DateTime latest) {
		Period period = new Period();
		period.setStart(earliest.withDayOfMonth(1));
		period.setStop(latest.withDayOfMonth(latest.dayOfMonth().getMaximumValue()));
		return period;
	}

	public static Period createMonthPeriod(DateTime timeStamp) {
		timeStamp = timeStamp.minus(timeStamp.getMillisOfDay());
		timeStamp = timeStamp.minusDays(timeStamp.getDayOfMonth() - 1);

		Period period = new Period();
		period.setYear(timeStamp.getYear());
		period.setMonth(timeStamp.getMonthOfYear());
		period.setStart(timeStamp);

		period.setStop(timeStamp.plusMonths(1).minusMillis(1));

		return period;
	}

	public int compareTimeStamp(long timestamp) {
		DateTime date = new DateTime(timestamp);
		if (date.isBefore(start)) {
			return -1;
		}
		if (date.isAfter(stop)) {
			return 1;
		}
		return 0;
	}

	public boolean isBefore(Period other) {
		return stop.isBefore(other.start);
	}

	public boolean isAfter(Period other) {
		return start.isAfter(other.stop);
	}

	public boolean isDateWithin(DateTime dateTime) {
		return isInPeriod(dateTime);
	}

	@Deprecated
	public boolean isDateWithin(Date date) { // TODO remove date, use DateTime instead.
		return isDateWithin(new DateTime(date.getTime()));
	}

	@SuppressWarnings("SimplifiableIfStatement")
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Period period = (Period) o;

		if (year != null ? !year.equals(period.year) : period.year != null) {
			return false;
		}
		if (month != null ? !month.equals(period.month) : period.month != null) {
			return false;
		}
		if (week != null ? !week.equals(period.week) : period.week != null) {
			return false;
		}
		if (day != null ? !day.equals(period.day) : period.day != null) {
			return false;
		}
		if (!start.equals(period.start)) {
			return false;
		}
		return stop.equals(period.stop);
	}

	@Override
	public int hashCode() {
		int result = year != null ? year.hashCode() : 0;
		result = 31 * result + (month != null ? month.hashCode() : 0);
		result = 31 * result + (week != null ? week.hashCode() : 0);
		result = 31 * result + (day != null ? day.hashCode() : 0);
		result = 31 * result + start.hashCode();
		result = 31 * result + stop.hashCode();
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(year);

		if (month != null && month != 0) {
			sb.append("-");
			if (month < 10) {
				sb.append("0");
			}
			sb.append(month);
		}

		if (day != null && day != 0) {
			sb.append("-");
			if (day < 10) {
				sb.append("0");
			}
			sb.append(day);
		}

		return sb.toString();
	}

	public String toDayString() {
		StringBuilder sb = new StringBuilder();

		sb.append(year);

		if (month != null) {
			sb.append("-");
			if (month < 10) {
				sb.append("0");
			}
			sb.append(month);
		}

		if (day != null) {
			sb.append("-");
			if (day < 10) {
				sb.append("0");
			}
			sb.append(day);
		}

		return sb.toString();
	}

	public String toMonthString() {
		StringBuilder sb = new StringBuilder();

		sb.append(year);

		if (month != null) {
			sb.append("-");
			if (month < 10) {
				sb.append("0");
			}
			sb.append(month);
		}

		return sb.toString();
	}

	public String toYearString() {
		StringBuilder sb = new StringBuilder();
		sb.append(year);

		return sb.toString();
	}

	public Period subtractDays(int i) {
		if (!this.isDayPeriod()) {
			return null;
		}
		DateTime startTime = this.start;
		DateTime newStartTime = startTime.minusDays(i);
		Period newPeriod = createDayPeriod(newStartTime);
		return newPeriod;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		if (year != null) {
			parcel.writeInt(year);
		}
		if (month != null) {
			parcel.writeInt(month);
		}
		if (week != null) {
			parcel.writeInt(week);
		}
		if (day != null) {
			parcel.writeInt(day);
		}
		parcel.writeLong(start.getMillis());
		parcel.writeLong(stop.getMillis());
	}

}
