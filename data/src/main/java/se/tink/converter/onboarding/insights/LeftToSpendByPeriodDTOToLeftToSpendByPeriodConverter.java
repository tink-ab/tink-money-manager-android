package se.tink.converter.onboarding.insights;

import se.tink.core.models.onboarding.insights.LeftToSpendByPeriod;
import se.tink.grpc.v1.models.InsightsLeftToSpend;
import se.tink.modelConverter.AbstractConverter;

public class LeftToSpendByPeriodDTOToLeftToSpendByPeriodConverter extends
	AbstractConverter<InsightsLeftToSpend.LeftToSpendByPeriod, LeftToSpendByPeriod> {

	@Override
	public LeftToSpendByPeriod convert(InsightsLeftToSpend.LeftToSpendByPeriod source) {
		LeftToSpendByPeriod destination = new LeftToSpendByPeriod();
		destination.setPercentage(source.getPercentage());
		destination.setPeriod(source.getPeriod());
		return destination;
	}

	@Override
	public Class<InsightsLeftToSpend.LeftToSpendByPeriod> getSourceClass() {
		return InsightsLeftToSpend.LeftToSpendByPeriod.class;
	}

	@Override
	public Class<LeftToSpendByPeriod> getDestinationClass() {
		return LeftToSpendByPeriod.class;
	}
}
