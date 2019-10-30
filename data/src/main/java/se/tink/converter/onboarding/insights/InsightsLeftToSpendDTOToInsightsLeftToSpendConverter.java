package se.tink.converter.onboarding.insights;

import java.util.List;
import se.tink.converter.ModelConverter;
import se.tink.core.models.onboarding.insights.InsightsLeftToSpend;
import se.tink.core.models.onboarding.insights.LeftToSpendByPeriod;
import se.tink.modelConverter.AbstractConverter;

public class InsightsLeftToSpendDTOToInsightsLeftToSpendConverter extends
	AbstractConverter<se.tink.grpc.v1.models.InsightsLeftToSpend, InsightsLeftToSpend> {

	private ModelConverter converter;

	public InsightsLeftToSpendDTOToInsightsLeftToSpendConverter(ModelConverter converter) {
		this.converter = converter;
	}

	@Override
	public InsightsLeftToSpend convert(se.tink.grpc.v1.models.InsightsLeftToSpend source) {
		InsightsLeftToSpend destination = new InsightsLeftToSpend();
		destination.setTitle(source.getTitle());
		destination.setBody(source.getBody());
		List<LeftToSpendByPeriod> leftToSpendByPeriodList = converter
			.map(source.getLeftToSpendByPeriodList(), LeftToSpendByPeriod.class);
		destination.setLeftToSpendByPeriodList(leftToSpendByPeriodList);
		return destination;
	}

	@Override
	public Class<se.tink.grpc.v1.models.InsightsLeftToSpend> getSourceClass() {
		return se.tink.grpc.v1.models.InsightsLeftToSpend.class;
	}

	@Override
	public Class<InsightsLeftToSpend> getDestinationClass() {
		return InsightsLeftToSpend.class;
	}
}
