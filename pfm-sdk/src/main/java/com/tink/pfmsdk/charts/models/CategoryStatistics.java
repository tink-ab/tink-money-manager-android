package com.tink.pfmsdk.charts.models;


import se.tink.core.models.misc.Period;

public class CategoryStatistics {

	private CategoryChartData categoryChartData;
	private double total;
	private Period period;

	public CategoryChartData getCategoryChartData() {
		return categoryChartData;
	}

	public void setCategoryChartData(CategoryChartData categoryChartData) {
		this.categoryChartData = categoryChartData;
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
