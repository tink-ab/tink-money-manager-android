package se.tink.core.models.user;

import se.tink.core.models.authentication.AuthenticationStatus;

public class EmailAndPasswordAuthenticationResponse {

	private String authenticationToken;
	private AuthenticationStatus status;

	public EmailAndPasswordAuthenticationResponse() {

	}

	public EmailAndPasswordAuthenticationResponse(String authenticationToken,
		AuthenticationStatus status) {
		this.authenticationToken = authenticationToken;
		this.status = status;
	}

	public String getAuthenticationToken() {
		return authenticationToken;
	}

	public AuthenticationStatus getStatus() {
		return status;
	}
}
