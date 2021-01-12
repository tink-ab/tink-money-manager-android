package com.tink.pfmui.di

import android.content.Context
import com.tink.annotations.PfmScope
import com.tink.pfmui.FragmentCoordinator
import com.tink.pfmui.redirection.DefaultRedirectionReceiver
import dagger.Module
import dagger.Provides
import se.tink.android.di.application.ApplicationScoped
import se.tink.android.redirection.RedirectionReceiver

@Module
internal class RedirectionModule {

    @Provides
    @PfmScope
    fun redirectionReceiver(
        @ApplicationScoped context: Context,
        fragmentCoordinator: FragmentCoordinator
    ) : RedirectionReceiver = DefaultRedirectionReceiver(context, fragmentCoordinator)
}