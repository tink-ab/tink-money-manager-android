package se.tink.repository.cache

import se.tink.repository.ChangeObserver
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

fun <T> WritableCache<List<T>>.createChangeObserver() = object : ChangeObserver<T> {
    override fun onCreate(items: List<T>) = insert(items)
    override fun onRead(items: List<T>) = clearAndInsert(items)
    override fun onUpdate(items: List<T>) = update(items)
    override fun onDelete(items: List<T>) = delete(items)
}