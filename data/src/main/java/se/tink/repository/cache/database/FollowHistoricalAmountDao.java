package se.tink.repository.cache.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import java.util.List;
import se.tink.repository.cache.models.follow.FollowHistoricalAmountEntity;

@Dao
public abstract class FollowHistoricalAmountDao extends BaseDao<FollowHistoricalAmountEntity> {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	public abstract void insertAll(List<FollowHistoricalAmountEntity> items);

	@Query("DELETE FROM " + TableNames.FOLLOW_HISTORICAL_AMOUNTS)
	public abstract void clear();

	@Transaction
	public void clearAndInsertAll(List<FollowHistoricalAmountEntity> items) {
		clear();
		insertAll(items);
	}

}
