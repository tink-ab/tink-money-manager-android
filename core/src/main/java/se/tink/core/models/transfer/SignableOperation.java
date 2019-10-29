package se.tink.core.models.transfer;

import org.joda.time.DateTime;

public class SignableOperation {

	private DateTime created;
	private String id;
	private Status status;
	private String statusMessage;
	private Type type;
	private String underlyingId;
	private DateTime updated;
	private String credentialId;

	public String getCredentialId() {
		return credentialId;
	}

	public void setCredentialId(String credentialId) {
		this.credentialId = credentialId;
	}

	public DateTime getUpdated() {
		return updated;
	}

	public void setUpdated(DateTime updated) {
		this.updated = updated;
	}

	public String getUnderlyingId() {
		return underlyingId;
	}

	public void setUnderlyingId(String underlyingId) {
		this.underlyingId = underlyingId;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DateTime getCreated() {
		return created;
	}

	public void setCreated(DateTime created) {
		this.created = created;
	}

	public enum Type {
		TYPE_UNKNOWN,
		TYPE_TRANSFER,
		TYPE_ACCOUNT_CREATE,
	}

	public enum Status {
		STATUS_UNKNOWN,
		STATUS_CREATED,
		STATUS_EXECUTING,
		STATUS_AWAITING_CREDENTIALS,
		STATUS_CANCELLED,
		STATUS_FAILED,
		STATUS_EXECUTED,
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!obj.getClass().equals(getClass())) {
			return false;
		}
		return id.equals(((SignableOperation) obj).getId());
	}


}
