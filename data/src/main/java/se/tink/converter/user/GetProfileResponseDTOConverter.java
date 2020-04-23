package se.tink.converter.user;


import se.tink.converter.ModelConverter;
import se.tink.core.models.user.GetProfileResponse;
import se.tink.core.models.user.UserProfile;
import se.tink.modelConverter.AbstractConverter;

public class GetProfileResponseDTOConverter extends
	AbstractConverter<se.tink.grpc.v1.rpc.GetProfileResponse, GetProfileResponse> {

	private final ModelConverter modelConverter;

	public GetProfileResponseDTOConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public GetProfileResponse convert(se.tink.grpc.v1.rpc.GetProfileResponse source) {
		return new GetProfileResponse(modelConverter.map(source.getUserProfile(), UserProfile.class));
	}

	@Override
	public Class<se.tink.grpc.v1.rpc.GetProfileResponse> getSourceClass() {
		return se.tink.grpc.v1.rpc.GetProfileResponse.class;
	}

	@Override
	public Class<GetProfileResponse> getDestinationClass() {
		return GetProfileResponse.class;
	}
}
