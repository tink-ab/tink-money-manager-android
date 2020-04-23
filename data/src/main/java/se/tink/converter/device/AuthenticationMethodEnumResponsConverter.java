package se.tink.converter.device;

import se.tink.converter.ModelConverter;
import se.tink.core.models.device.AuthenticationMethod;
import se.tink.modelConverter.AbstractConverter;

public class AuthenticationMethodEnumResponsConverter extends
	AbstractConverter<se.tink.grpc.v1.models.AuthenticationMethod, AuthenticationMethod> {

	private ModelConverter modelConverter;

	public AuthenticationMethodEnumResponsConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public AuthenticationMethod convert(se.tink.grpc.v1.models.AuthenticationMethod source) {
		return AuthenticationMethod.getAuthMethodByKey(source.getNumber());
	}

	@Override
	public Class<se.tink.grpc.v1.models.AuthenticationMethod> getSourceClass() {
		return se.tink.grpc.v1.models.AuthenticationMethod.class;
	}

	@Override
	public Class<AuthenticationMethod> getDestinationClass() {
		return AuthenticationMethod.class;
	}

}
