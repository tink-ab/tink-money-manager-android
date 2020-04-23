package se.tink.core.models.device.pushnotifications;

public class RegisterPushNotificationTokenRequest {

	private String deviceId;
	private String notificationToken;
	private String notificationPublicKey;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getNotificationToken() {
		return notificationToken;
	}

	public void setNotificationToken(String notificationToken) {
		this.notificationToken = notificationToken;
	}

	public String getNotificationPublicKey() {
		return notificationPublicKey;
	}

	public void setNotificationPublicKey(String notificationPublicKey) {
		this.notificationPublicKey = notificationPublicKey;
	}

}
