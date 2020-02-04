package com.tink.pfmsdk.charts;

import com.tink.pfmsdk.charts.models.PeriodBalance;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Manager {

	private static Manager sharedInstance = new Manager();

	public static Manager sharedInstance() {
		return sharedInstance;
	}

	private Manager() { }

	public double[] getMinAndMaxValue(List<PeriodBalance> items) {
		double[] minAndMax = new double[2];

		if (items == null || items.size() == 0) {
			return minAndMax;
		}

		List<PeriodBalance> localCopy = new ArrayList<>(items);
		Collections.sort(localCopy, new Comparator<PeriodBalance>() {
			@Override
			public int compare(PeriodBalance a1, PeriodBalance a2) {
				return Double.compare(a2.getAmount(), a1.getAmount());
			}
		});

		minAndMax[0] = localCopy.get(0).getAmount();
		minAndMax[1] = localCopy.get(localCopy.size() - 1).getAmount();

		return minAndMax;
	}
}
