package se.tink.repository.cache.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import java.util.List;
import se.tink.repository.cache.models.TagEntity;

@Dao
public abstract class TagDao {

	@Query("SELECT * FROM " + TableNames.TAGS + " WHERE transactionId = :transactionId")
	public abstract List<TagEntity> getForTransactionId(String transactionId);

	@Query("DELETE FROM " + TableNames.TAGS)
	public abstract void clear();

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	public abstract void insertAll(List<TagEntity> items);

	@Delete
	public abstract void deleteAll(List<TagEntity> items);

	@Update
	public abstract void updateAll(List<TagEntity> items);

	@Transaction
	public void clearAllAndInsert(List<TagEntity> items) {
		clear();
		insertAll(items);
	}

	@Query("DELETE FROM " + TableNames.TAGS + " WHERE transactionId IN(:transactionIds)")
	abstract void clearWhereTransactionIdIn(List<String> transactionIds);

	@Transaction
	public void clearWithTransactionIdAndInsertAllTags(List<String> transactionIds, List<TagEntity> tags) {
		clearWhereTransactionIdIn(transactionIds);
		insertAll(tags);
	}

}
