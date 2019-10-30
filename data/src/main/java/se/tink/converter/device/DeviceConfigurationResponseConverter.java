package se.tink.converter.device;

import java.util.ArrayList;
import se.tink.converter.ModelConverter;
import se.tink.core.models.device.DeviceConfigurationMarket;
import se.tink.core.models.device.DeviceConfigurationResponse;
import se.tink.modelConverter.AbstractConverter;

public class DeviceConfigurationResponseConverter extends
	AbstractConverter<se.tink.grpc.v1.rpc.DeviceConfigurationResponse, DeviceConfigurationResponse> {

	private ModelConverter modelConverter;

	public DeviceConfigurationResponseConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public DeviceConfigurationResponse convert(
		se.tink.grpc.v1.rpc.DeviceConfigurationResponse source) {
		DeviceConfigurationResponse destination = new DeviceConfigurationResponse();
		if (source.getFeatureFlagsList() != null) {
			destination.setFeatureFlags(new ArrayList<>(source.getFeatureFlagsList()));
		}
		if (source.getMarketsList() != null) {
			destination.setMarkets(
				modelConverter
					.map(source.getMarketsList(), DeviceConfigurationMarket.class));
		}
		return destination;
	}

	@Override
	public Class<se.tink.grpc.v1.rpc.DeviceConfigurationResponse> getSourceClass() {
		return se.tink.grpc.v1.rpc.DeviceConfigurationResponse.class;
	}

	@Override
	public Class<DeviceConfigurationResponse> getDestinationClass() {
		return DeviceConfigurationResponse.class;
	}

}
