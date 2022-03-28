package com.tink.moneymanagerui.tracking

/**
 * An interface that allows users of the SDK to track the various [ScreenEvent] events in the finance overview UI.
 *
 * Users of the SDK can implement this interface with their tracking logic and pass the implementation as input when
 * creating a new instance of the [FinanceOverviewFragment].
 */
interface Tracker {
    fun track(screenEvent: ScreenEvent)
}
