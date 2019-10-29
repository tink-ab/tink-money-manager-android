package se.tink.core.models.user;


public class GetProfileResponse {
	private UserProfile userProfile;

	public GetProfileResponse() {
	}

	public GetProfileResponse(UserProfile userProfile) {
		this.userProfile = userProfile;

	}

	public UserProfile getUserProfile() {
		return userProfile;
	}
}
