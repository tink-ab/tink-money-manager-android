package se.tink.converter.follow;

import se.tink.converter.ModelConverter;
import se.tink.core.models.follow.PeriodExactNumberPair;
import se.tink.modelConverter.AbstractConverter;

public class PeriodExactNumberPairDTOToPeriodExactNumberPairConverter extends
	AbstractConverter<PeriodExactNumberPair, se.tink.grpc.v1.models.PeriodExactNumberPair> {

	private final ModelConverter converter;

	public PeriodExactNumberPairDTOToPeriodExactNumberPairConverter(ModelConverter modelConverter) {
		this.converter = modelConverter;
	}

	@Override
	public se.tink.grpc.v1.models.PeriodExactNumberPair convert(PeriodExactNumberPair source) {
		se.tink.grpc.v1.models.PeriodExactNumberPair.Builder destination = se.tink.grpc.v1.models.PeriodExactNumberPair
			.newBuilder();
		if (source.getValue() != null) {
			destination.setValue(
				converter.map(source.getValue(), se.tink.grpc.v1.models.ExactNumber.class));
		}
		if (source.getPeriod() != null) {
			destination
				.setPeriod(converter.map(source.getPeriod(), se.tink.grpc.v1.models.Period.class));
		}
		return destination.build();
	}

	@Override
	public Class<PeriodExactNumberPair> getSourceClass() {
		return PeriodExactNumberPair.class;
	}

	@Override
	public Class<se.tink.grpc.v1.models.PeriodExactNumberPair> getDestinationClass() {
		return se.tink.grpc.v1.models.PeriodExactNumberPair.class;
	}
}