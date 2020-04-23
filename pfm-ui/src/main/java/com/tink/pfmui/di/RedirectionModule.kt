package com.tink.pfmui.di

import android.content.Context
import com.tink.pfmui.FragmentCoordinator
import com.tink.pfmui.redirection.DefaultRedirectionReceiver
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