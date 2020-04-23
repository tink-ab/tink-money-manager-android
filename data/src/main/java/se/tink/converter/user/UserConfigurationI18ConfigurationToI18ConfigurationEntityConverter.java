package se.tink.converter.user;

import se.tink.core.models.user.UserConfigurationI18NConfiguration;
import se.tink.modelConverter.AbstractConverter;
import se.tink.repository.cache.models.I18ConfigurationEntity;

public class UserConfigurationI18ConfigurationToI18ConfigurationEntityConverter extends
	AbstractConverter<UserConfigurationI18NConfiguration, I18ConfigurationEntity> {

	@Override
	public I18ConfigurationEntity convert(UserConfigurationI18NConfiguration source) {
		I18ConfigurationEntity destination = new I18ConfigurationEntity();
		destination.setCurrencyCode(source.getCurrencyCode());
		destination.setLocaleCode(source.getLocaleCode());
		destination.setMarketCode(source.getMarketCode());
		destination.setTimezoneCode(source.getTimezoneCode());
		return destination;
	}

	@Override
	public Class<UserConfigurationI18NConfiguration> getSourceClass() {
		return UserConfigurationI18NConfiguration.class;
	}

	@Override
	public Class<I18ConfigurationEntity> getDestinationClass() {
		return I18ConfigurationEntity.class;
	}
}
