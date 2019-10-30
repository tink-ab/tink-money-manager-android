package se.tink.repository.cache

import se.tink.privacy.Clearable
import se.tink.privacy.DataWipeManager
import se.tink.repository.ChangeObserver
import se.tink.repository.MutationHandler

class InMemoryCache<T> : WritableCacheRepository<T>, Clearable {

    val cache: MutableSet<T> = mutableSetOf()

    init {
        DataWipeManager.sharedInstance().register(this)
    }

    @Synchronized
    override fun reset(objects: List<T>, handler: MutationHandler<List<T>>) {
        cache.clear()
        cache.addAll(objects)
        handler.onNext(cache.toList())
        handler.onCompleted()
    }

    @Synchronized
    override fun add(
        objects: List<T>,
        handler: MutationHandler<List<T>>
    ) {
        cache.addAll(objects)
        handler.onNext(cache.toList())
        handler.onCompleted()
    }

    @Synchronized
    override fun delete(
        objects: List<T>,
        handler: MutationHandler<List<T>>
    ) {
        val shouldBeRemoved = cache.intersect(objects)
        cache.removeAll(shouldBeRemoved)
        handler.onNext(cache.toList())
        handler.onCompleted()
    }

    @Synchronized
    override fun clear(handler: MutationHandler<Void>) {
        clear()
        handler.onCompleted()
    }

    @Synchronized
    override fun read(handler: MutationHandler<List<T>>) {
        handler.onNext(cache.toList())
        handler.onCompleted()
    }

    @Synchronized
    override fun read(changeObserver: ChangeObserver<T>) {
        if (!cache.isEmpty()) {
            changeObserver.onRead(cache.toList())
        }
    }

    @Synchronized
    override fun update(obj: T, handler: MutationHandler<T>) {
        cache.remove(obj)
        cache.add(obj)
        handler.onNext(obj)
        handler.onCompleted()
    }

    @Synchronized
    override fun update(
        objects: List<T>,
        handler: MutationHandler<List<T>>
    ) {
        val shouldBeRemoved = cache.intersect(objects)

        cache.removeAll(shouldBeRemoved)
        cache.addAll(objects)

        handler.onCompleted()
    }

    @Synchronized
    override fun add(obj: T, handler: MutationHandler<T>) {
        cache.add(obj)
        handler.onNext(obj)
        handler.onCompleted()
    }

    @Synchronized
    override fun delete(obj: T, handler: MutationHandler<Void>) {
        cache.remove(obj)
        handler.onCompleted()
    }

    @Synchronized
    override fun clear() {
        cache.clear()
    }
}
