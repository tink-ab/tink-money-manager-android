package se.tink.converter.user;

import se.tink.core.models.user.UserConfigurationI18NConfiguration;
import se.tink.grpc.v1.models.UserConfiguration.I18NConfiguration;
import se.tink.modelConverter.AbstractConverter;

public class I18ConfigurationEntityToUserConfigurationI18ConfigurationConverter extends
	AbstractConverter<I18NConfiguration, UserConfigurationI18NConfiguration> {

	@Override
	public UserConfigurationI18NConfiguration convert(I18NConfiguration source) {
		UserConfigurationI18NConfiguration destination = new UserConfigurationI18NConfiguration();
		destination.setCurrencyCode(source.getCurrencyCode());
		destination.setLocaleCode(source.getLocaleCode());
		destination.setMarketCode(source.getMarketCode());
		destination.setTimezoneCode(source.getTimezoneCode());

		return destination;
	}

	@Override
	public Class<I18NConfiguration> getSourceClass() {
		return I18NConfiguration.class;
	}

	@Override
	public Class<UserConfigurationI18NConfiguration> getDestinationClass() {
		return UserConfigurationI18NConfiguration.class;
	}
}
