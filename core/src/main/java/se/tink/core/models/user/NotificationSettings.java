package se.tink.core.models.user;

public class NotificationSettings {

	private boolean balance;
	private boolean budget;
	private boolean doubleCharge;
	private boolean income;
	private boolean largeExpense;
	private boolean summaryMonthly;
	private boolean summaryWeekly;
	private boolean transaction;
	private boolean unusualAccount;
	private boolean unusualCategory;
	private boolean einvoices;
	private boolean fraud;

	public boolean isBalance() {
		return balance;
	}

	public void setBalance(boolean balance) {
		this.balance = balance;
	}

	public boolean isBudget() {
		return budget;
	}

	public void setBudget(boolean budget) {
		this.budget = budget;
	}

	public boolean isDoubleCharge() {
		return doubleCharge;
	}

	public void setDoubleCharge(boolean doubleCharge) {
		this.doubleCharge = doubleCharge;
	}

	public boolean isIncome() {
		return income;
	}

	public void setIncome(boolean income) {
		this.income = income;
	}

	public boolean isLargeExpense() {
		return largeExpense;
	}

	public void setLargeExpense(boolean largeExpense) {
		this.largeExpense = largeExpense;
	}

	public boolean isSummaryMonthly() {
		return summaryMonthly;
	}

	public void setSummaryMonthly(boolean summaryMonthly) {
		this.summaryMonthly = summaryMonthly;
	}

	public boolean isSummaryWeekly() {
		return summaryWeekly;
	}

	public void setSummaryWeekly(boolean summaryWeekly) {
		this.summaryWeekly = summaryWeekly;
	}

	public boolean isTransaction() {
		return transaction;
	}

	public void setTransaction(boolean transaction) {
		this.transaction = transaction;
	}

	public boolean isUnusualAccount() {
		return unusualAccount;
	}

	public void setUnusualAccount(boolean unusualAccount) {
		this.unusualAccount = unusualAccount;
	}

	public boolean isUnusualCategory() {
		return unusualCategory;
	}

	public void setUnusualCategory(boolean unusualCategory) {
		this.unusualCategory = unusualCategory;
	}

	public boolean isEinvoices() {
		return einvoices;
	}

	public void setEinvoices(boolean einvoices) {
		this.einvoices = einvoices;
	}

	public boolean isFraud() {
		return fraud;
	}

	public void setFraud(boolean fraud) {
		this.fraud = fraud;
	}

	public boolean isLeftToSpend() {
		return leftToSpend;
	}

	public void setLeftToSpend(boolean leftToSpend) {
		this.leftToSpend = leftToSpend;
	}

	private boolean leftToSpend;
}
