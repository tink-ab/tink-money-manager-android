package se.tink.core.models.device.pushnotifications;

public class Device {

	private String id;
	private String notificationToken;
	private String notificationPublicKey;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
