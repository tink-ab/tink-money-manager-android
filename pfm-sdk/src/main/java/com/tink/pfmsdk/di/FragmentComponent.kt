package com.tink.pfmsdk.di

import android.content.Context
import com.tink.pfmsdk.AppExecutorsDefaultImpl
import com.tink.pfmsdk.ClientDataStorage
import com.tink.pfmsdk.FinanceOverviewFragment
import com.tink.pfmsdk.FragmentCoordinator
import com.tink.pfmsdk.R
import com.tink.pfmsdk.Timezone
import com.tink.pfmsdk.TimezoneManager
import com.tink.pfmsdk.TransitionCoordinator
import com.tink.pfmsdk.TransitionCoordinatorImpl
import com.tink.pfmsdk.TransitionDescription
import com.tink.pfmsdk.configuration.SuitableLocaleFinder
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
import se.tink.utils.DateUtils
import java.util.Locale
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ContextModule::class,
        CurrencyModule::class,
        ThemingModule::class,
        EverythingModule::class,
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

@Module(includes = [TransitionModule::class, ServiceModule::class, NetworkModule::class])
class EverythingModule {

    @Singleton
    @Provides
    fun fragmentCoordinator(
        fragment: FinanceOverviewFragment,
        transitionCoordinator: TransitionCoordinatorImpl
    ) =
        FragmentCoordinator(fragment.childFragmentManager, R.id.fragmentRoot, transitionCoordinator)

    @Singleton
    @Provides
    fun providesTransitionCoordinatorImpl(transitions: Set<@JvmSuppressWildcards TransitionDescription>): TransitionCoordinatorImpl =
        TransitionCoordinatorImpl(transitions)

    @Singleton
    @Provides
    fun providesTransitionCoordinator(transitionCoordinator: TransitionCoordinatorImpl): TransitionCoordinator =
        transitionCoordinator

    @Provides
    @Singleton
    fun provideClientStorage(@ApplicationScoped context: Context): ClientDataStorage =
        ClientDataStorage.sharedInstance(context)

    @Provides
    fun provideLocale(localeFinder: SuitableLocaleFinder): Locale {
        return localeFinder.findLocale()
    }

    @Provides
    fun provideTimezone(): Timezone = TimezoneManager.defaultTimezone

    @Provides
    fun provideDateUtils(locale: Locale, timezone: Timezone): DateUtils =
        DateUtils.getInstance(locale, timezone)


    @Provides //TODO:PFMSDK
    fun exceptionTracker() = object : ExceptionTracker {
        override fun <E : Exception> exceptionThrown(e: E) {
            throw e
        }
    }
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

