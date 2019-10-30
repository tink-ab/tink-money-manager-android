package se.tink.core.models.identity;

import org.joda.time.DateTime;
import se.tink.core.models.misc.Amount;

public class IdentityStateRecordOfNonPayment {

	private Amount amount;
	private String name;
	private DateTime registeredDate;

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	public Amount getAmount() {
		return amount;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRegisteredDate(DateTime registeredDate) {
		this.registeredDate = registeredDate;
	}

	public String getName() {
		return name;
	}

	public DateTime getRegisteredDate() {
		return registeredDate;
	}
}
