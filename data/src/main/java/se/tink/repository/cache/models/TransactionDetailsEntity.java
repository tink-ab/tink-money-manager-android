package se.tink.repository.cache.models;

import androidx.room.Entity;

@Entity
public class TransactionDetailsEntity {

	private String transferTransactionId;

	public String getTransferTransactionId() {
		return transferTransactionId;
	}

	public void setTransferTransactionId(String transferTransactionId) {
		this.transferTransactionId = transferTransactionId;
	}
}
