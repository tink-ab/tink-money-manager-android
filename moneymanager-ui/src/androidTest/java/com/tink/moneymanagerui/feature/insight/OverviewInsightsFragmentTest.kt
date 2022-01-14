package com.tink.moneymanagerui.feature.insight

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tink.moneymanagerui.BaseTestSuite
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.mock.CategoryMockFactory
import com.tink.moneymanagerui.mock.TransactionMockFactory
import com.tink.moneymanagerui.testutil.PathDispatcher
import com.tink.moneymanagerui.testutil.RecyclerViewItemCountAssertion
import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OverviewInsightsFragmentTest: BaseTestSuite() {

    private var allInsights = InsightMockFactory.getInsightsForTypes(InsightMockFactory.allTypes)
    private var supportedInsights = InsightMockFactory.getInsightsForTypes(InsightMockFactory.supportedTypes)

    private fun setupDispatcher() {
        val defaultTransaction = TransactionMockFactory.getTransaction()


        server.dispatcher = PathDispatcher(
            mutableMapOf(
                "/api/v1/transactions/${defaultTransaction["id"]}" to
                        MockResponse().setResponseCode(200).setBody(defaultTransaction.toString()),
                "/api/v1/insights" to MockResponse().setResponseCode(200).setBody(allInsights.toString()),
                "/api/v1/insights/archived" to MockResponse().setResponseCode(200).setBody("[]"),
                "/api/v1/categories" to
                        MockResponse().setResponseCode(200).setBody(CategoryMockFactory.getFoodCategories().toString())
            )
        )
    }

    @Test
    fun testDisplayNumberOfInsightsOnOverview() {
        setupDispatcher()
        launchFinanceOverviewFragment()

        overviewDisplaysCorrectNumberOfInsights()
    }

    @Test
    fun testDisplayNumberOfInsightsOnOverviewAndList() {
        setupDispatcher()
        launchFinanceOverviewFragment()

        overviewDisplaysCorrectNumberOfInsights()

        navigator.fromOverviewToInsight()

        onView(withId(R.id.recyclerView))
            .check(RecyclerViewItemCountAssertion(supportedInsights.size))
    }

    private fun overviewDisplaysCorrectNumberOfInsights() {
        onView(withText(R.string.tink_insights_overview_card_action_text))
            .check(matches(isDisplayed()))
        onView(withId(R.id.insightsCount))
            .check(matches(withText(supportedInsights.size.toString())))
    }
}
