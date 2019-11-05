package com.tink.pfmsdk

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tink.pfmsdk.configuration.I18nConfiguration
import com.tink.pfmsdk.di.DaggerFragmentComponent
import com.tink.pfmsdk.overview.OverviewChartFragment
import com.tink.pfmsdk.overview.latesttransactions.LatestTransactionsFragment
import com.tink.pfmsdk.security.DefaultRecoveryHandler
import com.tink.pfmsdk.security.SecuredClientDataStorage
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
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

    /*
		Injects all singleton services that has a cached implementation. This needs to be done before the streaming
		starts because a side effect of the initialization of each "cached service" is to setup the
		cache as a streaming listener. And if we get events before this happens the data will be lost
	 */
    @Inject
    lateinit var serviceCacheInitialization: ServiceCacheInitialization

    override fun androidInjector(): AndroidInjector<Any> = androidInjector


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        DaggerFragmentComponent.factory().create(this).inject(this)
        i18nConfiguration.initialize()

        context?.let(::initSecuredDataStorage)
        //TODO:PFMSDK: Do we need Fresco and Timber?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tink, container, false)
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
}
