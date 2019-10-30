package se.tink.repository.cache.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import io.reactivex.Flowable;
import java.util.List;
import se.tink.repository.cache.models.StatisticEntity;

@Dao
public abstract class StatisticsDao {

	@Query("SELECT * FROM " + TableNames.STATISTICS)
	public abstract Flowable<List<StatisticEntity>> getAll();

	@Query("DELETE FROM " + TableNames.STATISTICS)
	public abstract void clear();

	@Insert
	public abstract void insertAll(List<StatisticEntity> categories);

	@Delete
	public abstract void deleteAll(List<StatisticEntity> category);

	@Update
	public abstract void updateAll(List<StatisticEntity> categoryEntity);

	@Transaction
	public void clearAllAndInsert(List<StatisticEntity> categories) {
		clear();
		insertAll(categories);
	}

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	protected abstract void insertAllIgnoreConflicts(List<StatisticEntity> items);

	@Transaction
	public void upsert(List<StatisticEntity> items) {
		insertAllIgnoreConflicts(items);
		updateAll(items);
	}
}
