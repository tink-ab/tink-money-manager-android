package com.tink.pfmsdk.tracking

interface Tracker {
    fun track(actionEvent: ActionEvent)
    fun track(screenEvent: ScreenEvent)
}