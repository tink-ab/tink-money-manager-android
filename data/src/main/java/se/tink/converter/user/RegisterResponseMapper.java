package se.tink.converter.user;

import se.tink.converter.ModelConverter;
import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.user.RegisterResponse;
import se.tink.modelConverter.AbstractConverter;

public class RegisterResponseMapper extends
	AbstractConverter<se.tink.grpc.v1.rpc.RegisterResponse, RegisterResponse> {

	private ModelConverter modelConverter;

	public RegisterResponseMapper(ModelConverterImpl modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public RegisterResponse convert(se.tink.grpc.v1.rpc.RegisterResponse source) {
		return new RegisterResponse(source.getSessionId());
	}

	@Override
	public Class<se.tink.grpc.v1.rpc.RegisterResponse> getSourceClass() {
		return se.tink.grpc.v1.rpc.RegisterResponse.class;
	}

	@Override
	public Class<RegisterResponse> getDestinationClass() {
		return RegisterResponse.class;
	}
}
