package com.tink.moneymanagerui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.facebook.drawee.backends.pipeline.Fresco
import com.tink.core.Tink
import com.tink.model.user.User
import com.tink.moneymanagerui.buildConfig.BuildConfigurations
import com.tink.moneymanagerui.configuration.I18nConfiguration
import com.tink.moneymanagerui.di.DaggerFragmentComponent
import com.tink.moneymanagerui.insights.actionhandling.CustomInsightActionHandler
import com.tink.moneymanagerui.insights.actionhandling.InsightActionHandler
import com.tink.moneymanagerui.insights.actionhandling.PerformedActionNotifier
import com.tink.moneymanagerui.overview.OverviewFragment
import com.tink.moneymanagerui.repository.StatisticsRepository
import com.tink.moneymanagerui.security.DefaultRecoveryHandler
import com.tink.moneymanagerui.security.SecuredClientDataStorage
import com.tink.moneymanagerui.tracking.AnalyticsSingleton
import com.tink.moneymanagerui.tracking.Tracker
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import se.tink.android.repository.service.DataRefreshHandler
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
    internal lateinit var dataRefreshHandler: DataRefreshHandler

    @Inject
    internal lateinit var performedActionNotifier: PerformedActionNotifier

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Fresco.initialize(context)
        setupTimber()
        DaggerFragmentComponent
            .factory()
            .create(Tink.requireComponent(), this)
            .inject(this)
        Tink.setUser(User.fromAccessToken(accessToken))
        i18nConfiguration.initialize()
        refreshData()

        context.let(::initSecuredDataStorage)
        //TODO:PFMSDK: Do we need Fresco and Timber?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.theme?.applyStyle(tinkStyle, false)
        activity?.applicationContext?.theme?.applyStyle(tinkStyle, false)
        return inflater.inflate(R.layout.tink_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentCoordinator.clear()
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

    fun refreshData() {
        dataRefreshHandler.refreshCategories()
        dataRefreshHandler.refreshUserConfiguration()
        dataRefreshHandler.refreshRegistered()
    }

    fun setAccessToken(accessToken: String) {

        if (arguments == null) {
            arguments = bundleOf()
        }
        arguments!!.putString(ARG_ACCESS_TOKEN, accessToken)

        Tink.setUser(User.fromAccessToken(accessToken));
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
         * @param tracker An optional [Tracker] implementation
         * @param overviewFeatures The [OverviewFeatures] object with the list of overview features to be included
         * @param insightActionHandler The optional [InsightActionHandler] implementation for custom handling of [insight actions][Insight.Action]
         */
        @JvmOverloads
        @JvmStatic
        fun newInstance(
            accessToken: String,
            styleResId: Int,
            tracker: Tracker? = null,
            overviewFeatures: OverviewFeatures = OverviewFeatures.ALL,
            insightActionHandler: InsightActionHandler? = null
        ): FinanceOverviewFragment {
            AnalyticsSingleton.tracker = tracker
            insightActionHandler?.let { CustomInsightActionHandler.setInsightActionHandler(it) }
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
