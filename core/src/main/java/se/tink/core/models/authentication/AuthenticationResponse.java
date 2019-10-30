package se.tink.core.models.authentication;


public class AuthenticationResponse {

	private String authenticationToken;
	private AuthenticationStatus status;


	public AuthenticationResponse() {
	}

	public AuthenticationResponse(String authenticationToken, AuthenticationStatus status) {
		this.authenticationToken = authenticationToken;
		this.status = status;
	}

	public AuthenticationStatus getStatus() {
		return status;
	}

	public void setStatus(AuthenticationStatus status) {
		this.status = status;
	}

	public String getAuthenticationToken() {
		return authenticationToken;
	}

	public void setAuthenticationToken(String authenticationToken) {
		this.authenticationToken = authenticationToken;
	}
}
