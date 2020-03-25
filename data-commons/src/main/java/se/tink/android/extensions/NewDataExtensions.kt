package se.tink.android.extensions

import com.tink.service.observer.ListChangeObserver
import se.tink.repository.ChangeObserver

@Deprecated("TODO: Core setup - Remove this")
fun <T : Any?> ChangeObserver<T>.toListChangeObserver(): ListChangeObserver<T> =
    let {
        object : ListChangeObserver<T> {
            override fun onCreate(items: List<T>) = it.onCreate(items)
            override fun onDelete(items: List<T>) = it.onDelete(items)
            override fun onRead(items: List<T>) = it.onRead(items)
            override fun onUpdate(items: List<T>) = it.onUpdate(items)
        }
    }
