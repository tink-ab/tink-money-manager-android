package se.tink.repository.cache.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import se.tink.repository.cache.models.CredentialsEntity

@Dao
abstract class CredentialsDao {
    @Query("SELECT * FROM ${TableNames.CREDENTIALS}")
    abstract fun getAll(): LiveData<List<CredentialsEntity>>

    @Query("DELETE FROM ${TableNames.CREDENTIALS}")
    abstract fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(items: List<CredentialsEntity>)

    @Delete
    abstract fun delete(items: List<CredentialsEntity>)

    @Update
    abstract fun update(items: List<CredentialsEntity>)

    @Transaction
    open fun clearAndInsert(items: List<CredentialsEntity>) {
        clear()
        insert(items)
    }
}