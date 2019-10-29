package se.tink.core.models.feed;

import java.util.List;

public class EventsPage {

	private String html;
	private List<String> identifiers;
	private List<String> keys;
	private long timestamp;
	private long nextPageOffset;

	public EventsPage() {
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public List<String> getIdentifiers() {
		return identifiers;
	}

	public void setIdentifiers(List<String> identifiers) {
		this.identifiers = identifiers;
	}

	public List<String> getKeys() {
		return keys;
	}

	public void setKeys(List<String> keys) {
		this.keys = keys;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public long getNextPageOffset() {
		return nextPageOffset;
	}

	public void setNextPageOffset(long nextPageOffset) {
		this.nextPageOffset = nextPageOffset;
	}
}
