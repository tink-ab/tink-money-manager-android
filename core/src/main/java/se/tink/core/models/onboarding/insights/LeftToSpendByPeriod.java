package se.tink.core.models.onboarding.insights;

import java.io.Serializable;

public class LeftToSpendByPeriod implements Serializable {

	private String period;
	private double percentage;

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
}
