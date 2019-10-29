package se.tink.core.models.user;

import java.util.List;

public class DeleteAccountReason {

	private List<String> reasons;
	private String comment;

	public List<String> getReasons() {
		return reasons;
	}

	public void setReasons(List<String> reasons) {
		this.reasons = reasons;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
