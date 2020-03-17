package se.tink.converter.misc;


import com.tink.model.misc.ExactNumber;
import se.tink.modelConverter.AbstractConverter;
import se.tink.repository.cache.models.ExactNumberEntity;

public class ExactNumberToExactNumberEntityConverter extends
	AbstractConverter<ExactNumber, ExactNumberEntity> {

	@Override
	public ExactNumberEntity convert(ExactNumber source) {
		ExactNumberEntity destination = new ExactNumberEntity();
		destination.setUnscaledValue(source.getUnscaledValue());
		destination.setScale(source.getScale());
		return destination;
	}

	@Override
	public Class<ExactNumber> getSourceClass() {
		return ExactNumber.class;
	}

	@Override
	public Class<ExactNumberEntity> getDestinationClass() {
		return ExactNumberEntity.class;
	}
}
