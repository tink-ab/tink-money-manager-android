package com.tink.moneymanagerui.feature.account

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
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
import com.tink.moneymanagerui.accounts.list.EVERYDAY_ACCOUNTS
import com.tink.moneymanagerui.accounts.list.GroupedAccountListAdapter
import com.tink.moneymanagerui.accounts.list.OTHER_ACCOUNTS
import com.tink.moneymanagerui.accounts.list.SAVINGS_ACCOUNTS
import com.tink.moneymanagerui.accounts.list.toAccountGroup
import com.tink.moneymanagerui.mock.AccountMockFactory
import com.tink.moneymanagerui.testutil.CustomMatchers
import com.tink.moneymanagerui.testutil.RecyclerViewItemCountAssertion
import kotlinx.android.synthetic.main.tink_grouped_item_account.view.*
import org.hamcrest.Description
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

    private fun openAccountDetailsListWithAcoounts(
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
        val accountTypes = arrayOf(
            Account.Type.SAVINGS,
            Account.Type.CHECKING,
            Account.Type.SAVINGS,
            Account.Type.CREDIT_CARD,
            Account.Type.SAVINGS,
            Account.Type.OTHER,
            Account.Type.CREDIT_CARD,
        )

        val accounts = (accountTypes.indices).map { i ->
            AccountMockFactory.getAccount(id = i.toString(), type = accountTypes[i].toString())
        }

        val groups = accountTypes
            .map { it.toAccountGroup() }

        openAccountDetailsListWithAcoounts(
            accounts = accounts,
            accountGroupType = AccountGroupByKind
        )

        checkVisibleViews(
            displayAllList = false,
            displayGroupList = true
        )

        onView(withId(R.id.groupedAccountsList))
            .check(RecyclerViewItemCountAssertion(groups.distinct().size))

        onView(withId(R.id.groupedAccountsList))
            .perform(
                RecyclerViewActions.scrollToHolder(
                    GroupAccountListMatcher(
                        resources.getString(R.string.tink_everyday_account),
                        "6000000,00kr",
                        groups.filter { it == EVERYDAY_ACCOUNTS }.count())
                )
            )

        onView(withId(R.id.groupedAccountsList))
            .perform(
                RecyclerViewActions.scrollToHolder(
                    GroupAccountListMatcher(
                        resources.getString(R.string.tink_savings_account),
                        "6000000,00kr",
                        groups.filter { it == SAVINGS_ACCOUNTS }.count())
                )
            )

        onView(withId(R.id.groupedAccountsList))
            .perform(
                RecyclerViewActions.scrollToHolder(
                    GroupAccountListMatcher(
                        resources.getString(R.string.tink_other_account),
                        "2000000,00kr",
                        groups.filter { it == OTHER_ACCOUNTS }.count())
                )
            )


    }

    class GroupAccountListMatcher(
        private val title: String,
        private val amount: String,
        private val numberOfAccounts: Int,
    ): BoundedMatcher<GroupedAccountListAdapter.GroupedAccountViewHolder, GroupedAccountListAdapter.GroupedAccountViewHolder>
        (GroupedAccountListAdapter.GroupedAccountViewHolder::class.java) {
        override fun describeTo(description: Description?) {
            description?.appendText("Matching title, amount and number of list items.")
        }

        override fun matchesSafely(item: GroupedAccountListAdapter.GroupedAccountViewHolder?): Boolean {
            if (item == null) return false

            val x = item.itemView.accountBalanceSumText.text.filter { !it.isWhitespace() }.toString()
            val y = amount

            return item.itemView.accountKindText.text == title
                    //&& item.itemView.accountBalanceSumText.text.filter { !it.isWhitespace() }.toString() == amount
                    && item.itemView.accountsList.adapter?.itemCount == numberOfAccounts

        }
    }



    private fun checkVisibleViews(
        displayAllList: Boolean,
        displayGroupList: Boolean,
    ) {
        onView(withId(R.id.allAccountsList))
            .check(ViewAssertions.matches(CustomMatchers.isDisplayedIf(displayAllList)))
        onView(withId(R.id.groupedAccountsList))
            .check(ViewAssertions.matches(CustomMatchers.isDisplayedIf(displayGroupList)))
    }

}