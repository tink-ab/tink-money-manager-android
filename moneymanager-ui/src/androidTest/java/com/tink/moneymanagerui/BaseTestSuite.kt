package com.tink.moneymanagerui

import android.net.Uri
import androidx.test.espresso.IdlingRegistry
import androidx.test.platform.app.InstrumentationRegistry
import com.tink.core.Tink
import com.tink.moneymanagerui.util.EspressoIdlingResource
import com.tink.service.network.TinkConfiguration
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before


abstract class BaseTestSuite {

    var server = MockWebServer()
    private val url = server.url("/").toString()
    val testConfiguration = TestConfiguration(url)

    @Before
    fun tinkInit() {
        val config = TinkConfiguration(
            testConfiguration.sampleEnvironment,
            testConfiguration.sampleOAuthClientId,
            Uri.parse(url)
        )
        Tink.init(config, InstrumentationRegistry.getInstrumentation().targetContext)
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
