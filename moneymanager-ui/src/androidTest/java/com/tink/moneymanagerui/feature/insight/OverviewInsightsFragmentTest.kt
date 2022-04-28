package com.tink.moneymanagerui.feature.insight

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.mock.InsightMockFactory
import com.tink.moneymanagerui.testutil.RecyclerViewItemCountAssertion
import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OverviewInsightsFragmentTest : BaseInsightTestSuit() {

    private var allInsights = InsightMockFactory.getInsightsForTypes(InsightMockFactory.allTypes)
    private var supportedInsights = InsightMockFactory.getInsightsForTypes(InsightMockFactory.supportedTypes)

    private fun setupDispatcher() {

        insightDispatcher.addResponse(
            "/api/v1/insights", MockResponse().setResponseCode(200).setBody(allInsights.toString())
        )

        server.dispatcher = insightDispatcher
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
