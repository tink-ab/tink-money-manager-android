package se.tink.repository.cache.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import java.util.List;
import se.tink.repository.cache.models.IdentifiablePeriodEntity;

@Dao
public abstract class PeriodDao {

	@Query("SELECT * FROM " + TableNames.PERIODS + " WHERE periodKey = :periodKey LIMIT 1")
	public abstract IdentifiablePeriodEntity getForKey(String periodKey);

	@Query("SELECT * FROM " + TableNames.PERIODS)
	public abstract List<IdentifiablePeriodEntity> getAll();

	@Query("DELETE FROM " + TableNames.PERIODS)
	public abstract void clear();

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	public abstract void insertAll(List<IdentifiablePeriodEntity> items);

	@Delete
	public abstract void deleteAll(List<IdentifiablePeriodEntity> items);

	@Update
	public abstract void updateAll(List<IdentifiablePeriodEntity> items);

	@Transaction
	public void clearAllAndInsert(List<IdentifiablePeriodEntity> items) {
		clear();
		insertAll(items);
	}
}
