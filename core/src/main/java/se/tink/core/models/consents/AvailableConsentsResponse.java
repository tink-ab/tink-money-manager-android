package se.tink.core.models.consents;

import java.util.List;

public class AvailableConsentsResponse {

	private List<Consent> consents;

	public List<Consent> getConsents() {
		return consents;
	}

	public void setConsents(List<Consent> consents) {
		this.consents = consents;
	}
}
