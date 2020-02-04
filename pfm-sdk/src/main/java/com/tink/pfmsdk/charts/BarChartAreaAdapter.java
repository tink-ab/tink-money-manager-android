package com.tink.pfmsdk.charts;

import com.tink.pfmsdk.charts.models.PeriodBalance;
import java.util.ArrayList;
import java.util.List;

public class BarChartAreaAdapter {

	private List<PeriodBalance> items;
	private double maxValue;
	private double minValue;

	public BarChartAreaAdapter(List<PeriodBalance> items, double minValue, double maxValue) {
		this.items = items;
		if (this.items == null) {
			this.items = new ArrayList<>();
		}

		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public List<PeriodBalance> getItems() {
		return items;
	}

	public int getCount() {
		return items.size();
	}

	public double getMaxValue() {
		return maxValue;
	}

	public double getMinValue() {
		return minValue;
	}

	public double getDifference() {
		return maxValue - minValue;
	}

	public double getTotalValue() {
		int total = 0;
		for (PeriodBalance periodBalance : items) {
			total += periodBalance.getAmount();
		}
		return total;
	}

	public double getMeanValue() {
		return getTotalValue() / items.size();
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}
}
