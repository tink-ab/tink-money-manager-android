package se.tink.repository.cache

import se.tink.repository.DataSource
import se.tink.repository.LiveDataSource

interface Cache<T> : WritableCache<T>, DataSource<T>
interface LiveDataCache<T> : WritableCache<T>, LiveDataSource<T>

interface WritableCache<T> {
    fun clearAndInsert(item: T)
    fun insert(item: T)
    fun update(item: T)
    fun delete(item: T)
    fun clear()
}
