package com.tink.pfmsdk

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.tink.pfmsdk.collections.Categories
import com.tink.pfmsdk.configuration.I18nConfiguration
import com.tink.pfmsdk.di.DaggerFragmentComponent
import com.tink.pfmsdk.overview.OverviewFragment
import com.tink.pfmsdk.security.DefaultRecoveryHandler
import com.tink.pfmsdk.security.SecuredClientDataStorage
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import se.tink.repository.cache.CacheHandle
import se.tink.repository.service.CategoryService
import se.tink.repository.service.DataRefreshHandler
import se.tink.repository.service.HeaderClientInterceptor
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

class TinkFragment : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var i18nConfiguration: I18nConfiguration

    @Inject
    lateinit var fragmentCoordinator: FragmentCoordinator

    @Inject
    lateinit var interceptor: HeaderClientInterceptor

    @Inject
    lateinit var categoryService: CategoryService

    @Inject
    lateinit var cacheHandle: CacheHandle

    @Inject
    lateinit var dataRefreshHandler: DataRefreshHandler

    /*
		Injects all singleton services that has a cached implementation. This needs to be done before the streaming
		starts because a side effect of the initialization of each "cached service" is to setup the
		cache as a streaming listener. And if we get events before this happens the data will be lost
	 */
    //@Inject
    //lateinit var serviceCacheInitialization: ServiceCacheInitialization

    val tinkStyle by lazy {
        requireNotNull(arguments?.getInt(ARG_STYLE_RES))
    }

    val clientConfiguration by lazy {
        requireNotNull(arguments?.getParcelable(ARG_CLIENT_CONFIGURATION) as? ClientConfiguration)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        DaggerFragmentComponent.factory().create(this).inject(this)
        interceptor.setAccessToken(clientConfiguration.accessToken)
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
        fragmentCoordinator.add(OverviewFragment(), false, FragmentAnimationFlags.NONE)
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
    }

    private fun attachListeners() {
        Categories.getSharedInstance().attatchListener(categoryService)
    }

    private fun removeListenersAndClearCache() {
        Categories.getSharedInstance().removeListener(categoryService)
        cacheHandle.clearCache()
    }

    companion object {

        private const val ARG_STYLE_RES = "styleRes"
        private const val ARG_CLIENT_CONFIGURATION = "clientConfiguration"

        fun newInstance(styleResId: Int, clientConfiguration: ClientConfiguration) =
            TinkFragment().apply {
                arguments = bundleOf(
                    ARG_STYLE_RES to styleResId,
                    ARG_CLIENT_CONFIGURATION to clientConfiguration
                )
            }
    }
}
