package se.tink.converter.device;

import com.google.common.base.Strings;
import com.google.protobuf.StringValue;
import se.tink.converter.ModelConverter;
import se.tink.core.models.device.UpdateI18NSettingsRequest;
import se.tink.grpc.v1.rpc.UpdateI18NSettingsRequest.Builder;
import se.tink.modelConverter.AbstractConverter;

public class UpdateI18NSettingsRequestConverter extends
	AbstractConverter<UpdateI18NSettingsRequest, se.tink.grpc.v1.rpc.UpdateI18NSettingsRequest> {

	private ModelConverter modelConverter;

	public UpdateI18NSettingsRequestConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public se.tink.grpc.v1.rpc.UpdateI18NSettingsRequest convert(
		UpdateI18NSettingsRequest source) {
		Builder destination = se.tink.grpc.v1.rpc.UpdateI18NSettingsRequest.newBuilder();
		if (!Strings.isNullOrEmpty(source.getLocaleCode())) {
			destination
				.setLocaleCode(modelConverter.map(source.getLocaleCode(), StringValue.class));
		}
		return destination.build();
	}

	@Override
	public Class<UpdateI18NSettingsRequest> getSourceClass() {
		return UpdateI18NSettingsRequest.class;
	}

	@Override
	public Class<se.tink.grpc.v1.rpc.UpdateI18NSettingsRequest> getDestinationClass() {
		return se.tink.grpc.v1.rpc.UpdateI18NSettingsRequest.class;
	}

}
