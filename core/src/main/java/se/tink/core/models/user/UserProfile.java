package se.tink.core.models.user;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import se.tink.core.models.device.AuthenticationMethod;

public final class UserProfile {

	private String username;
	private String nationalId;
	private Set<AuthenticationMethod> authorizedLoginMethods;
	private Set<AuthenticationMethod> availableAuthenticationMethods;

	public UserProfile() {

	}

	private UserProfile(String username, String nationalId,
		Set<AuthenticationMethod> authorizedLoginMethods,
		Set<AuthenticationMethod> availableAuthenticationMethods) {
		this.username = username;
		this.nationalId = nationalId;
		this.authorizedLoginMethods = authorizedLoginMethods;
		this.availableAuthenticationMethods = availableAuthenticationMethods;
	}

	public boolean hasPassword() {
		return authorizedLoginMethods
			.contains(AuthenticationMethod.AUTHENTICATION_METHOD_EMAIL_AND_PASSWORD);
	}

	public String getUsername() {
		return username;
	}

	public static final class Builder {

		private final String username;
		private final String nationalId;
		private EnumSet<AuthenticationMethod> authorizedLoginMethods = EnumSet
			.noneOf(AuthenticationMethod.class);
		private EnumSet<AuthenticationMethod> availableLoginMethods = EnumSet
			.noneOf(AuthenticationMethod.class);

		public Builder(String username, String nationalId) {
			this.username = username;
			this.nationalId = nationalId;
		}

		public Builder withAuthorizedLoginMethods(
			List<AuthenticationMethod> authorizedLoginMethods) {
			if (authorizedLoginMethods.isEmpty()) {
				this.authorizedLoginMethods = EnumSet.noneOf(AuthenticationMethod.class);
			} else {
				this.authorizedLoginMethods = EnumSet.copyOf(authorizedLoginMethods);
			}
			return this;
		}

		public Builder withAvailableLoginMethods(List<AuthenticationMethod> availableLoginMethods) {
			if (availableLoginMethods.isEmpty()) {
				this.authorizedLoginMethods = EnumSet.noneOf(AuthenticationMethod.class);
			} else {
				this.availableLoginMethods = EnumSet.copyOf(availableLoginMethods);
			}
			return this;
		}

		public UserProfile build() {
			return new UserProfile(
				username,
				nationalId,
				authorizedLoginMethods,
				availableLoginMethods);
		}
	}
}
