package se.tink.core.models.onboarding.insights;

import java.util.List;

public class InsightsMortgage extends InsightsItem {

	private List<HistogramBucket> histogramBuckets;
	private double interestRate;

	public List<HistogramBucket> getHistogramBuckets() {
		return histogramBuckets;
	}

	public void setHistogramBuckets(List<HistogramBucket> histogramBuckets) {
		this.histogramBuckets = histogramBuckets;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
}
