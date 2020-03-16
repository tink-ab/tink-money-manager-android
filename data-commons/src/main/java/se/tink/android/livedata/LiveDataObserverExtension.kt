package se.tink.android.livedata

import androidx.lifecycle.MutableLiveData
import com.tink.service.handler.ResultHandler
import se.tink.android.AppExecutors
import se.tink.repository.ChangeObserver
import se.tink.repository.MutationHandler
import se.tink.repository.TinkNetworkError

// T must provide equals method which is not based on objects identity
fun <T> MutableLiveData<List<T>>.createChangeObserver(executors: AppExecutors): ChangeObserver<T> {
    return object : ChangeObserver<T> {
        override fun onCreate(items: MutableList<T>?) = doUpdate(items) { value, created ->
            value.addAll(created)
        }

        override fun onRead(items: MutableList<T>?) = postValue(items)

        override fun onUpdate(items: MutableList<T>?) = doUpdate(items) { value, updated ->
            value.run {
                removeAll(updated)
                addAll(updated)
            }
        }

        override fun onDelete(items: MutableList<T>?) = doUpdate(items) { value, deleted ->
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

fun <T> MutableLiveData<ErrorOrValue<T>>.createMutationHandler() = object : MutationHandler<T> {

    override fun onError(error: TinkNetworkError?) {
        error?.let { postValue(ErrorOrValue(it)) }
    }

    override fun onCompleted() {
        //implement if necessary
    }

    override fun onNext(item: T) {
        postValue(ErrorOrValue(item))
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
