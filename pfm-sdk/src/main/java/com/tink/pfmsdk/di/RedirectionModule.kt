package com.tink.pfmsdk.di

import android.content.Context
import com.tink.pfmsdk.FragmentCoordinator
import com.tink.pfmsdk.redirection.DefaultRedirectionReceiver
import dagger.Module
import dagger.Provides
import se.tink.android.di.application.ApplicationScoped
import se.tink.android.redirection.RedirectionReceiver

@Module
internal class RedirectionModule {

    @Provides
    fun redirectionReceiver(
        @ApplicationScoped context: Context,
        fragmentCoordinator: FragmentCoordinator
    ) : RedirectionReceiver = DefaultRedirectionReceiver(context, fragmentCoordinator)
}