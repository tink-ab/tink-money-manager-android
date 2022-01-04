import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tink.moneymanagerui.BaseTestSuite
import com.tink.moneymanagerui.FinanceOverviewFragment
import com.tink.moneymanagerui.FinanceOverviewFragment.Companion.ARG_ACCESS_TOKEN
import com.tink.moneymanagerui.FinanceOverviewFragment.Companion.ARG_IS_OVERVIEW_TOOLBAR_VISIBLE
import com.tink.moneymanagerui.FinanceOverviewFragment.Companion.ARG_OVERVIEW_FEATURES
import com.tink.moneymanagerui.OverviewFeatures
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.Matchers
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OverviewInsightsFragmentTest: BaseTestSuite() {

    private var insightListBody = """
         [
            {
                "userId": 1234,
                "id": 1,
                "type": "LARGE_EXPENSE",
                "title": "Title",
                "description": "Description",
                "createdTime": 1111111,
                "insightActions": []
            },
            {
                "userId": 1234,
                "id": 2,
                "type": "LARGE_EXPENSE",
                "title": "Title",
                "description": "Description",
                "createdTime": 1111111,
                "insightActions": []
            },
            {
                "userId": 1234,
                "id": 3,
                "type": "LARGE_EXPENSE",
                "title": "Title",
                "description": "Description",
                "createdTime": 1111111,
                "insightActions": []
            }
        ]
        """

    private fun setupDispatcher() {
        val dispatcher: Dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/api/v1/insights" -> MockResponse().setResponseCode(200).setBody(insightListBody)
                    else -> MockResponse().setResponseCode(500).setBody("{}")
                }
            }
        }
        server.dispatcher = dispatcher
    }

    @Test
    fun testDisplayNumberOfInsights() {
        setupDispatcher()
        val fragmentArgs = bundleOf(
            ARG_ACCESS_TOKEN to testConfiguration.sampleAccessToken,
            ARG_OVERVIEW_FEATURES to OverviewFeatures.ALL,
            ARG_IS_OVERVIEW_TOOLBAR_VISIBLE to true
        )
        launchFragmentInContainer<FinanceOverviewFragment>(fragmentArgs)

        onView(withText(com.tink.moneymanagerui.R.string.tink_insights_overview_card_action_text))
            .check(matches(isDisplayed()))
        onView(withId(com.tink.moneymanagerui.R.id.insightsCount))
            .check(matches(withText("3")))

        // Move this line to navigation test when complete.
        onView(withText(com.tink.moneymanagerui.R.string.tink_insights_overview_card_action_text))
            .perform(click())

        insightListBody = """
         [
            {
                "userId": 1234,
                "id": 1,
                "type": "LARGE_EXPENSE",
                "title": "Title",
                "description": "Description",
                "createdTime": 1111111,
                "insightActions": []
            }
         ]"""
        setupDispatcher()

        onView(
            Matchers.allOf(
                withContentDescription("Navigate up"),
                isDisplayed()
            )
        ).perform(click())


        onView(withText(com.tink.moneymanagerui.R.string.tink_insights_overview_card_action_text))
            .check(matches(isDisplayed()))
        onView(withId(com.tink.moneymanagerui.R.id.insightsCount))
            .check(matches(withText("1")))

        onView(withText(com.tink.moneymanagerui.R.string.tink_insights_overview_card_action_text))
            .perform(click())

    }
}
