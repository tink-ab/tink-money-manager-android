package se.tink.android.tink_pfm_sdk_android

import com.tink.pfmsdk.analytics.AnalyticsEvent
import com.tink.pfmsdk.analytics.AnalyticsScreen
import com.tink.pfmsdk.analytics.Tracker
import timber.log.Timber

private const val TAG = "LogTracker"

/**
 * [Tracker] implementation that prints the analytics events to the log.
 */
class LogTracker : Tracker {
    override fun track(event: AnalyticsEvent) {
        Timber.tag(TAG).d(
            "Track event - Category: %s, Action: %s, Label: %s",
            event.category,
            event.action,
            event.label
        )
    }

    override fun track(screen: AnalyticsScreen) {
        Timber.tag(TAG).d("Track screen - Screen name: %s", screen.name)
    }
}