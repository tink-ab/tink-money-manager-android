package com.tink.moneymanagerui.feature.insight

import com.tink.service.network.Environment


sealed class EnvironmentType {
    abstract val sampleOAuthClientId: String
    abstract val sampleEnvironment: Environment

    data class UiTest(
        override val sampleOAuthClientId: String = "12345",
        override val sampleEnvironment: Environment = Environment.Custom(
            "https://testing.pfm.se",
            null
        )
    ) : EnvironmentType()

}

object TestConfiguration {
    private val environmentType = EnvironmentType.UiTest()
    val sampleOAuthClientId: String = environmentType.sampleOAuthClientId
    val sampleEnvironment = environmentType.sampleEnvironment
    val sampleAccessToken: String = "000"
}
