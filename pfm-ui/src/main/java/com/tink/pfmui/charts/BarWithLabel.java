package com.tink.pfmui.charts;

import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;

class BarWithLabel {

	private GradientDrawable Gradient;
	private Point LabelPoint;
	private String LabelText;

	BarWithLabel(GradientDrawable gradient, Point labelPoint, String labelText) {
		Gradient = gradient;
		LabelPoint = labelPoint;
		LabelText = labelText;
	}

	GradientDrawable getGradient() {
		return Gradient;
	}

	Point getLabelPoint() {
		return LabelPoint;
	}

	String getLabelText() {
		return LabelText;
	}

}
