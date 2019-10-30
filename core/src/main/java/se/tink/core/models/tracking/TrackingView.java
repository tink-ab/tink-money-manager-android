package se.tink.core.models.tracking;

import org.joda.time.DateTime;

public class TrackingView {

	private DateTime date;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}
}
