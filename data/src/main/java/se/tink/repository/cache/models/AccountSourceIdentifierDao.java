package se.tink.repository.cache.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import java.util.List;
import se.tink.repository.cache.database.TableNames;

@Dao
public abstract class AccountSourceIdentifierDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAll(List<AccountSourceIdentifierEntity> items);

	@Query("SELECT * FROM " + TableNames.ACCOUNT_SOURCE_IDENTIFIERS + " WHERE accountId = :accountId")
	public abstract List<AccountSourceIdentifierEntity> getForAccountId(String accountId);


	@Query("SELECT * FROM " + TableNames.ACCOUNT_SOURCE_IDENTIFIERS)
	public abstract List<AccountSourceIdentifierEntity> getAll();

	@Update
	public abstract void updateAll(List<AccountSourceIdentifierEntity> items);

	@Delete
	public abstract void deleteAll(List<AccountSourceIdentifierEntity> items);

	@Query("DELETE FROM " + TableNames.ACCOUNT_SOURCE_IDENTIFIERS)
	public abstract void clear();

	@Transaction
	public void clearAndInsertAll(List<AccountSourceIdentifierEntity> items) {
		clear();
		insertAll(items);
	}
}
