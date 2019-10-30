package se.tink.converter.user;

import java.util.LinkedList;
import java.util.List;
import se.tink.converter.EnumMappers;
import se.tink.core.models.device.AuthenticationMethod;
import se.tink.core.models.user.UserProfile;
import se.tink.modelConverter.AbstractConverter;

public class UserProfileDTOConverter extends
	AbstractConverter<se.tink.grpc.v1.models.UserProfile, UserProfile> {

	@Override
	public UserProfile convert(se.tink.grpc.v1.models.UserProfile source) {

		return new UserProfile.Builder(source.getUsername(), source.getNationalId())
			.withAuthorizedLoginMethods(mapAuthorizedAuthenticationMethods(source))
			.withAvailableLoginMethods(mapAvailableAuthenticationMethods(source))
			.build();
	}

	@Override
	public Class<se.tink.grpc.v1.models.UserProfile> getSourceClass() {
		return se.tink.grpc.v1.models.UserProfile.class;
	}

	@Override
	public Class<UserProfile> getDestinationClass() {
		return UserProfile.class;
	}

	private List<AuthenticationMethod> mapAuthorizedAuthenticationMethods(
		se.tink.grpc.v1.models.UserProfile source) {

		List<AuthenticationMethod> authenticationMethods = new LinkedList<>();

		for (se.tink.grpc.v1.models.AuthenticationMethod authenticationMethod : source
			.getAuthorizedLoginMethodsList()) {
			authenticationMethods
				.add(EnumMappers.AUTHENTICATION_METHOD_MAP.get(authenticationMethod));
		}

		return authenticationMethods;
	}

	private List<AuthenticationMethod> mapAvailableAuthenticationMethods(
		se.tink.grpc.v1.models.UserProfile source) {

		List<AuthenticationMethod> authenticationMethods = new LinkedList<>();

		for (se.tink.grpc.v1.models.AuthenticationMethod authenticationMethod : source
			.getAvailableLoginMethodsList()) {
			authenticationMethods
				.add(EnumMappers.AUTHENTICATION_METHOD_MAP.get(authenticationMethod));
		}

		return authenticationMethods;
	}

}
