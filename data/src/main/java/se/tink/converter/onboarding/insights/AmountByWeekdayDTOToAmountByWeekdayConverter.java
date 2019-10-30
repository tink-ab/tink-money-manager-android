package se.tink.converter.onboarding.insights;

import se.tink.converter.ModelConverter;
import se.tink.core.models.onboarding.insights.AmountByWeekday;
import se.tink.core.models.misc.Amount;
import se.tink.grpc.v1.models.InsightsDailySpend;
import se.tink.modelConverter.AbstractConverter;

public class AmountByWeekdayDTOToAmountByWeekdayConverter extends
	AbstractConverter<InsightsDailySpend.AmountByWeekday, AmountByWeekday> {

	private final ModelConverter modelConverter;

	public AmountByWeekdayDTOToAmountByWeekdayConverter(ModelConverter converter) {
		modelConverter = converter;
	}

	@Override
	public AmountByWeekday convert(InsightsDailySpend.AmountByWeekday source) {
		AmountByWeekday destination = new AmountByWeekday();
		destination.setAmount(modelConverter.map(source.getAmount(), Amount.class));
		destination.setWeekday(source.getWeekday());
		return destination;
	}

	@Override
	public Class<InsightsDailySpend.AmountByWeekday> getSourceClass() {
		return InsightsDailySpend.AmountByWeekday.class;
	}

	@Override
	public Class<AmountByWeekday> getDestinationClass() {
		return AmountByWeekday.class;
	}
}
