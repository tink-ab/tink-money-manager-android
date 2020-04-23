package com.tink.pfmui.di

import com.tink.pfmui.FinanceOverviewFragment
import com.tink.pfmui.FragmentCoordinator
import com.tink.pfmui.R
import com.tink.pfmui.TransitionCoordinator
import com.tink.pfmui.TransitionCoordinatorImpl
import com.tink.pfmui.TransitionDescription
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class FragmentModule {

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
