package se.tink.repository.cache.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import java.util.List;
import se.tink.repository.cache.models.FlagEntity;
import se.tink.repository.cache.models.I18ConfigurationEntity;

@Dao
public abstract class UserConfigurationDao {

	@Query("SELECT * FROM " + TableNames.FLAGS)
	public abstract List<FlagEntity> getFlags();

	@Query("SELECT * FROM " + TableNames.I18CONFIGURATION + " LIMIT 1")
	public abstract I18ConfigurationEntity getI18Configuration();

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	public abstract void insertAll(List<FlagEntity> flags);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	public abstract void insert(I18ConfigurationEntity i18ConfigurationEntity);

	@Update
	public abstract void updateAll(List<FlagEntity> tags);

	@Update
	public abstract void update(I18ConfigurationEntity i18ConfigurationEntity);

	@Delete
	public abstract void delete(List<FlagEntity> tags);

	@Delete
	public abstract void delete(I18ConfigurationEntity i18ConfigurationEntity);

	@Query("DELETE FROM " + TableNames.FLAGS)
	public abstract void clearFlags();

	@Query("DELETE FROM " + TableNames.I18CONFIGURATION)
	public abstract void clearI18Configuration();

	@Transaction
	public void clearAndInsertAll(List<FlagEntity> flags) {
		clearFlags();
		insertAll(flags);
	}

	@Transaction
	public void clearAndInsert(I18ConfigurationEntity i18ConfigurationEntity) {
		clearI18Configuration();
		insert(i18ConfigurationEntity);
	}

}
