package com.tink.moneymanagerui.util;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;

public class PortableChartUtils {


	public static final float RATIO_OF_RANGE_REQUIRED_TO_SHOW = 0.1f;

	public static List<Integer> getSupportLines(int minValue, int maxValue) {

		setupAllowedLabels();
		int range = maxValue - minValue;
		List<Integer> supportLines = new ArrayList<>();

		//Show maxvalue, minvalue and zero
		if (minValue < 0 && maxValue > 0
			&& Math.abs(minValue) / (float) range > RATIO_OF_RANGE_REQUIRED_TO_SHOW
			&& maxValue / (float) range > RATIO_OF_RANGE_REQUIRED_TO_SHOW) {
			supportLines.add(findMinValueLabel(minValue));
			supportLines.add(0);
			supportLines.add(findMaxValueLabel(maxValue));

		}
		//Show positive value and 0
		else if (maxValue > 0 && maxValue / (float) range > RATIO_OF_RANGE_REQUIRED_TO_SHOW) {
			supportLines.add(0);

			Integer maxLabelValue = findMaxValueLabel(maxValue);
			supportLines.add(maxLabelValue / 2);
			supportLines.add(maxLabelValue);

		}
		//Show negative value and 0
		else {
			Integer minLabelValue = findMinValueLabel(minValue);
			supportLines.add(minLabelValue);
			supportLines.add(minLabelValue / 2);
			supportLines.add(0);
		}

		return supportLines;
	}

	private static Integer findMaxValueLabel(int maxValue) {
		Integer previous = -1;
		Integer current = -1;
		for (Integer allowedLabel : allowedPositiveLabels) {
			previous = current;
			current = allowedLabel;
			if (current > maxValue) {
				break;
			}
		}
		//Choose current
		if (current - maxValue < maxValue - previous) {
			return current;
		}
		//Choose previous
		else {
			return previous;
		}
	}

	private static Integer findMinValueLabel(int minValue) {
		return -findMaxValueLabel(-minValue);
	}

	private static List<Integer> allowedPositiveLabels = Lists.newArrayList();
	private static List<Integer> allowedNegativeLabels = Lists.newArrayList();

	private static final int ONE = 1;
	private static final int MAX_LEADING_DIGIT = 6;
	private static final int TEN = 10;

	private static void setupAllowedLabels() {
		//Already setup
		if (!allowedPositiveLabels.isEmpty()) {
			return;
		}

		int currentLabel = ONE;
		int currentLeadingDigit = ONE;
		int tenFactor = 1;
		//Will break on int overflow
		while (currentLabel > 0) {
			allowedPositiveLabels.add(currentLabel);
			allowedNegativeLabels.add(-currentLabel);
			if (currentLeadingDigit < MAX_LEADING_DIGIT) {
				currentLeadingDigit++;
			} else {
				currentLeadingDigit = ONE;
				tenFactor *= TEN;
			}
			currentLabel = currentLeadingDigit * tenFactor;
		}
	}
}
