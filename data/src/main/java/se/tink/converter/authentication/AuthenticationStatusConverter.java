package se.tink.converter.authentication;


import se.tink.grpc.v1.models.AuthenticationStatus;
import se.tink.modelConverter.AbstractConverter;

public class AuthenticationStatusConverter extends
	AbstractConverter<AuthenticationStatus, se.tink.core.models.authentication.AuthenticationStatus> {

	@Override
	public se.tink.core.models.authentication.AuthenticationStatus convert(
		AuthenticationStatus source) {
		switch (source) {
			case AUTHENTICATION_STATUS_NO_USER:
				return se.tink.core.models.authentication.AuthenticationStatus.AUTHENTICATION_STATUS_NO_USER;
			case AUTHENTICATION_STATUS_AUTHENTICATED:
				return se.tink.core.models.authentication.AuthenticationStatus.AUTHENTICATION_STATUS_AUTHENTICATED;
			case AUTHENTICATION_STATUS_AUTHENTICATION_ERROR:
				return se.tink.core.models.authentication.AuthenticationStatus.AUTHENTICATION_STATUS_AUTHENTICATION_ERROR;
			case AUTHENTICATION_STATUS_USER_BLOCKED:
				return se.tink.core.models.authentication.AuthenticationStatus.AUTHENTICATION_STATUS_USER_BLOCKED;
			case AUTHENTICATION_STATUS_AUTHENTICATED_UNAUTHORIZED_DEVICE:
				return se.tink.core.models.authentication.AuthenticationStatus.AUTHENTICATION_STATUS_AUTHENTICATED_UNAUTHORIZED_DEVICE;
			case AUTHENTICATION_STATUS_UNKNOWN:
				return se.tink.core.models.authentication.AuthenticationStatus.AUTHENTICATION_STATUS_UNKNOWN;
			case UNRECOGNIZED:
			default:
				return se.tink.core.models.authentication.AuthenticationStatus.UNRECOGNIZED;
		}
	}

	@Override
	public Class<AuthenticationStatus> getSourceClass() {
		return AuthenticationStatus.class;
	}

	@Override
	public Class<se.tink.core.models.authentication.AuthenticationStatus> getDestinationClass() {
		return se.tink.core.models.authentication.AuthenticationStatus.class;
	}
}
