package com.tink.pfmsdk.di

import com.tink.pfmsdk.FinanceOverviewFragment
import com.tink.pfmsdk.FragmentCoordinator
import com.tink.pfmsdk.R
import com.tink.pfmsdk.TransitionCoordinator
import com.tink.pfmsdk.TransitionCoordinatorImpl
import com.tink.pfmsdk.TransitionDescription
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FragmentModule {

    @Singleton
    @Provides
    fun fragmentCoordinator(
        fragment: FinanceOverviewFragment,
        transitionCoordinator: TransitionCoordinatorImpl
    ) =
        FragmentCoordinator(fragment.childFragmentManager, R.id.fragmentRoot, transitionCoordinator)

    @Singleton
    @Provides
    fun providesTransitionCoordinatorImpl(transitions: Set<@JvmSuppressWildcards TransitionDescription>): TransitionCoordinatorImpl =
        TransitionCoordinatorImpl(transitions)

    @Singleton
    @Provides
    fun providesTransitionCoordinator(transitionCoordinator: TransitionCoordinatorImpl): TransitionCoordinator =
        transitionCoordinator
}
