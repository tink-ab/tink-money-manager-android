package se.tink.core.models.consents;

import java.util.List;

public class ConsentMessage {

	private String message;
	private List<ConsentMessageLink> links;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ConsentMessageLink> getLinks() {
		return links;
	}

	public void setLinks(List<ConsentMessageLink> links) {
		this.links = links;
	}

}
