package com.tink.moneymanagerui.di

import com.tink.annotations.PfmScope
import com.tink.moneymanagerui.AppExecutorsDefaultImpl
import com.tink.service.util.DefaultDispatcherProvider
import com.tink.service.util.DispatcherProvider

import dagger.Binds
import dagger.Module
import se.tink.android.AppExecutors
import javax.inject.Singleton

@Module
internal interface UtilsModule {

    @Binds
    @PfmScope
    fun provideAppExecutors(executors: AppExecutorsDefaultImpl): AppExecutors

    @Binds
    @PfmScope
    fun provideDispatcherProvider(dispatcherProvider: DefaultDispatcherProvider): DispatcherProvider
}
