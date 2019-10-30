package se.tink.converter.follow;


import se.tink.converter.ModelConverter;
import se.tink.core.models.follow.PeriodExactNumberPair;
import se.tink.core.models.misc.ExactNumber;
import se.tink.core.models.misc.Period;
import se.tink.modelConverter.AbstractConverter;
import se.tink.repository.cache.models.follow.FollowHistoricalAmountEntity;

public class FollowHistoricalAmountEntityToPeriodExactNumberPairConverter extends
	AbstractConverter<FollowHistoricalAmountEntity, PeriodExactNumberPair> {

	private ModelConverter modelConverter;

	public FollowHistoricalAmountEntityToPeriodExactNumberPairConverter(
		ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public PeriodExactNumberPair convert(FollowHistoricalAmountEntity source) {
		PeriodExactNumberPair destination = new PeriodExactNumberPair();
		destination.setPeriod(modelConverter.map(source.getPeriod(), Period.class));
		destination.setValue(modelConverter.map(source.getValue(), ExactNumber.class));
		return destination;
	}

	@Override
	public Class<FollowHistoricalAmountEntity> getSourceClass() {
		return FollowHistoricalAmountEntity.class;
	}

	@Override
	public Class<PeriodExactNumberPair> getDestinationClass() {
		return PeriodExactNumberPair.class;
	}
}
