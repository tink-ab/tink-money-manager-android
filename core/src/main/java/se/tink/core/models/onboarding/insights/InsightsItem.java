package se.tink.core.models.onboarding.insights;

import java.io.Serializable;

public class InsightsItem implements Serializable {

	private String title;
	private String body;

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
}
