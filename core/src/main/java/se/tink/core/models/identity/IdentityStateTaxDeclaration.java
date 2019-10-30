package se.tink.core.models.identity;

import org.joda.time.DateTime;
import se.tink.core.models.misc.Amount;

public class IdentityStateTaxDeclaration {

	private Amount finalTax;
	private Amount incomeByCapital;
	private Amount incomeByService;
	private Amount totalIncome;
	private DateTime registeredDate;
	private int year;

	public void setFinalTax(Amount finalTax) {
		this.finalTax = finalTax;
	}

	public void setIncomeByCapital(Amount incomeByCapital) {
		this.incomeByCapital = incomeByCapital;
	}

	public void setIncomeByService(Amount incomeByService) {
		this.incomeByService = incomeByService;
	}

	public void setTotalIncome(Amount totalIncome) {
		this.totalIncome = totalIncome;
	}

	public void setRegisteredDate(DateTime registeredDate) {
		this.registeredDate = registeredDate;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Amount getFinalTax() {
		return finalTax;
	}

	public Amount getIncomeByCapital() {
		return incomeByCapital;
	}

	public Amount getIncomeByService() {
		return incomeByService;
	}

	public Amount getTotalIncome() {
		return totalIncome;
	}

	public DateTime getRegisteredDate() {
		return registeredDate;
	}

	public int getYear() {
		return year;
	}
}
