package se.tink.core.models.onboarding.insights;

import java.io.Serializable;
import se.tink.core.models.misc.Amount;

public class AmountByCategory implements Serializable {

	private String categoryCode;
	private Amount amount;

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public Amount getAmount() {
		return amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}
}
