package se.tink.repository.cache.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import java.util.List;
import se.tink.repository.cache.models.TransactionEntity;
import se.tink.repository.cache.models.TransactionWithAllRelations;

@Dao
public abstract class TransactionDao {

	@Query("SELECT * FROM " + TableNames.TRANSACTIONS)
	public abstract List<TransactionEntity> getAll();

	@Transaction
	@Query("SELECT * FROM " + TableNames.TRANSACTIONS)
	public abstract List<TransactionWithAllRelations> getAllAndAllRelations();

	@Query("DELETE FROM " + TableNames.TRANSACTIONS)
	public abstract void clear();

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	public abstract void insertAll(List<TransactionEntity> items);

	@Delete
	public abstract void deleteAll(List<TransactionEntity> items);

	@Update
	public abstract void updateAll(List<TransactionEntity> items);

	@Transaction
	public void clearAllAndInsert(List<TransactionEntity> items) {
		clear();
		insertAll(items);
	}
}
