package se.tink.core.models.identity;

public class IdentityEventAnswer {

	private IdentityAnswerKey key;
	private String label;

	public void setLabel(String label) {
		this.label = label;
	}

	public IdentityAnswerKey getKey() {
		return key;
	}

	public String getLabel() {
		return label;
	}

	public void setKey(IdentityAnswerKey key) {
		this.key = key;
	}
}
