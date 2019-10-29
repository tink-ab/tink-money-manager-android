package se.tink.core.models.user;

public class RegisterResponse {

	private String sessionId;

	public RegisterResponse() {
	}

	public RegisterResponse(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getSessionId() {
		return sessionId;
	}
}
