package com.tink.pfmsdk.util

private const val DEFAULT = 10

internal object DynamicRoundingThresholds {

    val map: Map<String, Int> = mapOf(
        "SEK" to 10,
        "EUR" to 100,
        "GBP" to 100
    )

    fun get(currencyCode: String): Int {
        return map.takeIf { it.containsKey(currencyCode) }?.get(currencyCode) ?: DEFAULT
    }
}


