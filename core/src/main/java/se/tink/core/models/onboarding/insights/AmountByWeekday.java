package se.tink.core.models.onboarding.insights;

import java.io.Serializable;
import se.tink.core.models.misc.Amount;

public class AmountByWeekday implements Serializable {

	private String weekday;
	private Amount amount;

	public String getWeekday() {
		return weekday;
	}

	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}

	public Amount getAmount() {
		return amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}
}
