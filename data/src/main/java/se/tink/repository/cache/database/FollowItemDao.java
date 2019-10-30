package se.tink.repository.cache.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import java.util.List;
import se.tink.repository.cache.models.follow.FollowItemEntity;
import se.tink.repository.cache.models.follow.FollowItemWithRelations;

@Dao
public abstract class FollowItemDao extends BaseDao<FollowItemEntity> {

	@Transaction
	@Query("SELECT * FROM " + TableNames.FOLLOW_ITEMS)
	public abstract List<FollowItemWithRelations> getAll();

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	public abstract void insertAll(List<FollowItemEntity> items);

	@Query("DELETE FROM " + TableNames.FOLLOW_ITEMS)
	public abstract void clear();

	@Transaction
	public void clearAndInsertAll(List<FollowItemEntity> items) {
		clear();
		insertAll(items);
	}

}
