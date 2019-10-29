package se.tink.core.models.transaction;


import se.tink.core.models.misc.Amount;

public class SearchResultMetadata {

	private int totalCount;
	private Amount totalNetAmount;

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public Amount getTotalNetAmount() {
		return totalNetAmount;
	}

	public void setTotalNetAmount(Amount totalNetAmount) {
		this.totalNetAmount = totalNetAmount;
	}
}
