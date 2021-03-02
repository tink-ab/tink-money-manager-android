package com.tink.moneymanagerui.charts;

import com.tink.moneymanagerui.charts.models.PeriodBalance;
import java.util.ArrayList;
import java.util.List;

class BarChartAreaAdapter {

	private List<PeriodBalance> items;
	private double maxValue;
	private double minValue;

	BarChartAreaAdapter(List<PeriodBalance> items, double minValue, double maxValue) {
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

	int getCount() {
		return items.size();
	}

	double getMaxValue() {
		return maxValue;
	}

	double getMinValue() {
		return minValue;
	}

	double getDifference() {
		return maxValue - minValue;
	}

	private double getTotalValue() {
		int total = 0;
		for (PeriodBalance periodBalance : items) {
			total += periodBalance.getAmount();
		}
		return total;
	}

	double getMeanValue() {
		return getTotalValue() / items.size();
	}

	void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}
}
