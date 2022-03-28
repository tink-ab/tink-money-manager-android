package com.tink.moneymanagerui.buildConfig

import com.tink.moneymanagerui.BuildConfig
import timber.log.Timber

internal interface FeatureConfigurations {
    fun hasFeature(feature: Feature): Boolean
}

internal interface LoggingConfigurations {
    fun shouldStartFabric(): Boolean
    fun shouldLogTracking(): Boolean
    fun getTimberTree(): Timber.Tree
}

internal class BuildConfiguration(
    val featureConfigurations: FeatureConfigurations,
    val loggingConfigurations: LoggingConfigurations,
    val googleAnalyticsKey: String
)

internal object BuildConfigurations {

    @Deprecated("Use DI")
    val instance = if (BuildConfig.DEBUG) {
        BuildConfiguration(
            featureConfigurations = getFeatureConfigurations(),
            loggingConfigurations = getLoggingConfigurations(),
            googleAnalyticsKey = "UA-34242137-6"
        )
    } else {
        BuildConfiguration(
            featureConfigurations = getFeatureConfigurations(),
            loggingConfigurations = getLoggingConfigurations(),
            googleAnalyticsKey = "UA-34242137-3"
        )
    }
}

private fun getFeatureConfigurations(): FeatureConfigurations {
    return object : FeatureConfigurations {
        override fun hasFeature(feature: Feature): Boolean {
            return when (feature) {
                Feature.LEFT_TO_SPEND -> true
                Feature.TAG_VIEW -> false
                Feature.FAB -> true
                Feature.SAVINGS_GOAL -> true
                Feature.ID_CONTROL -> true
                Feature.TOUCH_ID_LOGIN -> true
                Feature.DISK_CACHE -> true
                Feature.BLOCK_SCREENSHOTS -> false
                Feature.LOANS -> true
                Feature.FORCE_PASSWORD_PROTECTION -> false
                Feature.MORTGAGE -> true
            }
        }
    }
}

private fun getLoggingConfigurations(): LoggingConfigurations {
    return object : LoggingConfigurations {
        override fun shouldStartFabric() = !BuildConfig.DEBUG
        override fun shouldLogTracking() = BuildConfig.DEBUG
        override fun getTimberTree() = if (BuildConfig.DEBUG) {
            Timber.DebugTree()
        } else {
            object : Timber.Tree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    // NOOP
                }
            }
        }
    }
}
