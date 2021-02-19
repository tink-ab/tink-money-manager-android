package se.tink.android.extensions

import com.tink.service.handler.ResultHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// TODO: Move this to Core
internal fun <T> CoroutineScope.launchForResult(
    resultHandler: ResultHandler<T>,
    getResult: suspend () -> T
) {
    launch {
        try {
            resultHandler.onSuccess(getResult())
        } catch (error: Throwable) {
            resultHandler.onError(error)
        }
    }
}
