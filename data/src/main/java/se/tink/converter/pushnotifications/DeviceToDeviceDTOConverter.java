package se.tink.converter.pushnotifications;

import se.tink.converter.ModelConverter;
import se.tink.core.models.device.pushnotifications.Device;
import se.tink.modelConverter.AbstractConverter;

public class DeviceToDeviceDTOConverter extends
	AbstractConverter<Device, se.tink.grpc.v1.models.Device> {

	private ModelConverter modelConverter;

	public DeviceToDeviceDTOConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public se.tink.grpc.v1.models.Device convert(Device source) {
		se.tink.grpc.v1.models.Device.Builder destination = se.tink.grpc.v1.models.Device.newBuilder();
		if (source.getId() != null) {
			destination.setId(source.getId());
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
	public Class<se.tink.grpc.v1.models.Device> getDestinationClass() {
		return se.tink.grpc.v1.models.Device.class;
	}

	@Override
	public Class<Device> getSourceClass() {
		return Device.class;
	}
}
