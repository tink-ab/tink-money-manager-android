package com.tink.moneymanagerui.feature.insight

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tink.moneymanagerui.mock.InsightMockFactory
import okhttp3.mockwebserver.MockResponse
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeeklySummaryExpensesByDayTest : BaseInsightTestSuit() {
    private var insight = JSONObject()

    private fun setupDispatcher() {
        insight = InsightMockFactory.getWeeklySummaryExpensesByDay()
        insightDispatcher.addResponse(
            "/api/v1/insights",
            MockResponse().setResponseCode(200).setBody(listOf(insight).toString())
        )
        server.dispatcher = insightDispatcher
    }

    @Test
    fun test_display_weekly_sumary_expense_by_day_insight() {
        setupDispatcher()
        openInsightsFragment()
    }
}
