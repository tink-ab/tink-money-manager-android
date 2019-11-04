package se.tink.piechart;


import se.tink.core.models.misc.Period;

public class CategoryStatistics {

	private Category category;
	private double total;
	private Period period;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

}
