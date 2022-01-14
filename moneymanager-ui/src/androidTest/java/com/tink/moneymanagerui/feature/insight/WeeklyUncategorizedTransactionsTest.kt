package com.tink.moneymanagerui.feature.insight

import android.util.Log
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tink.model.insights.InsightType
import com.tink.moneymanagerui.BaseTestSuite
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.mock.CategoryMockFactory
import com.tink.moneymanagerui.mock.TransactionMockFactory
import com.tink.moneymanagerui.testutil.PathDispatcher
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeeklyUncategorizedTransactionsTest: BaseTestSuite() {
    private var transactionIds = listOf("1111", "2222", "3333")
    private var transactions = listOf<JSONObject>()
    private var insight = JSONObject()

    private fun setupDispatcher() {
        val dispatcher = PathDispatcher()
        dispatcher.addResponses(mapOf(
            "/api/v1/insights/archived" to MockResponse().setResponseCode(200).setBody("[]"),
            "/api/v1/categories" to
                MockResponse().setResponseCode(200).setBody(CategoryMockFactory.getFoodCategories().toString()),
        ))

        insight = InsightMockFactory.getWeeklyUncategorizedTransactions(
            transactionIds
        )
        dispatcher.addResponse("/api/v1/insights", MockResponse().setResponseCode(200).setBody(listOf(insight).toString()))


        transactions = listOf(
            TransactionMockFactory.getTransaction(transactionIds[0], description = "Camera"),
            TransactionMockFactory.getTransaction(transactionIds[1], description = "Lunch"),
            TransactionMockFactory.getTransaction(transactionIds[2], description = "Coffee"),
        )
        dispatcher.addResponses(
            mutableMapOf(
                "/api/v1/transactions/${transactionIds[0]}" to
                        MockResponse().setResponseCode(200).setBody(transactions[0].toString()),
                "/api/v1/transactions/${transactionIds[1]}" to
                        MockResponse().setResponseCode(200).setBody(transactions[1].toString()),
                "/api/v1/transactions/${transactionIds[2]}" to
                        MockResponse().setResponseCode(200).setBody(transactions[2].toString()),
            )
        )

        server.dispatcher = dispatcher
    }

    private fun openInsightsFragment() {
        launchFinanceOverviewFragment()
        navigator.fromOverviewToInsight()
    }

    @Test
    fun test_display_weekly_uncategorized_insight() {
        setupDispatcher()
        openInsightsFragment()


        Espresso.onView(ViewMatchers.withId(R.id.amount))
            .check(ViewAssertions.matches(ViewMatchers.withText("3 expenses")))
        /*

        Espresso.onView(ViewMatchers.withId(R.id.amount))
            .check(ViewAssertions.matches(ViewMatchers.withText("-€150.00")))

        Espresso.onView(ViewMatchers.withId(R.id.transactionDescription))
            .check(ViewAssertions.matches(ViewMatchers.withText("Climbing shoes")))

        Espresso.onView(ViewMatchers.withId(R.id.date))
            .check(ViewAssertions.matches(ViewMatchers.withText("Monday, Dec 27, 2021")))
         */
    }
}