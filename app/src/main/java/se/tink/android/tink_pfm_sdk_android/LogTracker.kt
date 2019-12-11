package se.tink.android.tink_pfm_sdk_android

import com.tink.pfmsdk.tracking.ActionEvent
import com.tink.pfmsdk.tracking.ScreenEvent
import com.tink.pfmsdk.tracking.Tracker
import timber.log.Timber

private const val TAG = "LogTracker"

/**
 * [Tracker] implementation that prints the events to the log.
 */
class LogTracker : Tracker {
    override fun track(actionEvent: ActionEvent) {
        Timber.tag(TAG).d(
            "Track event - Category: %s, Action: %s, Label: %s",
            actionEvent.category,
            actionEvent.action,
            actionEvent.label
        )
    }

    override fun track(screenEvent: ScreenEvent) {
        Timber.tag(TAG).d("Track screen - Screen name: %s", screenEvent.name)
    }
}