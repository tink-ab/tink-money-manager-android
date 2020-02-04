package com.tink.pfmsdk

typealias Timezone = String

internal object TimezoneManager {
    @JvmField
    var defaultTimezone: Timezone = "Europe/Stockholm"
}
