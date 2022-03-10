package com.tink.moneymanagerui.feature.account

import com.tink.model.misc.Amount
import com.tink.model.misc.ExactNumber
import com.tink.moneymanagerui.BaseTestSuite
import com.tink.moneymanagerui.OverviewFeature
import com.tink.moneymanagerui.OverviewFeatures
import com.tink.moneymanagerui.accounts.AccountEditConfiguration
import com.tink.moneymanagerui.accounts.AccountGroupType
import com.tink.moneymanagerui.accounts.NoAccountGroup
import com.tink.moneymanagerui.accounts.OverviewAccountsMode
import com.tink.moneymanagerui.accounts.OverviewFavoriteAccounts
import com.tink.moneymanagerui.mock.AccountMockFactory
import com.tink.moneymanagerui.mock.CategoryMockFactory
import com.tink.moneymanagerui.mock.TransactionMockFactory
import com.tink.moneymanagerui.testutil.PathDispatcher
import okhttp3.mockwebserver.MockResponse
import org.json.JSONObject

open class BaseAccountTestSuit: BaseTestSuite() {

    val defaultAmount = Amount(ExactNumber(999.0), "SEK")

    val defaultAccount = AccountMockFactory.getAccount(
        id = "ID123",
        name = "AccountTransactionListTest Account Name",
        accountNumber = "AN875",
        balance = defaultAmount.value.toBigDecimal().toDouble(),
        currencyCode = defaultAmount.currencyCode
    )

    private val defaultTransaction = TransactionMockFactory.getTransaction()
    val accountDispatcher = PathDispatcher(
        mutableMapOf(
            "/api/v1/transactions/${defaultTransaction["id"]}" to
                    MockResponse().setResponseCode(200).setBody(defaultTransaction.toString()),
            "/api/v1/categories" to
                    MockResponse().setResponseCode(200).setBody(CategoryMockFactory.getFoodCategories().toString()))
    )

    internal fun openOverviewWithAcoounts(
        accounts: List<JSONObject>,
        overviewAccountsMode: OverviewAccountsMode = OverviewFavoriteAccounts,
        accountGroupType: AccountGroupType = NoAccountGroup,
        accountEditConfiguration: AccountEditConfiguration = AccountEditConfiguration.ALL

    ) {
        accountDispatcher.addResponse(
            "/api/v1/accounts/list",
            MockResponse().setResponseCode(200).setBody(
                AccountMockFactory.getAccounts(accounts).toString()
            )
        )

        server.dispatcher = accountDispatcher
        launchFinanceOverviewFragment(features = OverviewFeatures(listOf(
            OverviewFeature.Accounts(
                overviewAccountsMode = overviewAccountsMode,
                accountGroupType = accountGroupType,
                accountEditConfiguration = accountEditConfiguration
            )
        )))
    }
}