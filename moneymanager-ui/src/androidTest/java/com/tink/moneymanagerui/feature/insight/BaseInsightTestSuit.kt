package com.tink.moneymanagerui.feature.insight

import com.tink.moneymanagerui.BaseTestSuite
import com.tink.moneymanagerui.mock.CategoryMockFactory
import com.tink.moneymanagerui.mock.InsightMockFactory
import com.tink.moneymanagerui.mock.TransactionMockFactory
import com.tink.moneymanagerui.testutil.PathDispatcher
import okhttp3.mockwebserver.MockResponse
import org.json.JSONObject
import org.junit.After

open class BaseInsightTestSuit: BaseTestSuite() {

    private val defaultTransaction = TransactionMockFactory.getTransaction()
    val insightDispatcher = PathDispatcher(
        mutableMapOf(
            "/api/v1/transactions/${defaultTransaction["id"]}" to
                    MockResponse().setResponseCode(200).setBody(defaultTransaction.toString()),
            "/api/v1/insights/archived" to MockResponse().setResponseCode(200).setBody("[]"),
            "/api/v1/categories" to
                    MockResponse().setResponseCode(200).setBody(CategoryMockFactory.getFoodCategories().toString()))
    )

    internal fun openInsightsFragment() {
        launchFinanceOverviewFragment()
        navigator.fromOverviewToInsight()
    }

    @After
    fun clearDispatcher() {
        //insightDispatcher.clearResponses()

        /*
        insightDispatcher.addResponse("/api/v1/insights",
            MockResponse().setResponseCode(200).setBody(listOf(
                InsightMockFactory.getAccountBalanceLow()
            ).toString()))
         */

    }
}