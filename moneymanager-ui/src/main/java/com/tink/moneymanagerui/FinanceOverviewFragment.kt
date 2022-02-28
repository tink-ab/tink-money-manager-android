package com.tink.moneymanagerui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StyleRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.jakewharton.threetenabp.AndroidThreeTen
import com.tink.core.Tink
import com.tink.model.user.User
import com.tink.moneymanagerui.accounts.AccountEditConfiguration
import com.tink.moneymanagerui.accounts.AccountEditConfiguration.Companion.AllAccountFieldsEditable
import com.tink.moneymanagerui.accounts.AccountGroupType
import com.tink.moneymanagerui.accounts.NoAccountGroup
import com.tink.moneymanagerui.accounts.OverviewAccountsMode
import com.tink.moneymanagerui.accounts.OverviewFavoriteAccounts
import com.tink.moneymanagerui.buildConfig.BuildConfigurations
import com.tink.moneymanagerui.configuration.BackPressedConfiguration
import com.tink.moneymanagerui.configuration.I18nConfiguration
import com.tink.moneymanagerui.configuration.OnBackPressedListener
import com.tink.moneymanagerui.di.DaggerFragmentComponent
import com.tink.moneymanagerui.insights.actionhandling.CustomInsightActionHandler
import com.tink.moneymanagerui.insights.actionhandling.InsightActionHandler
import com.tink.moneymanagerui.insights.actionhandling.JavaInsightActionHandler
import com.tink.moneymanagerui.insights.actionhandling.PerformedActionNotifier
import com.tink.moneymanagerui.overview.OverviewFragment
import com.tink.moneymanagerui.repository.StatisticsRepository
import com.tink.moneymanagerui.security.DefaultRecoveryHandler
import com.tink.moneymanagerui.security.SecuredClientDataStorage
import com.tink.moneymanagerui.tracking.AnalyticsSingleton
import com.tink.moneymanagerui.tracking.Tracker
import com.tink.service.network.SdkClient
import com.tink.service.network.SdkInformation
import com.tink.service.network.coreSdkInformation
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.tink_fragment.*
import se.tink.android.repository.service.DataRefreshHandler
import se.tink.commons.BuildConfig
import timber.log.Timber
import java.io.IOException
import java.security.*
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

    private val isOverviewToolbarVisible: Boolean by lazy {
        requireArguments().getBoolean(ARG_IS_OVERVIEW_TOOLBAR_VISIBLE)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setupTimber()
        DaggerFragmentComponent
            .factory()
            .create(Tink.requireComponent(), this)
            .inject(this)
        Tink.setUser(User.fromAccessToken(accessToken))
        i18nConfiguration.initialize()
        refreshData()

        context.let(::initSecuredDataStorage)
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
        fragmentCoordinator.add(OverviewFragment.newInstance(overviewFeatures, isOverviewToolbarVisible), false, FragmentAnimationFlags.NONE)
        AndroidThreeTen.init(view.context.applicationContext)
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
        requireArguments().putString(ARG_ACCESS_TOKEN, accessToken)

        Tink.setUser(User.fromAccessToken(accessToken));
    }

    private fun setupTimber() {
        Timber.plant(BuildConfigurations.instance.loggingConfigurations.getTimberTree())
    }

    companion object {

        const val ARG_STYLE_RES = "styleRes"
        const val ARG_ACCESS_TOKEN = "accessToken"
        const val ARG_OVERVIEW_FEATURES = "overviewFeatures"
        const val ARG_IS_OVERVIEW_TOOLBAR_VISIBLE = "isOverviewToolbarVisible"

        @JvmStatic
        var featureSpecificThemes: Map<MoneyManagerFeatureType, Int> = mapOf()
            private set

        internal var accountGroupType: AccountGroupType = NoAccountGroup

        internal var accountEditConfiguration: AccountEditConfiguration = AllAccountFieldsEditable

        internal var overviewAccountsMode: OverviewAccountsMode = OverviewFavoriteAccounts

        /**
         * Creates a new instance of the [FinanceOverviewFragment].
         *
         * @param accessToken A valid access token for the user
         * @param styleResId The resource ID of your style that extends [R.style.TinkFinanceOverviewStyle]
         * @param tracker An optional [Tracker] implementation
         * @param overviewFeatures The [OverviewFeatures] object with the list of overview features to be included
         * @param insightActionHandler The optional [InsightActionHandler] implementation for custom handling of [insight actions][Insight.Action]
         * @param backPressedListener An optional [OnBackPressedListener] callback to listen to back presses in the SDK
         * @param isOverviewToolbarVisible Set if you want to show a toolbar for the overview fragment, defaults to false
         * @param javaInsightActionHandler an optional [JavaInsightActionHandler] you can use to handle insights when not using Kotlin, not used if insightActionHandler is set
         * @param featureSpecificThemes an optional Map where you can set specific themes for Money Manager's individual features. The keys in the map should be the [MoneyManagerFeatureType]
         * and the values the theme for that specific feature. It's recommended to have your feature specific theme inherit from your app's base theme
         */
        @JvmOverloads
        @JvmStatic
        fun newInstance(
            accessToken: String,
            @StyleRes styleResId: Int,
            tracker: Tracker? = null,
            overviewFeatures: OverviewFeatures = OverviewFeatures.ALL,
            insightActionHandler: InsightActionHandler? = null,
            backPressedListener: OnBackPressedListener? = null,
            isOverviewToolbarVisible: Boolean = false,
            javaInsightActionHandler: JavaInsightActionHandler? = null,
            featureSpecificThemes: Map<MoneyManagerFeatureType, Int> = emptyMap()
        ): FinanceOverviewFragment {
            AnalyticsSingleton.tracker = tracker
            coreSdkInformation = SdkInformation(SdkClient.MONEY_MANAGER, BuildConfig.libraryVersion)
            if (insightActionHandler != null) {
                CustomInsightActionHandler.setInsightActionHandler(insightActionHandler)
            } else if (javaInsightActionHandler != null) {
                CustomInsightActionHandler.setInsightActionHandler(javaInsightActionHandler.toInsightActionHandler())
            }
            BackPressedConfiguration.backPressedListener = backPressedListener
            this.featureSpecificThemes = featureSpecificThemes
            return FinanceOverviewFragment().apply {
                arguments = bundleOf(
                    ARG_ACCESS_TOKEN to accessToken,
                    ARG_STYLE_RES to styleResId,
                    ARG_OVERVIEW_FEATURES to overviewFeatures,
                    ARG_IS_OVERVIEW_TOOLBAR_VISIBLE to isOverviewToolbarVisible
                )
            }
        }
    }
}

public enum class MoneyManagerFeatureType {
    ACCOUNTS,
    ACTIONABLE_INSIGHTS,
    BUDGETS,
    STATISTICS;
}