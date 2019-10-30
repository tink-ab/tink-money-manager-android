package se.tink.converter.user;

import se.tink.converter.ModelConverter;
import se.tink.core.models.user.UserConfiguration;
import se.tink.modelConverter.AbstractConverter;

public class UserConfigurationDTOToUserConfigurationConverter extends
	AbstractConverter<UserConfiguration, se.tink.grpc.v1.models.UserConfiguration> {

	private final ModelConverter converter;

	public UserConfigurationDTOToUserConfigurationConverter(ModelConverter modelConverter) {
		this.converter = modelConverter;
	}

	@Override
	public se.tink.grpc.v1.models.UserConfiguration convert(UserConfiguration source) {
		se.tink.grpc.v1.models.UserConfiguration.Builder destination = se.tink.grpc.v1.models.UserConfiguration
			.newBuilder();
		if (source.getI18nConfiguration() != null) {
			destination.setI18NConfiguration(converter.map(source.getI18nConfiguration(),
				se.tink.grpc.v1.models.UserConfiguration.I18NConfiguration.class));
		}
		destination.addAllFlags(converter.map(source.getFlags(), String.class));
		return destination.build();
	}

	@Override
	public Class<UserConfiguration> getSourceClass() {
		return UserConfiguration.class;
	}

	@Override
	public Class<se.tink.grpc.v1.models.UserConfiguration> getDestinationClass() {
		return se.tink.grpc.v1.models.UserConfiguration.class;
	}
}
