package com.tink.moneymanagerui.feature.insight

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tink.moneymanagerui.mock.InsightMockFactory
import com.tink.moneymanagerui.mock.TransactionMockFactory
import okhttp3.mockwebserver.MockResponse
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SingleUncategorizedTransactionTest: BaseInsightTestSuit() {
    private val transactionId = "8a703fa458d144f9b802b09b26a43e89"
    private var transaction = JSONObject()
    private var insight = JSONObject()

    private fun setupDispatcher() {
        insightDispatcher.addResponse("/api/v1/insights",
            MockResponse().setResponseCode(200).setBody(listOf(insight).toString()))
        insightDispatcher.addResponse(  "/api/v1/transactions/$transactionId",
            MockResponse().setResponseCode(200).setBody(transaction.toString()))
        server.dispatcher = insightDispatcher
    }

    private fun setupData() {
        transaction = TransactionMockFactory.getTransaction(
            id = transactionId,
            amount = "-150",
            description = "Climbing shoes",
            date = "1640602800000",
            currencyCode = "EUR"
        )
        insight = InsightMockFactory.getSingleUncategorizedTransaction(transactionId)
    }


    @Test
    fun test_display_single_uncategorized_insight() {
        setupData()
        setupDispatcher()
        openInsightsFragment()

        onView(withId(com.tink.moneymanagerui.R.id.amount))
            .check(matches(withText("-€150.00")))

        onView(withId(com.tink.moneymanagerui.R.id.transactionDescription))
            .check(matches(withText("Climbing shoes")))

        onView(withId(com.tink.moneymanagerui.R.id.date))
            .check(matches(withText("Monday, Dec 27, 2021")))
    }
}