package com.tink.pfmsdk.di

import com.tink.pfmsdk.FinanceOverviewFragment
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
        FragmentBindingModule::class
    ]
)
internal interface FragmentComponent : AndroidInjector<FinanceOverviewFragment> {

    @Component.Factory
    interface Factory : AndroidInjector.Factory<FinanceOverviewFragment>
}
