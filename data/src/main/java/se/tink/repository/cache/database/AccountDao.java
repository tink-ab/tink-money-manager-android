package se.tink.repository.cache.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import java.util.List;
import se.tink.repository.cache.models.AccountAndIdentifiers;
import se.tink.repository.cache.models.AccountEntity;

@Dao
public abstract class AccountDao {

    @Transaction
    @Query("SELECT * FROM " + TableNames.ACCOUNTS)
    public abstract LiveData<List<AccountAndIdentifiers>> getAllWithIdentifiers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAll(List<AccountEntity> accountEntities);

    @Update
    public abstract void updateAll(List<AccountEntity> accountEntities);

    @Delete
    public abstract void deleteAll(List<AccountEntity> entities);

    @Query("DELETE FROM " + TableNames.ACCOUNTS)
    public abstract void clear();

    @Transaction
    public void clearAndInsertAll(List<AccountEntity> accountEntities) {
        clear();
        insertAll(accountEntities);
    }
}
