package se.tink.core.models;

import se.tink.core.models.misc.Period;

public class PeriodBalance {

	private Period period;
	private double amount;

	public PeriodBalance(Period period, double amount) {
		this.period = period;
		this.amount = amount;
	}

	public PeriodBalance() {
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
