package se.tink.converter.device;

import se.tink.converter.ModelConverter;
import se.tink.core.models.device.I18NSettings;
import se.tink.core.models.device.UpdateI18NSettingsResponse;
import se.tink.modelConverter.AbstractConverter;

public class UpdateI18NSettingsResponseConverter extends
	AbstractConverter<se.tink.grpc.v1.rpc.UpdateI18NSettingsResponse, UpdateI18NSettingsResponse> {

	private ModelConverter modelConverter;

	public UpdateI18NSettingsResponseConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public UpdateI18NSettingsResponse convert(
		se.tink.grpc.v1.rpc.UpdateI18NSettingsResponse source) {
		UpdateI18NSettingsResponse destination = new UpdateI18NSettingsResponse();
		if (source.getI18NSettings() != null) {
			destination
				.setI18NSettings(modelConverter.map(source.getI18NSettings(), I18NSettings.class));
		}
		return destination;
	}

	@Override
	public Class<se.tink.grpc.v1.rpc.UpdateI18NSettingsResponse> getSourceClass() {
		return se.tink.grpc.v1.rpc.UpdateI18NSettingsResponse.class;
	}

	@Override
	public Class<UpdateI18NSettingsResponse> getDestinationClass() {
		return UpdateI18NSettingsResponse.class;
	}

}
