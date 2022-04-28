package com.tink.moneymanagerui.feature.account

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.isNotChecked
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.accounts.AccountEditConfiguration
import com.tink.moneymanagerui.accounts.AccountGroupType
import com.tink.moneymanagerui.accounts.NoAccountGroup
import com.tink.moneymanagerui.accounts.OverviewAccountsMode
import com.tink.moneymanagerui.accounts.OverviewFavoriteAccounts
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EditAccountTest : BaseAccountTestSuit() {
    private fun openAccountEditWithAcoount(
        account: JSONObject,
        overviewAccountsMode: OverviewAccountsMode = OverviewFavoriteAccounts,
        accountGroupType: AccountGroupType = NoAccountGroup,
        accountEditConfiguration: AccountEditConfiguration = AccountEditConfiguration.ALL
    ) {
        openOverviewWithAcoounts(listOf(account), overviewAccountsMode, accountGroupType, accountEditConfiguration)
        navigator.fromOverviewToEditAccountDetails()
    }

    @Test
    fun shows_correct_settings() {
        openAccountEditWithAcoount(account = defaultAccount)

        onView(withId(R.id.nameInputText))
            .check(
                matches(withText(defaultAccount["name"].toString()))
            )

        val expectedType = "Loan"
        onView(withId(R.id.typeInputText))
            .check(
                matches(withText(expectedType))
            )

        onView(withId(R.id.includedSwitch))
            .check(getSwitchMatcher(!defaultAccount["excluded"].toString().toBoolean()))

        onView(withId(R.id.favoriteSwitch))
            .check(getSwitchMatcher(defaultAccount["favored"].toString().toBoolean()))

        val expectShared = defaultAccount["ownership"].toString() == "0.5"
        onView(withId(R.id.sharedSwitch))
            .check(getSwitchMatcher(expectShared))
    }

    private fun getSwitchMatcher(expectChecked: Boolean): ViewAssertion {
        return if (expectChecked) {
            matches(isChecked())
        } else {
            matches(isNotChecked())
        }
    }
}
