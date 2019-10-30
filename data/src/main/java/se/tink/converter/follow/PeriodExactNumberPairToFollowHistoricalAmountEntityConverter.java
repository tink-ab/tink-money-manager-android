package se.tink.converter.follow;


import se.tink.converter.ModelConverter;
import se.tink.core.models.follow.PeriodExactNumberPair;
import se.tink.modelConverter.AbstractConverter;
import se.tink.repository.cache.models.ExactNumberEntity;
import se.tink.repository.cache.models.PeriodEntity;
import se.tink.repository.cache.models.follow.FollowHistoricalAmountEntity;

public class PeriodExactNumberPairToFollowHistoricalAmountEntityConverter extends
	AbstractConverter<PeriodExactNumberPair, FollowHistoricalAmountEntity> {

	private ModelConverter modelConverter;

	public PeriodExactNumberPairToFollowHistoricalAmountEntityConverter(
		ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public FollowHistoricalAmountEntity convert(PeriodExactNumberPair source) {
		FollowHistoricalAmountEntity destination = new FollowHistoricalAmountEntity();
		destination.setPeriod(modelConverter.map(source.getPeriod(), PeriodEntity.class));
		destination.setValue(modelConverter.map(source.getValue(), ExactNumberEntity.class));
		return destination;
	}

	@Override
	public Class<PeriodExactNumberPair> getSourceClass() {
		return PeriodExactNumberPair.class;
	}

	@Override
	public Class<FollowHistoricalAmountEntity> getDestinationClass() {
		return FollowHistoricalAmountEntity.class;
	}
}
