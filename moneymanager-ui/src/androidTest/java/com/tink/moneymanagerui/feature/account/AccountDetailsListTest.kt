package com.tink.moneymanagerui.feature.account

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tink.model.account.Account
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.accounts.AccountEditConfiguration
import com.tink.moneymanagerui.accounts.AccountGroupByKind
import com.tink.moneymanagerui.accounts.AccountGroupType
import com.tink.moneymanagerui.accounts.NoAccountGroup
import com.tink.moneymanagerui.accounts.OverviewAccountsMode
import com.tink.moneymanagerui.accounts.OverviewFavoriteAccounts
import com.tink.moneymanagerui.mock.AccountMockFactory
import com.tink.moneymanagerui.testutil.CustomMatchers
import com.tink.moneymanagerui.testutil.RecyclerViewItemCountAssertion
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AccountDetailsListTest: BaseAccountTestSuit() {
    private val defaultAccounts = mutableListOf(
        AccountMockFactory.getAccount(id = "1", favored = true, excluded = true),
        AccountMockFactory.getAccount(id = "2", favored = false),
        AccountMockFactory.getAccount(id = "3", favored = true, ownership = 0.5),
        AccountMockFactory.getAccount(id = "4", favored = false),
        AccountMockFactory.getAccount(id = "5", favored = true),
        AccountMockFactory.getAccount(id = "6", favored = true)
    )

    fun openAccountDetailsListWithAcoounts(
        accounts: List<JSONObject>,
        overviewAccountsMode: OverviewAccountsMode = OverviewFavoriteAccounts,
        accountGroupType: AccountGroupType = NoAccountGroup,
        accountEditConfiguration: AccountEditConfiguration = AccountEditConfiguration.ALL
    ) {
        openOverviewWithAcoounts(accounts, overviewAccountsMode, accountGroupType, accountEditConfiguration)
        navigator.fromOverviewToAccountDetailsList()
    }


    @Test
    fun shows_all_account_when_no_grouping() {
        openAccountDetailsListWithAcoounts(accounts = defaultAccounts)

        checkVisibleViews(
            displayAllList = true,
            displayGroupList = false
        )

        onView(withId(R.id.allAccountsList))
            .check(RecyclerViewItemCountAssertion(defaultAccounts.size))
    }

    @Test
    fun shows_account_groups_when_grouping() {
        val defaultAccounts = mutableListOf(
            AccountMockFactory.getAccount(id = "1", type = Account.Type.SAVINGS.toString()),
            AccountMockFactory.getAccount(id = "2", type = Account.Type.CHECKING.toString()),
            AccountMockFactory.getAccount(id = "3", type = Account.Type.SAVINGS.toString()),
            AccountMockFactory.getAccount(id = "4", type = Account.Type.CREDIT_CARD.toString()),
            AccountMockFactory.getAccount(id = "5", type = Account.Type.SAVINGS.toString()),
            AccountMockFactory.getAccount(id = "6", type = Account.Type.OTHER.toString()),
            AccountMockFactory.getAccount(id = "7", type = Account.Type.CREDIT_CARD.toString()),
        )

        openAccountDetailsListWithAcoounts(
            accounts = defaultAccounts,
            accountGroupType = AccountGroupByKind
        )

        checkVisibleViews(
            displayAllList = false,
            displayGroupList = true
        )

//        onView(withId(R.id.groupedAccountsList))
//            .check(RecyclerViewItemCountAssertion(defaultAccounts.size))
    }

    private fun checkVisibleViews(
        displayAllList: Boolean,
        displayGroupList: Boolean,) {
        onView(withId(R.id.allAccountsList))
            .check(ViewAssertions.matches(CustomMatchers.isDisplayedIf(displayAllList)))
        onView(withId(R.id.groupedAccountsList))
            .check(ViewAssertions.matches(CustomMatchers.isDisplayedIf(displayGroupList)))
    }

}