package se.tink.core.models.user;

public class LoginResponse {

	private String sessionId;

	public LoginResponse() {
	}

	public LoginResponse(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getSessionId() {
		return sessionId;
	}
}
