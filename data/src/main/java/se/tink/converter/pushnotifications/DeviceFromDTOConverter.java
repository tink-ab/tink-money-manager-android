package se.tink.converter.pushnotifications;

import se.tink.converter.ModelConverter;
import se.tink.core.models.device.pushnotifications.Device;
import se.tink.modelConverter.AbstractConverter;

public class DeviceFromDTOConverter extends
	AbstractConverter<se.tink.grpc.v1.models.Device, Device> {

	private ModelConverter modelConverter;

	public DeviceFromDTOConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public Device convert(se.tink.grpc.v1.models.Device source) {
		Device destination = new Device();
		if (source.getId() != null) {
			destination.setId(source.getId());
		}
		if (source.getNotificationPublicKey() != null) {
			destination.setNotificationPublicKey(source.getNotificationPublicKey());
		}
		if (source.getNotificationToken() != null) {
			destination.setNotificationToken(source.getNotificationToken());
		}
		return destination;
	}

	@Override
	public Class<Device> getDestinationClass() {
		return Device.class;
	}

	@Override
	public Class<se.tink.grpc.v1.models.Device> getSourceClass() {
		return se.tink.grpc.v1.models.Device.class;
	}
}
