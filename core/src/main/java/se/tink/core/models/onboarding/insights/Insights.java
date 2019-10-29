package se.tink.core.models.onboarding.insights;

import java.io.Serializable;

public class Insights implements Serializable {

	private InsightsCategories categories;
	private InsightsDailySpend dailySpend;
	private InsightsLeftToSpend leftToSpend;
	private InsightsMortgage mortgage;
	private InsightsSavings savings;

	public InsightsCategories getCategories() {
		return categories;
	}

	public void setCategories(InsightsCategories categories) {
		this.categories = categories;
	}

	public InsightsDailySpend getDailySpend() {
		return dailySpend;
	}

	public void setDailySpend(InsightsDailySpend dailySpend) {
		this.dailySpend = dailySpend;
	}

	public InsightsLeftToSpend getLeftToSpend() {
		return leftToSpend;
	}

	public void setLeftToSpend(InsightsLeftToSpend leftToSpend) {
		this.leftToSpend = leftToSpend;
	}

	public InsightsMortgage getMortgage() {
		return mortgage;
	}

	public void setMortgage(InsightsMortgage mortgage) {
		this.mortgage = mortgage;
	}

	public InsightsSavings getSavings() {
		return savings;
	}

	public void setSavings(InsightsSavings savings) {
		this.savings = savings;
	}

}
