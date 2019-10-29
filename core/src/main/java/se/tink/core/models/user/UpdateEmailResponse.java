package se.tink.core.models.user;


public class UpdateEmailResponse {

	private UserProfile userProfile;

	public UpdateEmailResponse() {
	}

	public UpdateEmailResponse(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}
}
