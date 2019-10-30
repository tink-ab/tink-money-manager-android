package se.tink.modelConverter

import java.lang.reflect.ParameterizedType

abstract class AbstractConverter<S, D> {

    @Suppress("UNCHECKED_CAST")
    open fun getSourceClass(): Class<S> =
        (requireNotNull(javaClass.genericSuperclass) as ParameterizedType)
            .actualTypeArguments[0] as Class<S>

    @Suppress("UNCHECKED_CAST")
    open fun getDestinationClass(): Class<D> =
        (requireNotNull(javaClass.genericSuperclass) as ParameterizedType)
            .actualTypeArguments[1] as Class<D>

    abstract fun convert(source: S): D
}
