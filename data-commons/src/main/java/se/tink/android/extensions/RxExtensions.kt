package se.tink.android.extensions

import com.tink.service.handler.ResultHandler
import io.reactivex.subjects.Subject
import se.tink.repository.ChangeObserver
import se.tink.repository.MutationHandler
import se.tink.repository.ObjectChangeObserver
import se.tink.repository.TinkNetworkError

fun <T> Subject<T>.toMutationHandler(): MutationHandler<T> {
    return object : MutationHandler<T> {
        override fun onError(error: TinkNetworkError?) {
            error?.let { this@toMutationHandler.onError(it) }
        }

        override fun onCompleted() {
            this@toMutationHandler.onComplete()
        }

        override fun onNext(item: T?) {
            item?.let { this@toMutationHandler.onNext(it) }
        }
    }
}

fun <T> Subject<T>.toObjectChangeObserver(): ObjectChangeObserver<T> {
    return object : ObjectChangeObserver<T> {
        override fun onCreate(item: T) {
            onNext(item)
        }

        override fun onRead(item: T) {
            onNext(item)
        }

        override fun onUpdate(item: T) {
            onNext(item)
        }

        override fun onDelete(item: T) {
            // Do nothing
        }

    }
}

fun <T> Subject<List<T>>.toChangeObserver(): ChangeObserver<T> {
    return object : ChangeObserver<T> {

        private val items: MutableList<T> = mutableListOf()

        @Synchronized
        override fun onCreate(items: MutableList<T?>?) {
            for (item in items ?: mutableListOf()) {
                item?.let { this.items += it }
            }

            onNext(this.items.toList())
        }

        @Synchronized
        override fun onRead(items: MutableList<T?>?) {
            this.items.clear()

            for (item in items ?: mutableListOf()) {
                item?.let { this.items += it }
            }

            onNext(this.items.toList())
        }

        @Synchronized
        override fun onUpdate(items: MutableList<T?>?) {
            for (item in items ?: mutableListOf()) {
                item?.let {
                    this.items -= it
                    this.items += it
                }
            }

            onNext(this.items.toList())
        }

        @Synchronized
        override fun onDelete(items: MutableList<T?>?) {
            for (item in items ?: mutableListOf()) {
                item?.let { this.items -= it }
            }

            onNext(this.items.toList())
        }
    }
}

fun <T> Subject<T>.toResultHandler(): ResultHandler<T> = ResultHandler(::onNext, ::onError)
