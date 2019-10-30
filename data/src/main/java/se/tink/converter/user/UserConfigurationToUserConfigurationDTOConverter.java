package se.tink.converter.user;

import se.tink.converter.ModelConverter;
import se.tink.core.models.user.UserConfiguration;
import se.tink.core.models.user.UserConfigurationI18NConfiguration;
import se.tink.modelConverter.AbstractConverter;

public class UserConfigurationToUserConfigurationDTOConverter extends
	AbstractConverter<se.tink.grpc.v1.models.UserConfiguration, UserConfiguration> {

	private final ModelConverter converter;

	public UserConfigurationToUserConfigurationDTOConverter(ModelConverter modelConverter) {
		converter = modelConverter;
	}

	@Override
	public UserConfiguration convert(se.tink.grpc.v1.models.UserConfiguration source) {
		UserConfiguration destination = new UserConfiguration();
		if (source.getI18NConfiguration() != null) {
			destination.setI18nConfiguration(converter
				.map(source.getI18NConfiguration(), UserConfigurationI18NConfiguration.class));
		}
		if (source.getFlagsCount() > 0) {
			destination.setFlags(
				source.getFlagsList());
		}
		return destination;
	}

	@Override
	public Class<se.tink.grpc.v1.models.UserConfiguration> getSourceClass() {
		return se.tink.grpc.v1.models.UserConfiguration.class;
	}

	@Override
	public Class<UserConfiguration> getDestinationClass() {
		return UserConfiguration.class;
	}

}
