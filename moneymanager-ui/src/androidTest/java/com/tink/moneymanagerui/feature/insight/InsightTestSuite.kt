import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tink.moneymanagerui.BaseTestSuite
import com.tink.moneymanagerui.FinanceOverviewFragment
import com.tink.moneymanagerui.FinanceOverviewFragment.Companion.ARG_ACCESS_TOKEN
import com.tink.moneymanagerui.FinanceOverviewFragment.Companion.ARG_IS_OVERVIEW_TOOLBAR_VISIBLE
import com.tink.moneymanagerui.FinanceOverviewFragment.Companion.ARG_OVERVIEW_FEATURES
import com.tink.moneymanagerui.FinanceOverviewFragment.Companion.ARG_STYLE_RES
import com.tink.moneymanagerui.OverviewFeatures
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InsightTestSuite: BaseTestSuite() {
    private val tag = "TestingFinanceOverviewFragment"

    private fun setupDispatcher() {
        val insightListBody = "[" +
                "  {" +
                "    \"userId\": 1234," +
                "    \"id\": 1," +
                "    \"type\": \"LARGE_EXPENSE\"," +
                "    \"title\": \"Title\"," +
                "    \"description\": \"Description\"," +
                "    \"createdTime\": 1111111," +
                "    \"insightActions\": []" +
                "  }," +
                "  {" +
                "    \"userId\": 1234," +
                "    \"id\": 2," +
                "    \"type\": \"LARGE_EXPENSE\"," +
                "    \"title\": \"Title\"," +
                "    \"description\": \"Description\"," +
                "    \"createdTime\": 1111111," +
                "    \"insightActions\": []" +
                "  }," +
                "  {" +
                "    \"userId\": 1234," +
                "    \"id\": 3," +
                "    \"type\": \"LARGE_EXPENSE\"," +
                "    \"title\": \"Title\"," +
                "    \"description\": \"Description\"," +
                "    \"createdTime\": 1111111," +
                "    \"insightActions\": []" +
                "  }" +
                "]"

        val dispatcher: Dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                when (request.path) {
                    "/api/v1/insights"
                    -> return MockResponse().setResponseCode(200).setBody(insightListBody)
                }
                return MockResponse().setResponseCode(500).setBody("{}")
            }
        }
        server.dispatcher = dispatcher
    }

    @Test
    fun testOverviewInsightsFragmentIsDisplayed() {
        setupDispatcher()
        val fragmentArgs = bundleOf(
            Pair(ARG_STYLE_RES, com.tink.moneymanagerui.R.style.tink_FinanceOverviewSampleStyle),
            Pair(ARG_ACCESS_TOKEN, testConfiguration.sampleAccessToken),
            Pair(ARG_OVERVIEW_FEATURES, OverviewFeatures.ALL),
            Pair(ARG_IS_OVERVIEW_TOOLBAR_VISIBLE, true)
        )
        val scenario = launchFragmentInContainer<FinanceOverviewFragment>(fragmentArgs)
        Log.d(tag, "testOverviewInsightsFragmentIsDisplayed: LAUNCHED")

        onView(withText(com.tink.moneymanagerui.R.string.tink_insights_overview_card_action_text))
            .check(matches(isDisplayed()))
        onView(withId(com.tink.moneymanagerui.R.id.insightsCount))
            .check(matches(withText("3")))
        Log.d(tag, "testOverviewInsightsFragmentIsDisplayed: requestCount ${server.requestCount}")
    }
}
