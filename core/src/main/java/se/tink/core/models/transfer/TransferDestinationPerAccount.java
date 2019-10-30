package se.tink.core.models.transfer;

import java.util.List;

public class TransferDestinationPerAccount {
	private String accountId;
	private List<TransferDestination> destinations;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public List<TransferDestination> getDestinations() {
		return destinations;
	}

	public void setDestinations(List<TransferDestination> destinations) {
		this.destinations = destinations;
	}
}
