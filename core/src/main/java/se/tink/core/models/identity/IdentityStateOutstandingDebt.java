package se.tink.core.models.identity;

import org.joda.time.DateTime;
import se.tink.core.models.misc.Amount;

public class IdentityStateOutstandingDebt {

	private Amount amount;
	private int number;
	private DateTime registeredDate;

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setRegisteredDate(DateTime registeredDate) {
		this.registeredDate = registeredDate;
	}

	public Amount getAmount() {
		return amount;
	}

	public int getNumber() {
		return number;
	}

	public DateTime getRegisteredDate() {
		return registeredDate;
	}
}
