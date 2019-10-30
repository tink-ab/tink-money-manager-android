package se.tink.converter.misc;


import se.tink.core.models.misc.ExactNumber;
import se.tink.modelConverter.AbstractConverter;
import se.tink.repository.cache.models.ExactNumberEntity;

public class ExactNumberEntityToExactNumberConverter extends
	AbstractConverter<ExactNumberEntity, ExactNumber> {

	@Override
	public ExactNumber convert(ExactNumberEntity source) {
		ExactNumber destination = new ExactNumber(source.getUnscaledValue(), source.getScale());
		return destination;
	}

	@Override
	public Class<ExactNumberEntity> getSourceClass() {
		return ExactNumberEntity.class;
	}

	@Override
	public Class<ExactNumber> getDestinationClass() {
		return ExactNumber.class;
	}
}
