package com.tink.moneymanagerui

typealias Timezone = String

internal object TimezoneManager {
    @JvmField
    var defaultTimezone: Timezone = "Europe/Stockholm"
}
