package com.tink.pfmsdk.di

import dagger.Module
import dagger.Provides
import se.tink.repository.ExceptionTracker

@Module
class TrackingModule {

    @Provides //TODO:PFMSDK
    fun exceptionTracker() = object : ExceptionTracker {
        override fun <E : Exception> exceptionThrown(e: E) {
            throw e
        }
    }
}
