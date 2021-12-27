import android.net.Uri
import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.tink.core.Tink
import com.tink.moneymanagerui.FinanceOverviewFragment
import com.tink.moneymanagerui.FinanceOverviewFragment.Companion.ARG_ACCESS_TOKEN
import com.tink.moneymanagerui.FinanceOverviewFragment.Companion.ARG_IS_OVERVIEW_TOOLBAR_VISIBLE
import com.tink.moneymanagerui.FinanceOverviewFragment.Companion.ARG_OVERVIEW_FEATURES
import com.tink.moneymanagerui.FinanceOverviewFragment.Companion.ARG_STYLE_RES
import com.tink.moneymanagerui.OverviewFeatures
import com.tink.moneymanagerui.feature.insight.TestConfiguration
import com.tink.service.network.TinkConfiguration
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MyTestSuite {
    val tag = "TestingFinanceOverviewFragment"

    @Test
    fun testEventFragment() {
        val config = TinkConfiguration(
            TestConfiguration.sampleEnvironment,
            TestConfiguration.sampleOAuthClientId,
            Uri.parse(TestConfiguration.sampleEnvironment.restUrl)
        )

        Tink.init(config, InstrumentationRegistry.getInstrumentation().targetContext)
        Log.d(tag, "testEventFragment: INIT")

        val fragmentArgs = bundleOf(
            Pair(ARG_STYLE_RES, com.tink.moneymanagerui.R.style.tink_FinanceOverviewSampleStyle),
            Pair(ARG_ACCESS_TOKEN, TestConfiguration.sampleAccessToken),
            Pair(ARG_OVERVIEW_FEATURES, OverviewFeatures.ALL),
            Pair(ARG_IS_OVERVIEW_TOOLBAR_VISIBLE, true)
        )
        val scenario = launchFragmentInContainer<FinanceOverviewFragment>(fragmentArgs)
        Log.d(tag, "testEventFragment: LAUNCHED")
        var j = 0
        for (i in 1..10000)
           j = j + i
        Log.d(tag, "testEventFragment: DONE")
    }
}
/*

class TestApp: DaggerApplication(), HasSupportFragmentInjector {
    private val applicationInjector: AndroidInjector<TestApp> by lazy { DaggerTestAppComponent.builder().create(this) }

    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment> = supportFragmentInjector

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = applicationInjector

    override fun onCreate() {
        super.onCreate()
        applicationInjector.inject(this)
    }
}

@Component(
    modules = [AndroidInjectionModule::class, TestAppModule::class, TestMessageModule::class]
)
interface TestAppComponent : AndroidInjector<TestApp> {
    @Component.Builder
    abstract class Builder: AndroidInjector.Builder<TestApp>()
}

@Module(includes = [AppModule::class])
abstract class TestAppModule

@Module
class TestMessageModule {
    @Provides
    fun message() = "Hello test world."
}
 */