package com.tink.pfmsdk.tracking

interface Tracker {
    fun track(screenEvent: ScreenEvent)
}