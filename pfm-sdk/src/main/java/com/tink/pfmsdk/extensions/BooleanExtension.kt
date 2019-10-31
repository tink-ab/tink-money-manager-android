package com.tink.pfmsdk.extensions

fun Boolean?.isTrue(): Boolean {
    return this == true
}

fun Boolean?.isNullOrFalse(): Boolean {
    return this != true
}