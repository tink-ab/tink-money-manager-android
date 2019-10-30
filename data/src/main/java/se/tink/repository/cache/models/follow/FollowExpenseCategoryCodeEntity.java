package se.tink.repository.cache.models.follow;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.annotation.NonNull;
import se.tink.repository.cache.database.TableNames;

@Entity(tableName = TableNames.FOLLOW_EXPENSE_CATEGORY_CODES, primaryKeys = {"followItemId",
	"categoryCode"})
public class FollowExpenseCategoryCodeEntity {

	@NonNull
	@ForeignKey(entity = FollowItemEntity.class, parentColumns = "id", childColumns = "followItemId", onDelete = CASCADE)
	String followItemId;

	@NonNull
	String categoryCode;

	@NonNull
	public String getFollowItemId() {
		return followItemId;
	}

	public void setFollowItemId(@NonNull String followItemId) {
		this.followItemId = followItemId;
	}

	@NonNull
	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(@NonNull String categoryCode) {
		this.categoryCode = categoryCode;
	}
}
