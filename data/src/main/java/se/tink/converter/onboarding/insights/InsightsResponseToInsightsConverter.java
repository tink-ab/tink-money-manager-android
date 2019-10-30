package se.tink.converter.onboarding.insights;

import se.tink.converter.ModelConverter;
import se.tink.core.models.onboarding.insights.Insights;
import se.tink.core.models.onboarding.insights.InsightsCategories;
import se.tink.core.models.onboarding.insights.InsightsDailySpend;
import se.tink.core.models.onboarding.insights.InsightsLeftToSpend;
import se.tink.core.models.onboarding.insights.InsightsMortgage;
import se.tink.core.models.onboarding.insights.InsightsSavings;
import se.tink.grpc.v1.rpc.InsightsResponse;
import se.tink.modelConverter.AbstractConverter;

public class InsightsResponseToInsightsConverter extends
	AbstractConverter<InsightsResponse, Insights> {

	private ModelConverter converter;

	public InsightsResponseToInsightsConverter(ModelConverter converter) {
		this.converter = converter;
	}

	@Override
	public Insights convert(InsightsResponse source) {
		Insights destination = new Insights();
		InsightsCategories categories = converter
			.map(source.getCategories(), InsightsCategories.class);
		InsightsDailySpend dailySpend = converter
			.map(source.getDailySpend(), InsightsDailySpend.class);
		InsightsLeftToSpend leftToSpend = converter
			.map(source.getLeftToSpend(), InsightsLeftToSpend.class);
		InsightsMortgage mortgage = converter.map(source.getMortgage(), InsightsMortgage.class);
		InsightsSavings savings = converter.map(source.getSavings(), InsightsSavings.class);

		if (source.hasCategories()) {
			destination.setCategories(categories);
		}
		if (source.hasDailySpend()) {
			destination.setDailySpend(dailySpend);
		}
		if (source.hasLeftToSpend()) {
			destination.setLeftToSpend(leftToSpend);
		}
		if (source.hasMortgage()) {
			destination.setMortgage(mortgage);
		}
		if (source.hasSavings()) {
			destination.setSavings(savings);
		}

		return destination;
	}

	@Override
	public Class<InsightsResponse> getSourceClass() {
		return InsightsResponse.class;
	}

	@Override
	public Class<Insights> getDestinationClass() {
		return Insights.class;
	}
}
