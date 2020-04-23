package se.tink.converter.user;


import se.tink.converter.ModelConverter;
import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.user.LoginResponse;
import se.tink.modelConverter.AbstractConverter;

public class LoginResponseDTOConverter extends
	AbstractConverter<se.tink.grpc.v1.rpc.LoginResponse, LoginResponse> {

	private ModelConverter modelConverter;

	public LoginResponseDTOConverter(ModelConverterImpl modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public LoginResponse convert(se.tink.grpc.v1.rpc.LoginResponse source) {
		return new LoginResponse(source.getSessionId());
	}

	@Override
	public Class<se.tink.grpc.v1.rpc.LoginResponse> getSourceClass() {
		return se.tink.grpc.v1.rpc.LoginResponse.class;
	}

	@Override
	public Class<LoginResponse> getDestinationClass() {
		return LoginResponse.class;
	}
}
