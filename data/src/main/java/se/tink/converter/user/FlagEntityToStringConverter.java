package se.tink.converter.user;

import se.tink.modelConverter.AbstractConverter;
import se.tink.repository.cache.models.FlagEntity;

public class FlagEntityToStringConverter extends AbstractConverter<FlagEntity, String> {

	@Override
	public String convert(FlagEntity source) {
		return source.getFlag();
	}

	@Override
	public Class<FlagEntity> getSourceClass() {
		return FlagEntity.class;
	}

	@Override
	public Class<String> getDestinationClass() {
		return String.class;
	}
}
