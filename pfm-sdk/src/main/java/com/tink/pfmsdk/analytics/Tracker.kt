package com.tink.pfmsdk.analytics

interface Tracker {
    fun track(event: AnalyticsEvent)
    fun track(screen: AnalyticsScreen)
}