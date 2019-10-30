package se.tink.repository.cache.models.follow;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.annotation.NonNull;
import se.tink.repository.cache.database.TableNames;

@Entity(tableName = TableNames.FOLLOW_SAVINGS_ACCOUNT_IDS, primaryKeys = {"followItemId",
	"accountId"})
public class FollowSavingAccountIdEntity {

	@NonNull
	@ForeignKey(entity = FollowItemEntity.class, parentColumns = "id", childColumns = "followItemId", onDelete = CASCADE)
	private String followItemId;

	@NonNull
	private String accountId;

	@NonNull
	public String getFollowItemId() {
		return followItemId;
	}

	public void setFollowItemId(@NonNull String followItemId) {
		this.followItemId = followItemId;
	}

	@NonNull
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(@NonNull String accountId) {
		this.accountId = accountId;
	}
}
