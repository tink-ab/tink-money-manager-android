package com.tink.moneymanagerui.charts.extensions

internal fun <E> MutableList<E>.addNonNull(item: E?) = item?.let { add(it) }

internal inline fun <T> Iterable<T>.sumByFloat(selector: (T) -> Float): Float {
    var sum = 0.0f
    for (element in this) {
        sum += selector(element)
    }
    return sum
}
