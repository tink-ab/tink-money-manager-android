package com.tink.moneymanagerui.feature.insight

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tink.moneymanagerui.insights.actionhandling.CustomInsightActionHandler
import com.tink.moneymanagerui.insights.actionhandling.InsightActionHandler
import com.tink.moneymanagerui.mock.AccountMockFactory
import com.tink.moneymanagerui.mock.InsightMockFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import okhttp3.mockwebserver.MockResponse
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class InsightActionTest: BaseInsightTestSuit() {
    private val accountId = "ee7ddbd178494220bb184791783f4f64"
    private val accountName = "Test account"

    private val accountBalance = InsightMockFactory.getAccountBalanceLow(
        InsightMockFactory.getViewAccountAction(accountId))
    private var insights = listOf<JSONObject>()

    private fun setupData(insight: JSONObject) {
        insights = listOf(insight)
    }

    private fun setupDispatcher() {
        insightDispatcher.addResponse("/api/v1/insights",
            MockResponse().setResponseCode(200).setBody(insights.toString()))

        insightDispatcher.addResponse("/api/v1/accounts/list",
            MockResponse().setResponseCode(200).setBody(
                AccountMockFactory.getListWithOneAccount(accountId, accountName).toString()
            ))
        server.dispatcher = insightDispatcher
    }

    @Test
    fun test_view_accounts_default_handler() {
        openInsight(accountBalance)
        onView(withText("See details")).perform(ViewActions.click())
        onView(withText(accountName)).check(matches(isDisplayed()));
    }

    @Test
    fun test_view_accounts_custom_handler() {
        openInsight(accountBalance)

        val insightHandler = mockk<InsightActionHandler>(relaxed = true).apply {
            every { viewAccount(any()) } returns true
        }
        CustomInsightActionHandler.setInsightActionHandler(insightHandler)

        onView(withText("See details")).perform(ViewActions.click())

        verify { insightHandler.viewAccount(accountId) }
    }

    private fun openInsight(insight: JSONObject) {
        setupData(insight)
        setupDispatcher()
        openInsightsFragment()
    }
}