package com.tink.moneymanagerui.feature.insight

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tink.model.budget.BudgetFilter
import com.tink.model.budget.BudgetPeriodicity
import com.tink.model.misc.Amount
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.insights.actionhandling.InsightActionHandler
import com.tink.moneymanagerui.mock.InsightMockFactory
import com.tink.moneymanagerui.testutil.RecyclerViewItemCountAssertion
import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import org.junit.runner.RunWith
import timber.log.Timber

@RunWith(AndroidJUnit4::class)
class OverviewInsightsFragmentTest: BaseInsightTestSuit() {

    private var allInsights = InsightMockFactory.getInsightsForTypes(InsightMockFactory.allTypes)
    private var supportedInsights = InsightMockFactory.getInsightsForTypes(InsightMockFactory.supportedTypes)

    private fun setupDispatcher() {

        insightDispatcher.addResponse(
            "/api/v1/insights", MockResponse().setResponseCode(200).setBody(allInsights.toString()))

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

    val x = object : InsightActionHandler() {

        override fun viewBudget(budgetId: String, periodStart: String): Boolean {
            Timber.d("Custom viewBudget")
            return true
        }

        override fun createBudget(
            amount: Amount?,
            filter: BudgetFilter?,
            periodicity: BudgetPeriodicity?,
            onComplete: (isActionDone: Boolean) -> Unit
        ): Boolean {
            Timber.d("Custom createBudget")
            onComplete(true) // Archive insight
            return true // Mark as handled, preventing default handler from handling this again.
        }

        override fun categorizeExpense(transactionId: String, onComplete: (isActionDone: Boolean) -> Unit): Boolean {
            Timber.d("Custom categorizeExpense")
            return true
        }

        override fun categorizeTransactions(transactionIds: List<String>, onComplete: (isActionDone: Boolean) -> Unit): Boolean {
            Timber.d("Custom categorizeTransactions")
            return true
        }

        override fun refreshCredentials(credentialId: String, onComplete: (isActionDone: Boolean) -> Unit): Boolean {
            Timber.d("Custom refreshCredentials")
            return true
        }

        override fun viewTransactions(transactionIds: List<String>): Boolean {
            Timber.d("Custom viewTransactions")
            return true
        }

        override fun viewTransactionsByCategory(transactionsByCategory: Map<String, List<String>>): Boolean {
            Timber.d("Custom viewTransactionsByCategory")
            return true
        }

        override fun initiateTransfer(
            sourceUri: String?,
            sourceAccountNumber: String?,
            destinationUri: String?,
            destinationAccountNumber: String?,
            amount: Amount?,
            onComplete: (isActionDone: Boolean) -> Unit
        ): Boolean {
            Timber.d("Custom initiateTransfer")
            onComplete(true)
            return true
        }
    }

    private fun overviewDisplaysCorrectNumberOfInsights() {
        onView(withText(R.string.tink_insights_overview_card_action_text))
            .check(matches(isDisplayed()))
        onView(withId(R.id.insightsCount))
            .check(matches(withText(supportedInsights.size.toString())))
    }
}
