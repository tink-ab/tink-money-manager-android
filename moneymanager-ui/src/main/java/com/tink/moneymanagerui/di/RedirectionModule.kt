package com.tink.moneymanagerui.di

import android.content.Context
import com.tink.annotations.PfmScope
import com.tink.moneymanagerui.FragmentCoordinator
import com.tink.moneymanagerui.redirection.DefaultRedirectionReceiver
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