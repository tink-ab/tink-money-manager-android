import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tink.moneymanagerui.BaseTestSuite
import com.tink.moneymanagerui.FinanceOverviewFragment
import com.tink.moneymanagerui.FinanceOverviewFragment.Companion.ARG_ACCESS_TOKEN
import com.tink.moneymanagerui.FinanceOverviewFragment.Companion.ARG_IS_OVERVIEW_TOOLBAR_VISIBLE
import com.tink.moneymanagerui.FinanceOverviewFragment.Companion.ARG_OVERVIEW_FEATURES
import com.tink.moneymanagerui.FinanceOverviewFragment.Companion.ARG_STYLE_RES
import com.tink.moneymanagerui.OverviewFeatures
import com.tink.moneymanagerui.TestConfiguration
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InsightTestSuite: BaseTestSuite() {
    private val tag = "TestingFinanceOverviewFragment"

    @Test
    fun testOverviewInsightsFragmentIsDisplayed() {
        val fragmentArgs = bundleOf(
            Pair(ARG_STYLE_RES, com.tink.moneymanagerui.R.style.tink_FinanceOverviewSampleStyle),
            Pair(ARG_ACCESS_TOKEN, TestConfiguration.sampleAccessToken),
            Pair(ARG_OVERVIEW_FEATURES, OverviewFeatures.ALL),
            Pair(ARG_IS_OVERVIEW_TOOLBAR_VISIBLE, true)
        )
        val scenario = launchFragmentInContainer<FinanceOverviewFragment>(fragmentArgs)
        Log.d(tag, "testEventFragment: LAUNCHED")

        onView(withText(com.tink.moneymanagerui.R.string.tink_insights_overview_card_action_text)).check(matches(isDisplayed()))
    }
}
