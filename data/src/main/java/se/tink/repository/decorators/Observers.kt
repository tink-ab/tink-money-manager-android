@file:JvmName("Observers")

package se.tink.repository.decorators

import se.tink.repository.ChangeObserver
import timber.log.Timber

private class LoggingDecorator<T>(
    private val changeObserver: ChangeObserver<T>,
    private val logTag: String,
    private val logTypeName: String
) : ChangeObserver<T> {


    override fun onCreate(items: List<T>?) {
        Timber.tag(logTag).d(generateLogMessage("onCreate", items))
        changeObserver.onCreate(items)
    }

    override fun onRead(items: List<T>?) {
        Timber.tag(logTag).d(generateLogMessage("onRead", items))
        changeObserver.onRead(items)
    }

    override fun onUpdate(items: List<T>?) {
        Timber.tag(logTag).d(generateLogMessage("onUpdate", items))
        changeObserver.onUpdate(items)
    }

    override fun onDelete(items: List<T>?) {
        Timber.tag(logTag).d(generateLogMessage("onDelete", items))
        changeObserver.onDelete(items)
    }

    private fun generateLogMessage(methodName: String, items: List<T>?): String =
        if (items != null) {
            "Streamed ${logTypeName}s - $methodName: nr of items: ${items.size}"
        } else {

            "Streamed null in $methodName"
        }
}


private class SynchronizedDecorator<T>(
    private val observer: ChangeObserver<T>,
    private val lock: Any
) : ChangeObserver<T> {

    override fun onCreate(items: MutableList<T>?) {
        synchronized(lock) {
            observer.onCreate(items)
        }
    }

    override fun onRead(items: MutableList<T>?) {
        synchronized(lock) {
            observer.onRead(items)
        }
    }

    override fun onUpdate(items: MutableList<T>?) {
        synchronized(lock) {
            observer.onUpdate(items)
        }
    }

    override fun onDelete(items: MutableList<T>?) {
        synchronized(lock) {
            observer.onDelete(items)
        }
    }

}

/**
 * Returns a an observer with logging added to all the events.
 */
fun <T> ChangeObserver<T>.withLogging(
    logTag: String,
    logTypeName: String
): ChangeObserver<T> = LoggingDecorator(this, logTag, logTypeName)

/**
 * Returns an observer that is synchronized with [lock] on all events.
 *
 * If lock is omitted a new [Any] is created as the lock object.
 */
@JvmOverloads
fun <T> ChangeObserver<T>.withSynchronization(
    lock: Any = Any()
): ChangeObserver<T> = SynchronizedDecorator(this, lock)
