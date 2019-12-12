package com.tink.pfmsdk.di

import com.tink.pfmsdk.AppExecutorsDefaultImpl
import com.tink.pfmsdk.FinanceOverviewFragment
import com.tink.pfmsdk.FragmentCoordinator
import com.tink.pfmsdk.R
import com.tink.pfmsdk.TransitionCoordinator
import com.tink.pfmsdk.TransitionCoordinatorImpl
import com.tink.pfmsdk.TransitionDescription
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import se.tink.android.AppExecutors
import se.tink.android.di.application.ApplicationScoped
import se.tink.repository.DefaultErrorHandler
import se.tink.repository.ExceptionTracker
import se.tink.repository.TinkNetworkErrorHandler
import se.tink.repository.service.DeviceService
import se.tink.repository.service.DeviceServiceImpl
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
        AllBindings::class,
        ViewModelModule::class,
        FragmentBindingModule::class
    ]
)
interface FragmentComponent : AndroidInjector<FinanceOverviewFragment> {

    @Component.Factory
    interface Factory : AndroidInjector.Factory<FinanceOverviewFragment>
}

@Module
class ContextModule {

    @Provides
    @ApplicationScoped
    fun context(fragment: FinanceOverviewFragment) = fragment.context!!.applicationContext
}

@Module
interface AllBindings {

    @Binds
    fun deviceService(deviceServiceImpl: DeviceServiceImpl): DeviceService

    @Binds
    fun errorHandler(defaultErrorHandler: DefaultErrorHandler): TinkNetworkErrorHandler

    @Binds
    @Singleton
    fun provideAppExecutors(executors: AppExecutorsDefaultImpl): AppExecutors
}

