package se.tink.repository.cache.database

import androidx.room.*
import se.tink.repository.cache.models.CounterpartEntity

@Dao
abstract class CounterpartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(items: List<CounterpartEntity>)

    @Query("DELETE FROM ${TableNames.COUNTERPARTS}")
    abstract fun clear()

    @Transaction
    open fun clearAllAndInsert(items: List<CounterpartEntity>) {
        clear()
        insertAll(items)
    }

    //TODO: kotlin bug, change `arg0` to `transactionIds` as soon as resolved
    @Query("DELETE FROM ${TableNames.COUNTERPARTS} WHERE transactionId IN(:transactionIds)")
    abstract fun clearWhereTransactionIdIn(transactionIds: List<String>)

    @Transaction
    open fun clearWithTransactionIdAndInsertAllCounterparts(transactionIds: List<String>, counterparts: List<CounterpartEntity>) {
        clearWhereTransactionIdIn(transactionIds)
        insertAll(counterparts)
    }

}
