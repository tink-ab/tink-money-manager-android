package se.tink.repository.cache.models;

import androidx.room.Entity;
import androidx.annotation.NonNull;
import se.tink.repository.cache.database.TableNames;

@Entity(primaryKeys = {"name", "transactionId"}, tableName = TableNames.TAGS)
public class TagEntity {

	@NonNull
	private String name;

	@NonNull
	private String transactionId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
}
