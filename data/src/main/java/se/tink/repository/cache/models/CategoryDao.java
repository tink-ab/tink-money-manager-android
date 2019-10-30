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
public abstract class CategoryDao {

	@Query("SELECT * FROM " + TableNames.CATEGORIES)
	public abstract List<CategoryEntity> getAll();

	@Query("SELECT * FROM " + TableNames.CATEGORIES + " WHERE parentCode = :code")
	public abstract List<CategoryEntity> getChildrenForCode(String code);

	@Query(
		"SELECT * FROM " + TableNames.CATEGORIES + " WHERE parentCode IS NULL OR parentCode = ''")
	public abstract List<CategoryEntity> getTopLevelCategories();

	@Query("SELECT * FROM " + TableNames.CATEGORIES + " WHERE code = :code")
	public abstract CategoryEntity getCategoryByCode(String code);

	@Query("DELETE FROM " + TableNames.CATEGORIES)
	public abstract void clear();

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	public abstract void insertAll(List<CategoryEntity> categories);

	@Delete
	public abstract void deleteAll(List<CategoryEntity> category);

	@Update
	public abstract void updateAll(List<CategoryEntity> categoryEntity);

	@Transaction
	public void clearAllAndInsert(List<CategoryEntity> categories) {
		clear();
		insertAll(categories);
	}
}
