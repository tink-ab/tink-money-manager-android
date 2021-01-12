package se.tink.android.extensions

import com.tink.service.handler.ResultHandler
import io.reactivex.subjects.Subject

fun <T> Subject<T>.toResultHandler(): ResultHandler<T> = ResultHandler(::onNext, ::onError)
