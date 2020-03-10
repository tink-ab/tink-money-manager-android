package se.tink.android.tink_pfm_sdk_android

import com.tink.pfmui.tracking.ScreenEvent
import com.tink.pfmui.tracking.Tracker
import timber.log.Timber

private const val TAG = "LogTracker"

/**
 * [Tracker] implementation that prints the events to the log.
 */
class LogTracker : Tracker {
    override fun track(screenEvent: ScreenEvent) {
        Timber.tag(TAG).d("Track screen - Screen name: %s", screenEvent.name)
    }
}