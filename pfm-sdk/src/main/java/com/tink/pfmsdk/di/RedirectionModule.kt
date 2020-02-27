package com.tink.pfmsdk.di

import android.content.Context
import com.tink.pfmsdk.FragmentCoordinator
import com.tink.pfmsdk.redirection.DefaultRedirectionReceiver
import dagger.Module
import dagger.Provides
import se.tink.android.di.application.ApplicationScoped
import se.tink.android.redirection.RedirectionReceiver
import javax.inject.Singleton

@Module
internal class RedirectionModule {

    @Provides
    @Singleton
    fun redirectionReceiver(
        @ApplicationScoped context: Context,
        fragmentCoordinator: FragmentCoordinator
    ) : RedirectionReceiver = DefaultRedirectionReceiver(context, fragmentCoordinator)
}