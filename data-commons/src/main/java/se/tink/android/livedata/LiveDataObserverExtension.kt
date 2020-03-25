package se.tink.android.livedata

import androidx.lifecycle.MutableLiveData
import com.tink.service.handler.ResultHandler
import com.tink.service.observer.ListChangeObserver
import se.tink.android.AppExecutors
import se.tink.android.repository.TinkNetworkError

// T must provide equals method which is not based on objects identity
fun <T> MutableLiveData<List<T>>.createChangeObserver(executors: AppExecutors): ListChangeObserver<T> {
    return object : ListChangeObserver<T> {
        override fun onCreate(items: List<T>) = doUpdate(items) { value, created ->
            value.addAll(created)
        }

        override fun onRead(items: List<T>) = postValue(items)

        override fun onUpdate(items: List<T>) = doUpdate(items) { value, updated ->
            value.run {
                removeAll(updated)
                addAll(updated)
            }
        }

        override fun onDelete(items: List<T>) = doUpdate(items) { value, deleted ->
            value.removeAll(deleted)
        }

        private fun doUpdate(
            items: List<T>?,
            action: (value: MutableList<T>, items: List<T>) -> Unit
        ) {
            executors.mainThreadExecutor.execute {
                items?.let { created ->
                    value = (value ?: mutableListOf()).toMutableList().also {
                        action(it, created)
                    }
                }
            }
        }
    }
}

fun <T> MutableLiveData<ErrorOrValue<T>>.createResultHandler() = ResultHandler<T>(
    onSuccess = { item ->
        postValue(ErrorOrValue(item))
    },
    onError = {
        postValue(ErrorOrValue(TinkNetworkError(it)))
    }
)

class ErrorOrValue<T>
private constructor(
    val value: T?,
    val error: TinkNetworkError?
) {

    constructor(value: T) : this(value, null)
    constructor(error: TinkNetworkError) : this(null, error)

}
