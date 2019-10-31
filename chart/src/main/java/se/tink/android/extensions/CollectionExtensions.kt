package se.tink.android.extensions

fun <E> MutableList<E>.addNonNull(item: E?) = item?.let { add(it) }

fun <K, V> MutableMap<K, V>.mergeValue(key: K, value: V, remappingFunction: (v1: V, v2: V) -> V) {
    val oldValue = get(key)
    if (oldValue != null) {
        put(key, remappingFunction(oldValue, value))
    } else {
        put(key, value)
    }
}

public inline fun <T> Iterable<T>.sumByFloat(selector: (T) -> Float): Float {
    var sum = 0.0f
    for (element in this) {
        sum += selector(element)
    }
    return sum
}