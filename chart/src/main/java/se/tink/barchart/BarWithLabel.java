package se.tink.barchart;

import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;

public class BarWithLabel {

	private GradientDrawable Gradient;
	private Point LabelPoint;
	private String LabelText;

	public BarWithLabel(GradientDrawable gradient, Point labelPoint, String labelText) {
		Gradient = gradient;
		LabelPoint = labelPoint;
		LabelText = labelText;
	}

	public GradientDrawable getGradient() {
		return Gradient;
	}

	public Point getLabelPoint() {
		return LabelPoint;
	}

	public String getLabelText() {
		return LabelText;
	}

}
