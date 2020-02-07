package com.tink.pfmsdk.buildConfig

import com.tink.pfmsdk.BuildConfig
import com.tink.pfmsdk.buildConfig.Feature.*
import timber.log.Timber

internal interface FeatureConfigurations {
    fun hasFeature(feature: Feature): Boolean
}

internal interface LoggingConfigurations {
    fun shouldStartFabric(): Boolean
    fun shouldLogTracking(): Boolean
    fun getTimberTree(): Timber.Tree
}

internal class BuildConfiguration(val featureConfigurations: FeatureConfigurations,
                         val loggingConfigurations: LoggingConfigurations,
                         val supportedLangugaes: List<String>,
                         val googleAnalyticsKey: String)

internal object BuildConfigurations {

    @Deprecated("Use DI")
    val instance = if (BuildConfig.DEBUG) {
        BuildConfiguration(
                featureConfigurations = getFeatureConfigurations(),
                loggingConfigurations = getLoggingConfigurations(),
                supportedLangugaes = listOf("it_IT", "en_US"),
                googleAnalyticsKey = "UA-34242137-6")
    } else {
        BuildConfiguration(
                featureConfigurations = getFeatureConfigurations(),
                loggingConfigurations = getLoggingConfigurations(),
                supportedLangugaes = listOf("it_IT", "en_US"),
                googleAnalyticsKey = "UA-34242137-3")
    }

}

private fun getFeatureConfigurations(): FeatureConfigurations {
    return object : FeatureConfigurations {
        override fun hasFeature(feature: Feature): Boolean {
            return when (feature) {
                LEFT_TO_SPEND -> true
                TAG_VIEW -> false
                FAB -> true
                SAVINGS_GOAL -> true
                ID_CONTROL -> true
                TOUCH_ID_LOGIN -> true
                DISK_CACHE -> true
                BLOCK_SCREENSHOTS -> false
                LOANS -> true
                FORCE_PASSWORD_PROTECTION -> false
                MORTGAGE -> true
            }
        }
    }
}

private fun getLoggingConfigurations(): LoggingConfigurations {
    return object : LoggingConfigurations {
        override fun shouldStartFabric() = !BuildConfig.DEBUG
        override fun shouldLogTracking() = BuildConfig.DEBUG
        override fun getTimberTree() = if(BuildConfig.DEBUG) {
            Timber.DebugTree()
        } else {
            object : Timber.Tree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    //NOOP
                }
            }
        }
    }
}
