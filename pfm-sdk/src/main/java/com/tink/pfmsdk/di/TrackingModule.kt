package com.tink.pfmsdk.di

import com.tink.pfmsdk.BuildConfig
import dagger.Module
import dagger.Provides
import se.tink.repository.ExceptionTracker

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
