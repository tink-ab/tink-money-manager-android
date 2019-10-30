package se.tink.repository.cache

import se.tink.repository.ChangeObserver
import se.tink.repository.MutationHandler

interface WritableCacheRepository<T> {

    fun clear(handler: MutationHandler<Void>)

    fun read(handler: MutationHandler<List<T>>)

    fun read(changeObserver: ChangeObserver<T>)

    fun reset(objects: List<T>, handler: MutationHandler<List<T>>)

    fun update(objects: List<T>, handler: MutationHandler<List<T>>)

    fun update(obj: T, handler: MutationHandler<T>)

    fun add(objects: List<T>, handler: MutationHandler<List<T>>)

    fun add(obj: T, handler: MutationHandler<T>)

    fun delete(objects: List<T>, handler: MutationHandler<List<T>>)

    fun delete(obj: T, handler: MutationHandler<Void>)
}
