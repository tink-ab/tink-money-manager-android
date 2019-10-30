package se.tink.converter.user;


import se.tink.converter.ModelConverter;
import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.user.UpdateEmailResponse;
import se.tink.core.models.user.UserProfile;
import se.tink.modelConverter.AbstractConverter;

public class UpdateEmailResponseDTOConverter extends
	AbstractConverter<se.tink.grpc.v1.rpc.UpdateEmailResponse, UpdateEmailResponse> {

	private ModelConverter modelConverter;

	public UpdateEmailResponseDTOConverter(ModelConverterImpl modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public UpdateEmailResponse convert(se.tink.grpc.v1.rpc.UpdateEmailResponse source) {
		return new UpdateEmailResponse(modelConverter.map(source.getUserProfile(), UserProfile.class));
	}

	@Override
	public Class<se.tink.grpc.v1.rpc.UpdateEmailResponse> getSourceClass() {
		return se.tink.grpc.v1.rpc.UpdateEmailResponse.class;
	}

	@Override
	public Class<UpdateEmailResponse> getDestinationClass() {
		return UpdateEmailResponse.class;
	}
}
