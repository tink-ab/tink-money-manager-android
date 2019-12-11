package com.tink.pfmsdk.analytics

interface Tracker {
    fun track(actionEvent: ActionEvent)
    fun track(screenEvent: ScreenEvent)
}