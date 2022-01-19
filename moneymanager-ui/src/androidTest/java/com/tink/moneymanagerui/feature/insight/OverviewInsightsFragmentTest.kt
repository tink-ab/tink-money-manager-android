import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tink.moneymanagerui.BaseTestSuite
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.feature.insight.InsightMockFactory
import com.tink.moneymanagerui.mock.CategoryMockFactory
import com.tink.moneymanagerui.testutil.RecyclerViewItemCountAssertion
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OverviewInsightsFragmentTest: BaseTestSuite() {

    private val allInsights = InsightMockFactory.getInsightsForTypes(InsightMockFactory.allTypes)
    private val supportedInsights = InsightMockFactory.getInsightsForTypes(InsightMockFactory.supportedTypes)

    private fun setupDispatcher() {

        val dispatcher: Dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/api/v1/insights" -> MockResponse().setResponseCode(200).setBody(allInsights.toString())
                    "/api/v1/insights/archived" -> MockResponse().setResponseCode(200).setBody("[]")
                    "/api/v1/categories" ->
                        MockResponse().setResponseCode(200).setBody(CategoryMockFactory.getFoodCategories().toString())

                    else -> MockResponse().setResponseCode(500).setBody("{}")
                }
            }
        }
        server.dispatcher = dispatcher
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

        onView(withText(R.string.tink_insights_overview_card_action_text))
            .perform(click())

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
