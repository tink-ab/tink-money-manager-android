package se.tink.converter.user;

import se.tink.modelConverter.AbstractConverter;
import se.tink.repository.cache.models.FlagEntity;

public class StringToFlagEntityConverter extends AbstractConverter<String, FlagEntity> {

	@Override
	public FlagEntity convert(String source) {
		FlagEntity destination = new FlagEntity();
		destination.setFlag(source);
		return destination;
	}

	@Override
	public Class<String> getSourceClass() {
		return String.class;
	}

	@Override
	public Class<FlagEntity> getDestinationClass() {
		return FlagEntity.class;
	}
}
