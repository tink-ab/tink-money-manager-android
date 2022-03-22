package com.tink.moneymanagerui

import android.content.res.Resources
import android.net.Uri
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.IdlingRegistry
import androidx.test.platform.app.InstrumentationRegistry
import com.tink.core.Tink
import com.tink.moneymanagerui.testutil.TestNavigator
import com.tink.moneymanagerui.util.EspressoIdlingResource
import com.tink.service.network.TinkConfiguration
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before

abstract class BaseTestSuite {

    val resources: Resources = InstrumentationRegistry.getInstrumentation().targetContext.resources
    val navigator = TestNavigator()
    val server = MockWebServer()
    private lateinit var url: String
    private lateinit var testConfiguration: TestConfiguration

    internal fun launchFinanceOverviewFragment() {
        val fragmentArgs = bundleOf(
            FinanceOverviewFragment.ARG_ACCESS_TOKEN to testConfiguration.sampleAccessToken,
            FinanceOverviewFragment.ARG_OVERVIEW_FEATURES to OverviewFeatures.ALL,
            FinanceOverviewFragment.ARG_IS_OVERVIEW_TOOLBAR_VISIBLE to true
        )
        launchFragmentInContainer<FinanceOverviewFragment>(fragmentArgs)
    }

    @Before
    fun tinkInit() {
        server.start(4000)
        url = server.url("/").toString()
        testConfiguration = TestConfiguration(url)

        val config = TinkConfiguration(
            testConfiguration.sampleEnvironment,
            testConfiguration.sampleOAuthClientId,
            Uri.parse(url)
        )

        try {
            Tink.init(config, InstrumentationRegistry.getInstrumentation().targetContext)
        } catch (e: IllegalStateException) {
        }
    }

    @Before
    fun setUpIdlingRegistry() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDownIdlingRegistry() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDownMockServer() {
        server.shutdown()
    }
}
