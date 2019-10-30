package se.tink.repository.cache.models.follow;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.annotation.NonNull;
import se.tink.repository.cache.database.TableNames;
import se.tink.repository.cache.models.TransactionEntity;

@Entity(tableName = TableNames.FOLLOW_TRANSACTIONS, primaryKeys = {"followItemId", "transactionId"})
public class FollowItemTransactionEntity {

	@NonNull
	@ForeignKey(entity = FollowItemEntity.class, parentColumns = "id", childColumns = "followItemId", onDelete = CASCADE)
	private String followItemId;

	@NonNull
	@ForeignKey(entity = TransactionEntity.class, parentColumns = "id", childColumns = "transactionId", onDelete = CASCADE)
	private String transactionId;

	public String getFollowItemId() {
		return followItemId;
	}

	public void setFollowItemId(String followItemId) {
		this.followItemId = followItemId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
}
