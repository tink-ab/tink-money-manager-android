package com.tink.pfmsdk.analytics

import android.app.Activity


open class SimpleTracker : Tracker {

    override fun registerUser(identifier: String?, username: String?) {

    }

    override fun track(event: AnalyticsEvent?) {

    }

    override fun track(event: AnalyticsEvent?, parameters: Map<TrackerParameter, String>?) {

    }

    override fun track(screen: AnalyticsScreen?, activity: Activity) {

    }

    override fun track(
        screen: AnalyticsScreen?,
        parameters: MutableMap<TrackerParameter, String>?,
        activity: Activity
    ) {

    }

    override fun track(screen: String?) {

    }
}