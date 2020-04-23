package se.tink.converter.pushnotifications;

import se.tink.converter.ModelConverter;
import se.tink.core.models.device.pushnotifications.Device;
import se.tink.core.models.device.pushnotifications.RegisterPushNotificationTokenResponse;
import se.tink.modelConverter.AbstractConverter;

public class ResponseToRegisterPushNotificationTokenResponseConverter extends
	AbstractConverter<se.tink.grpc.v1.rpc.RegisterPushNotificationTokenResponse, RegisterPushNotificationTokenResponse> {

	private ModelConverter modelConverter;

	public ResponseToRegisterPushNotificationTokenResponseConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public RegisterPushNotificationTokenResponse convert(
		se.tink.grpc.v1.rpc.RegisterPushNotificationTokenResponse source) {
		RegisterPushNotificationTokenResponse destination = new RegisterPushNotificationTokenResponse();
		if (source.getDevice() != null) {
			destination.setDevice(modelConverter.map(source.getDevice(), Device.class));
		}
		return destination;
	}

	@Override
	public Class<se.tink.grpc.v1.rpc.RegisterPushNotificationTokenResponse> getSourceClass() {
		return se.tink.grpc.v1.rpc.RegisterPushNotificationTokenResponse.class;
	}

	@Override
	public Class<RegisterPushNotificationTokenResponse> getDestinationClass() {
		return RegisterPushNotificationTokenResponse.class;
	}

}
