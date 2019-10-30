package se.tink.repository.cache.models;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.annotation.NonNull;
import se.tink.repository.cache.database.TableNames;

@Entity(primaryKeys = {"accountId",
	"identifier"}, tableName = TableNames.ACCOUNT_SOURCE_IDENTIFIERS)
public class AccountSourceIdentifierEntity {

	@NonNull
	@ForeignKey(entity = AccountEntity.class, parentColumns = "id", childColumns = "accountId", onDelete = CASCADE)
	private String accountId;

	@NonNull
	private String identifier;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
}
