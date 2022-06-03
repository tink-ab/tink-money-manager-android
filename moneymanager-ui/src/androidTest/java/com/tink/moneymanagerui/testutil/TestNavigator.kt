package com.tink.moneymanagerui.testutil

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.overview.accounts.AccountItemViewHolder

class TestNavigator {
    /**
     * Requires list of insights to be non-empty
     */
    fun fromOverviewToInsight() {
        onView(ViewMatchers.withId(R.id.insightsCardContent))
            .perform(click())
    }

    fun fromOverviewToAccountDetailsList() {
        onView(ViewMatchers.withId(R.id.seeAllAccounts))
            .perform(click())
    }

    fun fromOverviewToAccountDetails() {
        onView(ViewMatchers.withId(R.id.accountList))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<AccountItemViewHolder>(
                    0, click()
                )
            )
    }

    fun fromOverviewToEditAccountDetails() {
        fromOverviewToAccountDetails()
        onView(ViewMatchers.withId(R.id.action_edit))
            .perform(click())
    }

    fun backWithToolbar() {
        onView(withContentDescription(R.string.abc_action_bar_up_description))
            .perform(click())
    }
}
