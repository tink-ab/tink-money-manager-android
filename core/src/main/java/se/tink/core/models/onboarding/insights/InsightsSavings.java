package se.tink.core.models.onboarding.insights;

import se.tink.core.models.misc.Amount;

public class InsightsSavings extends InsightsItem {

	private Amount amount;

	public Amount getAmount() {
		return amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}
}
