package com.tink.moneymanagerui

import android.net.Uri
import androidx.test.espresso.IdlingRegistry
import androidx.test.platform.app.InstrumentationRegistry
import com.tink.core.Tink
import com.tink.moneymanagerui.util.EspressoIdlingResource
import com.tink.service.network.TinkConfiguration
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before

abstract class BaseTestSuite {

    private var server = MockWebServer()

    @Before
    fun setupMockServer() {
        server.start(8080)
        server.url(TestConfiguration.sampleEnvironment.restUrl + "/api/v1/insights/archived")
        server.enqueue(MockResponse().setBody("{}"))
    }

    @After
    fun tearDownMockServer() {
        server.shutdown()
    }

    @Before
    fun setUpIdlingRegistry() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDownIdlingRegistry() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Before
    fun tinkInit() {
        val config = TinkConfiguration(
            TestConfiguration.sampleEnvironment,
            TestConfiguration.sampleOAuthClientId,
            Uri.parse(TestConfiguration.sampleEnvironment.restUrl)
        )

        Tink.init(config, InstrumentationRegistry.getInstrumentation().targetContext)
    }
}