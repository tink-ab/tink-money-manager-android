package com.tink.pfmui.di

import com.tink.pfmui.AppExecutorsDefaultImpl
import dagger.Binds
import dagger.Module
import se.tink.android.AppExecutors
import se.tink.repository.DefaultErrorHandler
import se.tink.repository.TinkNetworkErrorHandler
import javax.inject.Singleton

@Module
internal interface UtilsModule {

    @Binds
    fun errorHandler(defaultErrorHandler: DefaultErrorHandler): TinkNetworkErrorHandler

    @Binds
    @Singleton
    fun provideAppExecutors(executors: AppExecutorsDefaultImpl): AppExecutors
}
