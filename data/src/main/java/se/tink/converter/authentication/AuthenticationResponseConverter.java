package se.tink.converter.authentication;

import se.tink.converter.ModelConverter;
import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.authentication.AuthenticationResponse;
import se.tink.core.models.authentication.AuthenticationStatus;
import se.tink.grpc.v1.rpc.AbnAmroAuthenticationResponse;
import se.tink.modelConverter.AbstractConverter;

public class AuthenticationResponseConverter extends
	AbstractConverter<AbnAmroAuthenticationResponse, AuthenticationResponse> {

	private ModelConverter modelConverter;

	public AuthenticationResponseConverter(ModelConverterImpl modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public AuthenticationResponse convert(AbnAmroAuthenticationResponse source) {
		AuthenticationResponse destination = new AuthenticationResponse();
		destination.setAuthenticationToken(source.getAuthenticationToken());
		if (source.getStatus() != null) {
			destination
				.setStatus(modelConverter.map(source.getStatus(), AuthenticationStatus.class));
		}
		return destination;
	}

	@Override
	public Class<AbnAmroAuthenticationResponse> getSourceClass() {
		return AbnAmroAuthenticationResponse.class;
	}

	@Override
	public Class<AuthenticationResponse> getDestinationClass() {
		return AuthenticationResponse.class;
	}
}
