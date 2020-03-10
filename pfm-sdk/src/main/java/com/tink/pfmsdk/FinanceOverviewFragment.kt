package com.tink.pfmsdk

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.facebook.drawee.backends.pipeline.Fresco
import com.tink.pfmsdk.buildConfig.BuildConfigurations
import com.tink.pfmsdk.collections.Categories
import com.tink.pfmsdk.collections.Periods
import com.tink.pfmsdk.configuration.I18nConfiguration
import com.tink.pfmsdk.di.DaggerFragmentComponent
import com.tink.pfmsdk.insights.actionhandling.CustomInsightActionHandler
import com.tink.pfmsdk.insights.actionhandling.InsightActionHandler
import com.tink.pfmsdk.overview.OverviewFragment
import com.tink.pfmsdk.repository.StatisticsRepository
import com.tink.pfmsdk.security.DefaultRecoveryHandler
import com.tink.pfmsdk.security.SecuredClientDataStorage
import com.tink.pfmsdk.tracking.AnalyticsSingleton
import com.tink.pfmsdk.tracking.Tracker
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import se.tink.repository.cache.CacheHandle
import se.tink.repository.service.CategoryService
import se.tink.repository.service.DataRefreshHandler
import se.tink.repository.service.HeaderClientInterceptor
import timber.log.Timber
import java.io.IOException
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException
import java.security.UnrecoverableEntryException
import java.security.cert.CertificateException
import javax.crypto.NoSuchPaddingException
import javax.inject.Inject

/**
 * Fragment responsible for displaying an overview of the end-user's finances.
 * This is the entry point to the various flows which are part of the finance overview UI.
 */
class FinanceOverviewFragment : Fragment(), HasAndroidInjector {

    @Inject
    internal lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    internal lateinit var i18nConfiguration: I18nConfiguration

    @Inject
    internal lateinit var fragmentCoordinator: FragmentCoordinator

    @Inject
    internal lateinit var interceptor: HeaderClientInterceptor

    @Inject
    internal lateinit var categoryService: CategoryService

    @Inject
    internal lateinit var cacheHandle: CacheHandle

    @Inject
    internal lateinit var dataRefreshHandler: DataRefreshHandler

    /*
		Injects all singleton services that has a cached implementation. This needs to be done before the streaming
		starts because a side effect of the initialization of each "cached service" is to setup the
		cache as a streaming listener. And if we get events before this happens the data will be lost
	 */
    @Inject
    internal lateinit var serviceCacheInitialization: ServiceCacheInitialization

    @Inject
    internal lateinit var statisticsRepository: StatisticsRepository

    private val tinkStyle by lazy {
        requireNotNull(arguments?.getInt(ARG_STYLE_RES))
    }

    private val accessToken by lazy {
        requireNotNull(arguments?.getString(ARG_ACCESS_TOKEN))
    }

    private val overviewFeatures: OverviewFeatures by lazy {
        requireNotNull(arguments?.getParcelable<OverviewFeatures>(ARG_OVERVIEW_FEATURES))
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Fresco.initialize(context)
        setupTimber()
        DaggerFragmentComponent.factory().create(this).inject(this)
        interceptor.setAccessToken(accessToken)
        attachListeners()
        i18nConfiguration.initialize()
        refreshData()

        context?.let(::initSecuredDataStorage)
        //TODO:PFMSDK: Do we need Fresco and Timber?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.theme?.applyStyle(tinkStyle, false)
        activity?.applicationContext?.theme?.applyStyle(tinkStyle, false)
        return inflater.inflate(R.layout.fragment_tink, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentCoordinator.add(OverviewFragment.newInstance(overviewFeatures), false, FragmentAnimationFlags.NONE)
    }

    fun handleBackPress() =
        if (fragmentCoordinator.backStackEntryCount > 0) {
            fragmentCoordinator.handleBackPress()
        } else {
            false
        }

    //TODO:PFMSDK: This should be removed later, since we should not be responsible for handling sensitive data
    fun initSecuredDataStorage(context: Context) {
        try {
            SecuredClientDataStorage.init(context.applicationContext, DefaultRecoveryHandler())
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: CertificateException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyStoreException) {
            e.printStackTrace()
        } catch (e: UnrecoverableEntryException) {
            e.printStackTrace()
        } catch (e: InvalidAlgorithmParameterException) {
            e.printStackTrace()
        } catch (e: NoSuchPaddingException) {
            e.printStackTrace()
        } catch (e: InvalidKeyException) {
            e.printStackTrace()
        } catch (e: NoSuchProviderException) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        removeListenersAndClearCache()
    }

    fun refreshData() {
        dataRefreshHandler.refreshCategories()
        dataRefreshHandler.refreshStatistics()
        dataRefreshHandler.refreshUserConfiguration()
        dataRefreshHandler.refreshRegistered()
    }

    fun setAccessToken(accessToken: String) {

        if (arguments == null) {
            arguments = bundleOf()
        }
        arguments!!.putString(ARG_ACCESS_TOKEN, accessToken)

        if (::interceptor.isInitialized) {
            interceptor.setAccessToken(accessToken)
        }
    }

    private fun attachListeners() {
        Categories.getSharedInstance().attatchListener(categoryService)
        Periods.getSharedInstance().attatchListener(statisticsRepository)
    }

    private fun removeListenersAndClearCache() {
        Categories.getSharedInstance().removeListener(categoryService)
        Periods.getSharedInstance().removeListener(statisticsRepository)
        cacheHandle.clearCache()
    }

    private fun setupTimber() {
        Timber.plant(BuildConfigurations.instance.loggingConfigurations.getTimberTree())
    }

    companion object {

        private const val ARG_STYLE_RES = "styleRes"
        private const val ARG_ACCESS_TOKEN = "accessToken"
        private const val ARG_OVERVIEW_FEATURES = "overviewFeatures"

        /**
         * Creates a new instance of the [FinanceOverviewFragment].
         *
         * @param accessToken A valid access token for the user
         * @param styleResId The resource ID of your style that extends [R.style.TinkFinanceOverviewStyle]
         * @param clientConfiguration The [ClientConfiguration] object
         * @param tracker An optional [Tracker] implementation
         * @param overviewFeatures The [OverviewFeatures] object with the list of overview features to be included
         * @param insightActionHandler The optional [InsightActionHandler] implementation for custom handling of [insight actions][Insight.Action]
         */
        @JvmOverloads
        @JvmStatic
        fun newInstance(
            accessToken: String,
            styleResId: Int,
            clientConfiguration: ClientConfiguration,
            tracker: Tracker? = null,
            overviewFeatures: OverviewFeatures = OverviewFeatures.ALL,
            insightActionHandler: InsightActionHandler? = null
        ): FinanceOverviewFragment {
            AnalyticsSingleton.tracker = tracker
            insightActionHandler?.let { CustomInsightActionHandler.setInsightActionHandler(it) }
            NetworkConfigSingleton.apply {
                endpoint = clientConfiguration.environment.grpcUrl
                sslCertificate = clientConfiguration.sslCertificate
                port = clientConfiguration.environment.port
            }
            return FinanceOverviewFragment().apply {
                arguments = bundleOf(
                    ARG_ACCESS_TOKEN to accessToken,
                    ARG_STYLE_RES to styleResId,
                    ARG_OVERVIEW_FEATURES to overviewFeatures
                )
            }
        }
    }
}
