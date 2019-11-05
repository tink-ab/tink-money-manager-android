package com.tink.pfmsdk.di

import android.content.Context
import com.tink.pfmsdk.AppExecutorsDefaultImpl
import com.tink.pfmsdk.ClientDataStorage
import com.tink.pfmsdk.FragmentCoordinator
import com.tink.pfmsdk.R
import com.tink.pfmsdk.Timezone
import com.tink.pfmsdk.TimezoneManager
import com.tink.pfmsdk.TinkFragment
import com.tink.pfmsdk.TransitionCoordinator
import com.tink.pfmsdk.TransitionCoordinatorImpl
import com.tink.pfmsdk.TransitionDescription
import com.tink.pfmsdk.analytics.Analytics
import com.tink.pfmsdk.configuration.SuitableLocaleFinder
import com.tink.pfmsdk.overview.OverviewChartFragment
import com.tink.pfmsdk.overview.charts.ChartDetailsPagerFragment
import com.tink.pfmsdk.overview.charts.TabExpensesBarChartFragment
import com.tink.pfmsdk.overview.charts.piechart.TabPieChartFragment
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
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
//        BaseFragmentModule::class,
        EverythingModule::class,
        AllBindings::class,
        ViewModelModule::class,
        FragmentBindingModule::class
    ]
)
interface FragmentComponent : AndroidInjector<TinkFragment> {

    @Component.Factory
    interface Factory : AndroidInjector.Factory<TinkFragment>
}


//@Module
//interface BaseFragmentModule {
//
//    @ContributesAndroidInjector(
//        modules = [
//            EverythingModule::class,
//            AllBindings::class,
//            ViewModelModule::class,
//            FragmentBindingModule::class
//        ]
//    )
//    fun baseFragment(): BaseFragment
//
////    @Binds
////    @ApplicationScoped
////    fun bindContext(fragment: BaseFragment): Context
//}

@Module
class ContextModule {

    @Provides
    @ApplicationScoped
    fun context(fragment: TinkFragment) = fragment.context!!.applicationContext
}

@Module(includes = [TransitionModule::class, ServiceModule::class, NetworkModule::class])
class EverythingModule {

    @Provides
    fun fragmentCoordinator(
        fragment: TinkFragment,
        transitionCoordinator: TransitionCoordinatorImpl
    ) =
        FragmentCoordinator(fragment.childFragmentManager, R.id.fragmentRoot, transitionCoordinator)

    @Provides
    fun providesTransitionCoordinatorImpl(transitions: Set<@JvmSuppressWildcards TransitionDescription>): TransitionCoordinatorImpl =
        TransitionCoordinatorImpl(transitions)

    @Provides
    fun providesTransitionCoordinator(transitionCoordinator: TransitionCoordinatorImpl): TransitionCoordinator =
        transitionCoordinator

    @Provides
    fun analytics() = Analytics(setOf())

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

@Module
interface FragmentBindingModule {

    @ContributesAndroidInjector
    fun overviewChartFragment(): OverviewChartFragment

    @ContributesAndroidInjector
    fun chartDetailsPagerFragment(): ChartDetailsPagerFragment

    @TabPieChart
    @ContributesAndroidInjector(modules = [TabPieChartModule::class])
    fun tabPieChartFragment(): TabPieChartFragment

    @ContributesAndroidInjector
    fun tabExpensesBarChartFragment(): TabExpensesBarChartFragment
}