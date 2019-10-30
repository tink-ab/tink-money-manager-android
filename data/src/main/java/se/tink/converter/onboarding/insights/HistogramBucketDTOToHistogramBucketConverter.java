package se.tink.converter.onboarding.insights;

import se.tink.core.models.onboarding.insights.HistogramBucket;
import se.tink.grpc.v1.models.InsightsMortgage;
import se.tink.modelConverter.AbstractConverter;

public class HistogramBucketDTOToHistogramBucketConverter extends
	AbstractConverter<InsightsMortgage.HistogramBucket, HistogramBucket> {

	@Override
	public HistogramBucket convert(InsightsMortgage.HistogramBucket source) {
		HistogramBucket destination = new HistogramBucket();
		destination.setUpperEndpoint(source.getUpperEndpoint());
		destination.setLowerEndpoint(source.getLowerEndpoint());
		destination.setFrequency(source.getFrequency());
		return destination;
	}

	@Override
	public Class<InsightsMortgage.HistogramBucket> getSourceClass() {
		return InsightsMortgage.HistogramBucket.class;
	}

	@Override
	public Class<HistogramBucket> getDestinationClass() {
		return HistogramBucket.class;
	}
}
