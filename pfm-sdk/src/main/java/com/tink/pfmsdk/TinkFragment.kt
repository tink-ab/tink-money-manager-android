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
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class TinkFragment : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var i18nConfiguration: I18nConfiguration

    override fun androidInjector(): AndroidInjector<Any> = androidInjector


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        DaggerFragmentComponent.factory().create(this).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tink, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager
            .beginTransaction()
            .add(
                R.id.fragmentRoot,
                OverviewChartFragment()
            ).commit()
        i18nConfiguration.initialize()
    }


}