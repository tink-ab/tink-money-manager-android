package com.tink.moneymanagerui

import com.tink.service.network.Environment

class TestConfiguration(url: String) {
    val sampleOAuthClientId: String = "12345"
    var sampleEnvironment = Environment.Custom(url, null)
    val sampleAccessToken: String = "000"
}
