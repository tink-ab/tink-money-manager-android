package com.tink.moneymanagerui.feature.insight

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.extensions.milli
import com.tink.moneymanagerui.insights.actionhandling.CustomInsightActionHandler
import com.tink.moneymanagerui.insights.actionhandling.InsightActionHandler
import com.tink.moneymanagerui.mock.InsightMockFactory
import com.tink.moneymanagerui.mock.TransactionMockFactory
import com.tink.moneymanagerui.testutil.getLatestRequest
import io.mockk.mockk
import io.mockk.verify
import okhttp3.mockwebserver.MockResponse
import org.json.JSONObject
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class IconTextViewProviderTest : BaseInsightTestSuit() {
    private val transactionId = "8a703fa458d144f9b802b09b26a43e89"
    private val transactionDoubleId = "96b2888389df44f18f4b433c5a99e373"
    private var transaction = JSONObject()
    private var transactionDouble = JSONObject()

    private val accountBalance = InsightMockFactory.getAccountBalanceLow()
    private val budgetSuggestCreateFirst = InsightMockFactory.getBudgetSuggestCreateFirst()
    private val doubleCharge = InsightMockFactory.getDoubleCharge(listOf(transactionId, transactionDoubleId))
    private val largeExpense = InsightMockFactory.getLargeExpense(transactionId)
    private val creditCardLimitClose = InsightMockFactory.getCreditCardLimitClose()
    private val creditCardLimitReached = InsightMockFactory.getCreditCardLimitReached()

    private val refreshCredentials = "726491029375"
    private val refreshToken = InsightMockFactory.getAggregateRefreshP2d2Credentials(refreshCredentials)
    private var insights = listOf<JSONObject>()

    private fun setupData(insight: JSONObject) {
        transaction = TransactionMockFactory.getTransaction(
            id = transactionId,
            amount = "-150",
            description = "Climbing shoes",
            date = LocalDateTime.parse("2021-12-27T05:20").milli().toString(),
            currencyCode = "EUR"
        )
        transactionDouble = TransactionMockFactory.getTransaction(
            id = transactionDoubleId,
            amount = "-150",
            description = "Climbing shoes",
            date = LocalDateTime.parse("2021-12-27T05:20").milli().toString(),
            currencyCode = "EUR"
        )
        insights = listOf(insight)
    }

    private fun setupDispatcher() {
        insightDispatcher.addResponse(
            "/api/v1/insights",
            MockResponse().setResponseCode(200).setBody(insights.toString())
        )

        server.dispatcher = insightDispatcher
    }

    @Test
    fun test_display_account_balance_low_insight() {
        testIconTextInsight(accountBalance)
    }

    @Test
    fun test_display_budget_suggest_created_first() {
        testIconTextInsight(budgetSuggestCreateFirst)
    }

    @Test
    fun test_display_double_charge() {
        testIconTextInsight(doubleCharge)
    }

    @Test
    fun test_display_large_expense() {
        testIconTextInsight(largeExpense)
    }

    @Test
    fun test_credit_card_limit_close() {
        testIconTextInsight(creditCardLimitClose)
    }

    @Test
    fun test_credit_card_limit_reached() {
        testIconTextInsight(creditCardLimitReached)
    }

    @Test
    fun test_display_refresh_token_and_dismiss() {
        testIconTextInsight(refreshToken)

        onView(withText("Dismiss")).perform(ViewActions.click())

        val dismissRequest = server.getLatestRequest()
        Assert.assertNotNull(dismissRequest)
        // TODO: Make assertions in a more controlled manner.
        Assert.assertEquals(
            "[text={\"insightId\":\"2\",\"insightAction\":\"DISMISS\"}]",
            dismissRequest!!.body.toString()
        )
    }

    @Test
    fun test_display_refresh_token_and_refresh() {
        testIconTextInsight(refreshToken)

        val insightHandler = mockk<InsightActionHandler>(relaxed = true)
        CustomInsightActionHandler.setInsightActionHandler(insightHandler)

        onView(withText("Refresh")).perform(ViewActions.click())

        verify { insightHandler.refreshCredentials(refreshCredentials, any()) }
    }

    private fun testIconTextInsight(insight: JSONObject) {
        setupData(insight)
        setupDispatcher()
        openInsightsFragment()

        matchIconTextInsight(insight)
    }

    private fun matchIconTextInsight(insight: JSONObject) {
        val actualTitle = insight["title"] as String
        onView(withId(R.id.title))
            .check(matches(withText(actualTitle)))

        val actualDescription = insight["description"] as String
        onView(withId(R.id.description))
            .check(matches(withText(actualDescription)))

        // TODO: Maybe test that icon is correct when we have a matcher for that.
    }
}
