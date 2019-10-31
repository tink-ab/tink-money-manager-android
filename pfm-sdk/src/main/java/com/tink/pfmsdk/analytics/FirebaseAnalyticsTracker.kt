package com.tink.pfmsdk.analytics

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import se.tink.core.extensions.whenNonNull

private const val EVENT_LABEL = "label"
private const val EVENT_CATEGORY = "category"
private const val EVENT_SCREEN_NAME = "screen_name"

private const val SCREEN_EVENT = "screen_view_custom"

class FirebaseAnalyticsTracker(
    val context: Context
) : SimpleTracker() {

    private val firebaseAnalytics = FirebaseAnalytics.getInstance(context)

    override fun registerUser(identifier: String?, username: String?) {
        firebaseAnalytics.setUserId(identifier)
    }

    override fun track(screen: AnalyticsScreen?, activity: Activity) {
        screen?.let {
            firebaseAnalytics.setCurrentScreen(activity, it.name, null)
        }
    }

    override fun track(
        screen: AnalyticsScreen?,
        parameters: MutableMap<TrackerParameter, String>?,
        activity: Activity
    ) {
        whenNonNull(
            screen?.name,
            parameters
        ) { screenName, trackerParameters ->
            val bundle = Bundle().apply {
                putString(EVENT_SCREEN_NAME, screenName)
                for ((key, value) in trackerParameters) {
                    putString(key.name, value)
                }
            }
            firebaseAnalytics.logEvent(SCREEN_EVENT, bundle)
        }
        track(screen, activity)
    }

    override fun track(event: AnalyticsEvent?) {
        event?.let {
            val bundle = Bundle().apply {
                putString(EVENT_CATEGORY, it.category)
                putString(EVENT_LABEL, it.label)
            }
            firebaseAnalytics.logEvent(processActionLabelForFirebase(it.action), bundle)
        }
    }

    override fun track(event: AnalyticsEvent?, parameters: Map<TrackerParameter, String>?) {
        whenNonNull(
            event,
            parameters
        ) { (category, label, action), eventParameters ->
            val bundle = Bundle().apply {
                putString(EVENT_CATEGORY, category)
                putString(EVENT_LABEL, label)
                for ((key, value) in eventParameters) {
                    putString(key.name, value)
                }
            }
            firebaseAnalytics.logEvent(processActionLabelForFirebase(action), bundle)
        }
    }

    private fun processActionLabelForFirebase(string: String) =
        string.toLowerCase().replace(" ", "_")
}
