package se.tink.repository.cache.models;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import se.tink.repository.cache.database.TableNames;

@Entity(tableName = TableNames.TRANSACTIONS)
public class TransactionEntity {

	private String accountId;
	private String categoryCode;
	private String description;
	private String secondaryDescription;

	@PrimaryKey
	@NonNull
	private String id;
	private String notes;
	private long originalTimestamp;
	private String originalDescription;
	private boolean pending;
	private long timestamp;
	private String type;

	@Embedded
	private AmountEntity amount;

	@Embedded(prefix = "dispensable_")
	private AmountEntity dispensableAmount;

	public String getAccountId() {
		return accountId;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getOriginalDescription() {
		return originalDescription;
	}

	public void setOriginalDescription(String originalDescription) {
		this.originalDescription = originalDescription;
	}

	public boolean isPending() {
		return pending;
	}

	public void setPending(boolean pending) {
		this.pending = pending;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public long getOriginalTimestamp() {
		return originalTimestamp;
	}

	public void setOriginalTimestamp(long originalTimestamp) {
		this.originalTimestamp = originalTimestamp;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Embedded
	private TransactionDetailsEntity details;

	public TransactionDetailsEntity getDetails() {
		return details;
	}

	public void setDetails(TransactionDetailsEntity details) {
		this.details = details;
	}

	public AmountEntity getAmount() {
		return amount;
	}

	public void setAmount(AmountEntity amount) {
		this.amount = amount;
	}

	public String getSecondaryDescription() {
		return secondaryDescription;
	}

	public void setSecondaryDescription(String secondaryDescription) {
		this.secondaryDescription = secondaryDescription;
	}

	public AmountEntity getDispensableAmount() {
		return dispensableAmount;
	}

	public void setDispensableAmount(AmountEntity dispensableAmount) {
		this.dispensableAmount = dispensableAmount;
	}
}
