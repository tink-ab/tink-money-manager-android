package se.tink.extensions

import se.tink.repository.ChangeObserver

fun <T> List<ChangeObserver<T>>.getStreamingServiceObserver() = object : ChangeObserver<T> {
    override fun onCreate(items: List<T>) = forEach { it.onCreate(items) }
    override fun onRead(items: List<T>) = forEach { it.onRead(items) }
    override fun onUpdate(items: List<T>) = forEach { it.onUpdate(items) }
    override fun onDelete(items: List<T>) = forEach { it.onDelete(items) }
}