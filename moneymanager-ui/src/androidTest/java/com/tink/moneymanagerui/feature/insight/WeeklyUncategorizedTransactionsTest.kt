package com.tink.moneymanagerui.feature.insight

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.mock.InsightMockFactory
import com.tink.moneymanagerui.mock.TransactionMockFactory
import okhttp3.mockwebserver.MockResponse
import org.joda.time.DateTime
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeeklyUncategorizedTransactionsTest: BaseInsightTestSuit() {
    private var transactionIds = listOf("1111", "2222", "3333")
    private var transactions = listOf<JSONObject>()
    private var insight = JSONObject()

    private fun setupDispatcher() {
        insight = InsightMockFactory.getWeeklyUncategorizedTransactions(
            transactionIds
        )
        insightDispatcher.addResponse("/api/v1/insights", MockResponse().setResponseCode(200).setBody(listOf(insight).toString()))

        transactions = listOf(
            TransactionMockFactory.getTransaction(
                id = transactionIds[0],
                description = "Lunch",
                date = DateTime.parse("2021-07-04T05:20").millis.toString()
            ),
            TransactionMockFactory.getTransaction(
                id = transactionIds[1],
                description = "Camera",
                date = DateTime.parse("2021-06-30T01:20").millis.toString()
            ),
            TransactionMockFactory.getTransaction(
                id = transactionIds[2],
                description = "Coffee",
                date = DateTime.parse("2021-07-05T05:20").millis.toString()
            )
        )
        insightDispatcher.addResponses(
            mutableMapOf(
                "/api/v1/transactions/${transactionIds[0]}" to
                        MockResponse().setResponseCode(200).setBody(transactions[0].toString()),
                "/api/v1/transactions/${transactionIds[1]}" to
                        MockResponse().setResponseCode(200).setBody(transactions[1].toString()),
                "/api/v1/transactions/${transactionIds[2]}" to
                        MockResponse().setResponseCode(200).setBody(transactions[2].toString()),
            )
        )

        server.dispatcher = insightDispatcher
    }

    @Test
    fun test_display_weekly_uncategorized_insight() {
        setupDispatcher()
        openInsightsFragment()

        Espresso.onView(ViewMatchers.withId(R.id.transactionDescription))
            .check(ViewAssertions.matches(ViewMatchers.withText("Lunch, Camera, Coffee")))

        Espresso.onView(ViewMatchers.withId(R.id.date))
            .check(ViewAssertions.matches(ViewMatchers.withText("29 Jun 2021 - 05 Jul 2021")))

        Espresso.onView(ViewMatchers.withId(R.id.amount))
            .check(ViewAssertions.matches(ViewMatchers.withText(
                resources.getString(R.string.tink_expenses_text, 3)
            )))
    }
}