package se.tink.core.models.user;

public class InitiateBankIdAuthenticationResponse {

	private String autoStartToken;
	private String authenticationToken;
	private BankIdAuthenticationStatus status;

	public InitiateBankIdAuthenticationResponse() {
	}

	public InitiateBankIdAuthenticationResponse(String autoStartToken, String authenticationToken,
		BankIdAuthenticationStatus status) {
		this.autoStartToken = autoStartToken;
		this.authenticationToken = authenticationToken;
		this.status = status;
	}

	public String getAutoStartToken() {
		return autoStartToken;
	}

	public String getAuthenticationToken() {
		return authenticationToken;
	}

	public BankIdAuthenticationStatus getStatus() {
		return status;
	}

	public void setAutoStartToken(String autoStartToken) {
		this.autoStartToken = autoStartToken;
	}

	public void setAuthenticationToken(String authenticationToken) {
		this.authenticationToken = authenticationToken;
	}

	public void setStatus(BankIdAuthenticationStatus status) {
		this.status = status;
	}
}
