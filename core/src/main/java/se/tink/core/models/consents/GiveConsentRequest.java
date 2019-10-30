package se.tink.core.models.consents;


public class GiveConsentRequest {

	private ConsentAction action;
	private String checksum;
	private String key;
	private String version;

	public ConsentAction getAction() {
		return action;
	}

	public void setAction(ConsentAction action) {
		this.action = action;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
