package se.tink.converter.settings;

import se.tink.converter.ModelConverter;
import se.tink.core.models.user.NotificationSettings;
import se.tink.grpc.v1.rpc.NotificationSettingsResponse;
import se.tink.modelConverter.AbstractConverter;

public class NotificationSettingsResponseConverter extends
	AbstractConverter<NotificationSettingsResponse, NotificationSettings> {

	private final ModelConverter modelConverter;

	public NotificationSettingsResponseConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public NotificationSettings convert(NotificationSettingsResponse source) {
		return modelConverter.map(source.getNotificationSettings(), NotificationSettings.class);
	}

	@Override
	public Class<NotificationSettingsResponse> getSourceClass() {
		return NotificationSettingsResponse.class;
	}

	@Override
	public Class<NotificationSettings> getDestinationClass() {
		return NotificationSettings.class;
	}
}
