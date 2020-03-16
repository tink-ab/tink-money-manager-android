package com.tink.pfmui.di

import com.tink.annotations.PfmScope
import com.tink.core.TinkComponent
import com.tink.pfmui.FinanceOverviewFragment
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@PfmScope
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
    ],
    dependencies = [TinkComponent::class]
)
internal interface FragmentComponent : AndroidInjector<FinanceOverviewFragment> {

    @Component.Factory
    interface Factory {
        fun create(
            tinkComponent: TinkComponent,
            @BindsInstance instance: FinanceOverviewFragment
        ): AndroidInjector<FinanceOverviewFragment>
    }
}

