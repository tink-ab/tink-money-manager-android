package com.tink.moneymanagerui.testutil

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher

object CustomMatchers {
    fun isDisplayedIf(shouldBeDisplayed: Boolean): Matcher<View> {
        return if (shouldBeDisplayed) {
            ViewMatchers.isDisplayed()
        } else {
            CoreMatchers.not(ViewMatchers.isDisplayed())
        }
    }
}
