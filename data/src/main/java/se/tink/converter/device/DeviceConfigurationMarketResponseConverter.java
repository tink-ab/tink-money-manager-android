package se.tink.converter.device;

import java.util.List;
import se.tink.converter.ModelConverter;
import se.tink.core.models.device.AuthenticationMethod;
import se.tink.core.models.device.DeviceConfigurationMarket;
import se.tink.core.models.device.DeviceConfigurationMarket.Builder;
import se.tink.core.models.device.MarketStatus;
import se.tink.grpc.v1.rpc.DeviceConfigurationResponse;
import se.tink.modelConverter.AbstractConverter;

public class DeviceConfigurationMarketResponseConverter extends
	AbstractConverter<DeviceConfigurationResponse.DeviceConfigurationMarket, DeviceConfigurationMarket> {

	private ModelConverter modelConverter;

	public DeviceConfigurationMarketResponseConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public DeviceConfigurationMarket convert(
		DeviceConfigurationResponse.DeviceConfigurationMarket source) {

		return new Builder()
			.setMarket(source.getMarketCode())
			.setLabel(source.getLabel())
			.setTermsAndConditionsLink(source.getLinkToTermsOfServicePage())
			.setSuggested(source.getSuggested())
			.setLoginMethods(getLoginMethods(source))
			.setRegisterMethods(getRegisterMethods(source))
			.setStatus(MarketStatus.getMarketStatusByKey(source.getStatusValue()))
			.createDeviceConfigurationMarket();
	}

	@Override
	public Class<DeviceConfigurationResponse.DeviceConfigurationMarket> getSourceClass() {
		return DeviceConfigurationResponse.DeviceConfigurationMarket.class;
	}

	@Override
	public Class<DeviceConfigurationMarket> getDestinationClass() {
		return DeviceConfigurationMarket.class;
	}

	private List<AuthenticationMethod> getLoginMethods(
		DeviceConfigurationResponse.DeviceConfigurationMarket source) {
		//noinspection unchecked
		return modelConverter
			.map(source.getLoginMethodsList(), AuthenticationMethod.class);
	}

	private List<AuthenticationMethod> getRegisterMethods(
		DeviceConfigurationResponse.DeviceConfigurationMarket source) {
		//noinspection unchecked
		return modelConverter.map(source.getRegisterMethodsList(),
			AuthenticationMethod.class);
	}
}
