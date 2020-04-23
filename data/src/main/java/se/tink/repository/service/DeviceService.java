package se.tink.repository.service;

import se.tink.core.models.device.DeviceConfigurationResponse;
import se.tink.core.models.device.pushnotifications.RegisterPushNotificationTokenResponse;
import se.tink.core.models.origin.SetOriginRequest;
import se.tink.repository.MutationHandler;

public interface DeviceService extends TinkService {

	void registerPushNotificationToken(String deviceId, String token, String notificationPublicKey,
		final MutationHandler<RegisterPushNotificationTokenResponse> handler);

	void getDeviceConfiguration(String deviceId, String marketCode,
		final MutationHandler<DeviceConfigurationResponse> handler);

	void setOrigin(SetOriginRequest setOriginRequest, final MutationHandler<Void> handler);
}
