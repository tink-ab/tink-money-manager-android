package com.tink.moneymanagerui.feature.account

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.squareup.moshi.Moshi
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.accounts.AccountEditConfiguration
import com.tink.moneymanagerui.accounts.AccountGroupType
import com.tink.moneymanagerui.accounts.NoAccountGroup
import com.tink.moneymanagerui.accounts.OverviewAccountsMode
import com.tink.moneymanagerui.accounts.OverviewFavoriteAccounts
import com.tink.moneymanagerui.mock.TransactionMockFactory
import com.tink.moneymanagerui.testutil.RecyclerViewItemCountAssertion
import com.tink.moneymanagerui.util.extensions.formatCurrencyExact
import com.tink.rest.models.SearchQueryJsonAdapter
import okhttp3.mockwebserver.MockResponse
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AccountTransactionListTest : BaseAccountTestSuit() {
    private fun openAccountDetailsWithAcoount(
        account: JSONObject,
        overviewAccountsMode: OverviewAccountsMode = OverviewFavoriteAccounts,
        accountGroupType: AccountGroupType = NoAccountGroup,
        accountEditConfiguration: AccountEditConfiguration = AccountEditConfiguration.ALL
    ) {
        openOverviewWithAcoounts(listOf(account), overviewAccountsMode, accountGroupType, accountEditConfiguration)
        navigator.fromOverviewToAccountDetails()
    }

    @Test
    fun shows_account_number() {
        openAccountDetailsWithAcoount(account = defaultAccount)

        onView(withId(R.id.accountNumber))
            .check(
                matches(withText(defaultAccount["accountNumber"].toString()))
            )
    }

    @Test
    fun shows_balance() {
        openAccountDetailsWithAcoount(account = defaultAccount)

        val expectedBalanceText = defaultAmount.formatCurrencyExact()

        onView(withId(R.id.accountBalance))
            .check(matches(withText(expectedBalanceText)))
    }

    @Test
    fun shows_toolbar_title() {
        openAccountDetailsWithAcoount(account = defaultAccount)

        onView(withId(R.id.tink_toolbar))
            .check(
                matches(
                    hasDescendant(
                        withText(
                            defaultAccount["name"].toString()
                        )
                    )
                )
            )
    }

    @Test
    fun shows_transactions_in_list() {
        val numberOfTransactionForAccount = 15
        val accountId = defaultAccount["id"].toString()
        val transactionForAccount = TransactionMockFactory.getTransactionsForAccount(
            accountId = accountId,
            numberOfTransactions = numberOfTransactionForAccount
        )

        accountDispatcher.addResponse(
            "/api/v1/search",
            MockResponse().setResponseCode(200).setBody(transactionForAccount.toString())
        )

        val accountIdFilter = { body: String ->
            try {
                val query = SearchQueryJsonAdapter(Moshi.Builder().build()).fromJson(body)
                query?.accounts?.contains(accountId) ?: false
            } catch (e: Exception) {
                false
            }
        }
        accountDispatcher.addFilter("/api/v1/search", accountIdFilter)

        openAccountDetailsWithAcoount(account = defaultAccount)

        // Add two in the count since the header is included as an extra item is created by ConcatAdapter
        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))
            .check(RecyclerViewItemCountAssertion(numberOfTransactionForAccount + 2))
    }
}
