package com.tink.moneymanagerui.feature.account

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.accounts.OverviewCustomAccounts
import com.tink.moneymanagerui.mock.AccountMockFactory
import com.tink.moneymanagerui.testutil.CustomMatchers.isDisplayedIf
import com.tink.moneymanagerui.testutil.RecyclerViewItemCountAssertion
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AccountOverviewListTest : BaseAccountTestSuit() {

    @Test
    fun shows_see_accounts_button_when_no_accounts() {
        openOverviewWithAcoounts(emptyList())
        checkVisibleViews(
            displayList = false,
            displaysmallShowAllButton = false,
            displayLargeShowAllButton = true
        )
    }

    @Test
    fun shows_see_accounts_button_when_no_favorit_accounts() {
        openOverviewWithAcoounts(
            listOf(
                AccountMockFactory.getAccount(id = "1", favored = false),
                AccountMockFactory.getAccount(id = "2", favored = false)
            )
        )
        checkVisibleViews(
            displayList = false,
            displaysmallShowAllButton = false,
            displayLargeShowAllButton = true
        )
    }

    @Test
    fun shows_see_all_button_when_one_account_is_not_favorite() {
        openOverviewWithAcoounts(
            listOf(
                AccountMockFactory.getAccount(id = "1", favored = true),
                AccountMockFactory.getAccount(id = "2", favored = false)
            )
        )
        checkVisibleViews(
            displayList = true,
            displaysmallShowAllButton = true,
            displayLargeShowAllButton = false
        )
    }

    @Test
    fun list_shows_all_favorite_accounts() {
        val accounts = listOf(
            AccountMockFactory.getAccount(id = "1", favored = true),
            AccountMockFactory.getAccount(id = "2", favored = true),
            AccountMockFactory.getAccount(id = "3", favored = true),
            AccountMockFactory.getAccount(id = "4", favored = false),
            AccountMockFactory.getAccount(id = "5", favored = true)
        )
        openOverviewWithAcoounts(accounts)

        checkVisibleViews(
            displayList = true,
            displaysmallShowAllButton = true,
            displayLargeShowAllButton = false
        )

        val accountsToShow = accounts.filter { it["favored"] as Boolean }.size

        onView(withId(R.id.accountList))
            .check(RecyclerViewItemCountAssertion(accountsToShow))
    }

    @Test
    fun list_shows_all_filtered_accounts() {
        val accounts = listOf(
            AccountMockFactory.getAccount(id = "1", favored = true),
            AccountMockFactory.getAccount(id = "2", favored = true),
            AccountMockFactory.getAccount(id = "3", favored = true),
            AccountMockFactory.getAccount(id = "4", favored = false),
            AccountMockFactory.getAccount(id = "5", favored = true)
        )
        openOverviewWithAcoounts(
            accounts,
            OverviewCustomAccounts {
                it.account.id == "2" || it.account.id == "4"
            }
        )

        checkVisibleViews(
            displayList = true,
            displaysmallShowAllButton = true,
            displayLargeShowAllButton = false
        )

        val accountsToShow = accounts.filter {
            it["id"] == "2" || it["id"] == "4"
        }.size

        onView(withId(R.id.accountList))
            .check(RecyclerViewItemCountAssertion(accountsToShow))
    }

    private fun checkVisibleViews(
        displayList: Boolean,
        displaysmallShowAllButton: Boolean,
        displayLargeShowAllButton: Boolean
    ) {
        onView(withId(R.id.accountList))
            .check(matches(isDisplayedIf(displayList)))
        onView(withId(R.id.seeAllAccounts))
            .check(matches(isDisplayedIf(displaysmallShowAllButton)))
        onView(withText(R.string.tink_overview_favorite_accounts_see_all_accounts))
            .check(matches(isDisplayedIf(displayLargeShowAllButton)))
    }
}
