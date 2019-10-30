package se.tink.converter.onboarding.insights;

import java.util.List;
import se.tink.converter.ModelConverter;
import se.tink.core.models.onboarding.insights.HistogramBucket;
import se.tink.core.models.onboarding.insights.InsightsMortgage;
import se.tink.modelConverter.AbstractConverter;


public class InsightsMortgageDTOToInsightsMortgageConverter extends
	AbstractConverter<se.tink.grpc.v1.models.InsightsMortgage, InsightsMortgage> {

	private ModelConverter converter;

	public InsightsMortgageDTOToInsightsMortgageConverter(ModelConverter converter) {
		this.converter = converter;
	}

	@Override
	public InsightsMortgage convert(se.tink.grpc.v1.models.InsightsMortgage source) {
		InsightsMortgage destination = new InsightsMortgage();
		destination.setTitle(source.getTitle());
		destination.setBody(source.getBody());
		destination.setInterestRate(source.getInterestRate());
		List<HistogramBucket> histogramBuckets = converter
			.map(source.getDistributionList(), HistogramBucket.class);
		destination.setHistogramBuckets(histogramBuckets);
		return destination;
	}

	@Override
	public Class<se.tink.grpc.v1.models.InsightsMortgage> getSourceClass() {
		return se.tink.grpc.v1.models.InsightsMortgage.class;
	}

	@Override
	public Class<InsightsMortgage> getDestinationClass() {
		return InsightsMortgage.class;
	}
}
