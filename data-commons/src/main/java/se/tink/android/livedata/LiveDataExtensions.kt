package se.tink.android.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations

fun <X : Any?, Y : Any?> LiveData<X>.map(mapFunction: (X) -> (Y)): LiveData<Y> =
    Transformations.map(this, mapFunction)

fun <X : Any?, Y : Any?> LiveData<X>.switchMap(mapFunction: (X) -> (LiveData<Y>)): LiveData<Y> =
    Transformations.switchMap(this, mapFunction)

fun <T, R> mapDistinct(source: LiveData<T>, map: (T) -> R): LiveData<R> = MediatorLiveData<R>().apply {
    addSource(source) {
        it?.let {
            val res = map(it)
            if (value != res) value = res
        }
    }
}

fun <T: Any> LiveData<T>.observe(owner: LifecycleOwner, observer: (T) -> Unit) =
    observe(owner, Observer { it?.let(observer) })

val <T> LiveData<T>.requireValue: T
    get() = value!!