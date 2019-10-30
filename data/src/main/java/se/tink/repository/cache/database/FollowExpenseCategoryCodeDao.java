package se.tink.repository.cache.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import java.util.List;
import se.tink.repository.cache.models.follow.FollowExpenseCategoryCodeEntity;

@Dao
public abstract class FollowExpenseCategoryCodeDao extends
	BaseDao<FollowExpenseCategoryCodeEntity> {

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	public abstract void insertAll(List<FollowExpenseCategoryCodeEntity> items);

	@Query("DELETE FROM " + TableNames.FOLLOW_SAVINGS_ACCOUNT_IDS)
	public abstract void clear();

	@Transaction
	public void clearAndInsertAll(List<FollowExpenseCategoryCodeEntity> items) {
		clear();
		insertAll(items);
	}
}
