package com.tink.pfmui.di

import com.tink.pfmui.FinanceOverviewFragment
import com.tink.pfmui.buildConfig.NetworkConfiguration
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ContextModule::class,
        ConfigurationModule::class,
        CurrencyModule::class,
        FragmentModule::class,
        NetworkModule::class,
        ServiceModule::class,
        ThemingModule::class,
        TrackingModule::class,
        TransitionModule::class,
        UtilsModule::class,
        ViewModelModule::class,
        RedirectionModule::class,
        FragmentBindingModule::class
    ]
)
internal interface FragmentComponent : AndroidInjector<FinanceOverviewFragment> {

    @Component.Builder
    interface Factory {
        @BindsInstance
        fun networkConfiguration(networkConfiguration: NetworkConfiguration): Factory

        @BindsInstance
        fun fragment(financeOverviewFragment: FinanceOverviewFragment): Factory

        fun create(): FragmentComponent
    }
}
