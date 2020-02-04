package com.tink.pfmsdk.extensions

internal fun Boolean?.isTrue(): Boolean {
    return this == true
}

internal fun Boolean?.isNullOrFalse(): Boolean {
    return this != true
}