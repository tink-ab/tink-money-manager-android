package com.tink.moneymanagerui.testutil

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.tink.moneymanagerui.R

class TestNavigator {
    /**
     * Requires list of insights to
     */
    fun fromOverviewToInsight() {
        Espresso.onView(ViewMatchers.withId(R.id.insightsCardContent))
            .perform(ViewActions.click())
    }
}