package se.tink.core.models.onboarding.insights;

import java.io.Serializable;

public class HistogramBucket implements Serializable {

	private double lowerEndpoint;
	private double upperEndpoint;
	private double frequency;

	public double getLowerEndpoint() {
		return lowerEndpoint;
	}

	public void setLowerEndpoint(double lowerEndpoint) {
		this.lowerEndpoint = lowerEndpoint;
	}

	public double getUpperEndpoint() {
		return upperEndpoint;
	}

	public void setUpperEndpoint(double upperEndpoint) {
		this.upperEndpoint = upperEndpoint;
	}

	public double getFrequency() {
		return frequency;
	}

	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}
}
