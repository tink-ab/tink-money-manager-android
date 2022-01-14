package com.tink.moneymanagerui.feature.insight

import android.util.Log
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tink.moneymanagerui.BaseTestSuite
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
class SingleUncategorizedTransactionTest: BaseTestSuite() {
    private val transactionId = "8a703fa458d144f9b802b09b26a43e89"
    private var transaction = JSONObject()
    private var insight = JSONObject()

    private fun setupDispatcher() {
        server.dispatcher = PathDispatcher(
            mutableMapOf(
                "/api/v1/transactions/$transactionId" to MockResponse().setResponseCode(200).setBody(transaction.toString()),
                "/api/v1/insights" to MockResponse().setResponseCode(200).setBody(listOf(insight).toString()),
                "/api/v1/insights/archived" to MockResponse().setResponseCode(200).setBody("[]"),
                "/api/v1/categories" to
                        MockResponse().setResponseCode(200).setBody(CategoryMockFactory.getFoodCategories().toString()),
            )
        )
    }

    private fun openInsightsFragment() {
        launchFinanceOverviewFragment()
        navigator.fromOverviewToInsight()
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