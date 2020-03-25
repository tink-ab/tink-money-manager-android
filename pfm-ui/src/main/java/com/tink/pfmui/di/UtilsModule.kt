package com.tink.pfmui.di

import com.tink.annotations.PfmScope
import com.tink.pfmui.AppExecutorsDefaultImpl
import dagger.Binds
import dagger.Module
import se.tink.android.AppExecutors

@Module
internal interface UtilsModule {

    @Binds
    @PfmScope
    fun provideAppExecutors(executors: AppExecutorsDefaultImpl): AppExecutors
}
