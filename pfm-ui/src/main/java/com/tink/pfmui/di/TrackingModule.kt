package com.tink.pfmui.di

import com.tink.pfmui.BuildConfig
import dagger.Module
import dagger.Provides
import se.tink.android.repository.ExceptionTracker

@Module
internal class TrackingModule {

    @Provides //TODO:PFMSDK
    fun exceptionTracker() = object : ExceptionTracker {
        override fun <E : Exception> exceptionThrown(e: E) {
            if(BuildConfig.DEBUG) {
                throw e;
            }
        }
    }
}