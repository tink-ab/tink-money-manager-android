package se.tink.converter.follow;

import se.tink.converter.ModelConverter;
import se.tink.core.models.follow.PeriodExactNumberPair;
import se.tink.core.models.misc.ExactNumber;
import se.tink.core.models.misc.Period;
import se.tink.modelConverter.AbstractConverter;

public class PeriodExactNumberPairToPeriodExactNumberPairDTOConverter extends
	AbstractConverter<se.tink.grpc.v1.models.PeriodExactNumberPair, PeriodExactNumberPair> {

	private final ModelConverter converter;

	public PeriodExactNumberPairToPeriodExactNumberPairDTOConverter(ModelConverter modelConverter) {
		converter = modelConverter;
	}

	@Override
	public PeriodExactNumberPair convert(se.tink.grpc.v1.models.PeriodExactNumberPair source) {
		PeriodExactNumberPair destination = new PeriodExactNumberPair();
		if (source.getPeriod() != null) {
			destination.setPeriod(converter.map(source.getPeriod(), Period.class));
		}
		if (source.getValue() != null) {
			destination.setValue(converter.map(source.getValue(), ExactNumber.class));
		}
		return destination;
	}

	@Override
	public Class<se.tink.grpc.v1.models.PeriodExactNumberPair> getSourceClass() {
		return se.tink.grpc.v1.models.PeriodExactNumberPair.class;
	}

	@Override
	public Class<PeriodExactNumberPair> getDestinationClass() {
		return PeriodExactNumberPair.class;
	}

}
