package se.tink.core.models.identity;

import org.joda.time.DateTime;

public class IdentityEventSummary implements Comparable<IdentityEventSummary> {

	private DateTime date;
	private String id;
	private String description;
	private boolean seen;

	public void setDate(DateTime date) {
		this.date = date;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}

	public boolean isSeen() {
		return seen;
	}

	public String getDescription() {
		return description;
	}

	public String getId() {
		return id;
	}

	public DateTime getDate() {
		return date;
	}


	@Override
	public int compareTo(IdentityEventSummary o) {
		int seenCompare = Boolean.compare(seen, o.seen);
		if (seenCompare != 0) {
			return seenCompare;
		}
		int dateCompare = date.compareTo(o.date);
		if (dateCompare != 0) {
			return -dateCompare;
		}

		int descriptionCompare = description.compareTo(o.description);
		if (descriptionCompare != 0) {
			return descriptionCompare;
		}
		return id.compareTo(o.id);

	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof IdentityEventSummary)) {
			return false;
		}
		IdentityEventSummary other = (IdentityEventSummary) obj;
		id.equals(obj);

		return super.equals(obj);
	}

	public boolean deepEquals(IdentityEventSummary other) {
		return seen == other.seen & date.equals(other.date) && id.equals(other.id) && description
			.equals(other.description);
	}
}
