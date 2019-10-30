package se.tink.converter.pushnotifications;

import se.tink.converter.ModelConverter;
import se.tink.core.models.device.pushnotifications.RegisterPushNotificationTokenRequest;
import se.tink.modelConverter.AbstractConverter;

public class RegisterPushNotificationTokenRequestToSendConverter extends
	AbstractConverter<RegisterPushNotificationTokenRequest, se.tink.grpc.v1.rpc.RegisterPushNotificationTokenRequest> {

	private ModelConverter modelConverter;

	public RegisterPushNotificationTokenRequestToSendConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public se.tink.grpc.v1.rpc.RegisterPushNotificationTokenRequest convert(
		RegisterPushNotificationTokenRequest source) {
		se.tink.grpc.v1.rpc.RegisterPushNotificationTokenRequest.Builder destination = se.tink.grpc.v1.rpc.RegisterPushNotificationTokenRequest
			.newBuilder();
		if (source.getDeviceId() != null) {
			destination.setDeviceId(source.getDeviceId());
		}
		if (source.getNotificationPublicKey() != null) {
			destination.setNotificationPublicKey(source.getNotificationPublicKey());
		}
		if (source.getNotificationToken() != null) {
			destination.setNotificationToken(source.getNotificationToken());
		}
		return destination.build();
	}

	@Override
	public Class<RegisterPushNotificationTokenRequest> getSourceClass() {
		return RegisterPushNotificationTokenRequest.class;
	}

	@Override
	public Class<se.tink.grpc.v1.rpc.RegisterPushNotificationTokenRequest> getDestinationClass() {
		return se.tink.grpc.v1.rpc.RegisterPushNotificationTokenRequest.class;
	}

}
