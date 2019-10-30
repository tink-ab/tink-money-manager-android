package se.tink.repository.cache.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import se.tink.repository.cache.models.budgets.BudgetEntity

@Dao
abstract class BudgetsDao {
    @Query("SELECT * FROM ${TableNames.BUDGETS}")
    abstract fun getAll(): LiveData<List<BudgetEntity>>

    @Query("DELETE FROM ${TableNames.BUDGETS}")
    abstract fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(items: List<BudgetEntity>)

    @Delete
    abstract fun delete(items: List<BudgetEntity>)

    @Update
    abstract fun update(items: List<BudgetEntity>)

    @Transaction
    open fun clearAndInsert(items: List<BudgetEntity>) {
        clear()
        insert(items)
    }
}