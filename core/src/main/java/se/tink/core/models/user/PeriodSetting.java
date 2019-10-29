package se.tink.core.models.user;

public class PeriodSetting {

	public int getMonthlyAdjustedDay() {
		return monthlyAdjustedDay;
	}

	public void setMonthlyAdjustedDay(int monthlyAdjustedDay) {
		this.monthlyAdjustedDay = monthlyAdjustedDay;
	}

	public PeriodDateBreakType getPeriodDateBreakType() {
		return periodDateBreakType;
	}

	public void setPeriodDateBreakType(PeriodDateBreakType periodDateBreakType) {
		this.periodDateBreakType = periodDateBreakType;
	}

	public enum PeriodDateBreakType {
		UNKNOWN,
		MONTHLY,
		MONTHLY_ADJUSTED,
	}

	private PeriodDateBreakType periodDateBreakType;
	private int monthlyAdjustedDay;
}
