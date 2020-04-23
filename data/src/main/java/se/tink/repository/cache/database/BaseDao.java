package se.tink.repository.cache.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Update;
import java.util.List;

@Dao
public abstract class BaseDao<T> {

	@Update
	public abstract void update(T item);

	@Update
	public abstract void updateAll(List<T> item);

	@Delete
	public abstract void delete(T item);

	@Delete
	public abstract void deleteAll(List<T> item);
}
