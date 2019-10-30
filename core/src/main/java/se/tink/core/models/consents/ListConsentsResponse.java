package se.tink.core.models.consents;

import java.util.List;
import java.util.Map;

public class ListConsentsResponse {

	private String key;
	private String version;
	private String title;
	private String body;
	private String checksum;
	private List<ConsentMessage> messages;
	private Map<String, String> attachments;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public List<ConsentMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<ConsentMessage> messages) {
		this.messages = messages;
	}

	public Map<String, String> getAttachments() {
		return attachments;
	}

	public void setAttachments(Map<String, String> attachments) {
		this.attachments = attachments;
	}

}
