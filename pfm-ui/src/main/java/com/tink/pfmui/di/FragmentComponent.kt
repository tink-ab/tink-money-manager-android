package com.tink.pfmui.di

import com.tink.pfmui.FinanceOverviewFragment
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

    @Component.Factory
    interface Factory : AndroidInjector.Factory<FinanceOverviewFragment>
}
