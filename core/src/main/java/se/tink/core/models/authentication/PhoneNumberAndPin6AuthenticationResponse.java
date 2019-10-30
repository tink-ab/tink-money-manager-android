package se.tink.core.models.authentication;

public class PhoneNumberAndPin6AuthenticationResponse {

	private String authenticationToken;
	private AuthenticationStatus status;

	public String getAuthenticationToken() {
		return authenticationToken;
	}

	public void setAuthenticationToken(String authenticationToken) {
		this.authenticationToken = authenticationToken;
	}

	public AuthenticationStatus getStatus() {
		return status;
	}

	public void setStatus(AuthenticationStatus status) {
		this.status = status;
	}

}
