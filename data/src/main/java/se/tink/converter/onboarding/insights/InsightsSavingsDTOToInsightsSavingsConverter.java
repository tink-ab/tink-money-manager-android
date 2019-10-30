package se.tink.converter.onboarding.insights;

import se.tink.converter.ModelConverter;
import se.tink.core.models.onboarding.insights.InsightsSavings;
import se.tink.core.models.misc.Amount;
import se.tink.modelConverter.AbstractConverter;

public class InsightsSavingsDTOToInsightsSavingsConverter extends
	AbstractConverter<se.tink.grpc.v1.models.InsightsSavings, InsightsSavings> {

	private ModelConverter modelConverter;

	public InsightsSavingsDTOToInsightsSavingsConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public InsightsSavings convert(se.tink.grpc.v1.models.InsightsSavings source) {
		InsightsSavings destination = new InsightsSavings();
		destination.setTitle(source.getTitle());
		destination.setBody(source.getBody());
		destination.setAmount(modelConverter.map(source.getAmount(), Amount.class));
		return destination;
	}

	@Override
	public Class<se.tink.grpc.v1.models.InsightsSavings> getSourceClass() {
		return se.tink.grpc.v1.models.InsightsSavings.class;
	}

	@Override
	public Class<InsightsSavings> getDestinationClass() {
		return InsightsSavings.class;
	}
}
