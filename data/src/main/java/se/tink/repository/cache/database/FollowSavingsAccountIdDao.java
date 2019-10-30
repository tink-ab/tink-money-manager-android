package se.tink.repository.cache.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import java.util.List;
import se.tink.repository.cache.models.follow.FollowSavingAccountIdEntity;

@Dao
public abstract class FollowSavingsAccountIdDao extends BaseDao<FollowSavingAccountIdEntity> {

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	public abstract void insertAll(List<FollowSavingAccountIdEntity> items);

	@Query("DELETE FROM " + TableNames.FOLLOW_SAVINGS_ACCOUNT_IDS)
	public abstract void clear();

	@Transaction
	public void clearAndInsertAll(List<FollowSavingAccountIdEntity> items) {
		clear();
		insertAll(items);
	}
}
