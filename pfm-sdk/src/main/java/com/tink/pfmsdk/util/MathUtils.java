package com.tink.pfmsdk.util;

import java.util.Collection;

public class MathUtils {

	public static boolean isWithinBounds(int i, Collection collection) {
		if (collection == null || collection.size() == 0) {
			return false;
		}

		return isBetween(i, 0, collection.size() - 1);
	}

	public static boolean isBetween(double value, double lower, double upper) {
		if (lower > upper) {
			throw new IllegalArgumentException("Lower limit cannot be larger than upper limit");
		}

		return (value >= lower && value <= upper);
	}
}
