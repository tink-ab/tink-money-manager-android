package com.tink.pfmui.di

import com.tink.annotations.PfmScope
import com.tink.pfmui.FinanceOverviewFragment
import com.tink.pfmui.FragmentCoordinator
import com.tink.pfmui.R
import com.tink.pfmui.TransitionCoordinator
import com.tink.pfmui.TransitionCoordinatorImpl
import com.tink.pfmui.TransitionDescription
import dagger.Module
import dagger.Provides

@Module
internal class FragmentModule {

    @PfmScope
    @Provides
    fun fragmentCoordinator(
        fragment: FinanceOverviewFragment,
        transitionCoordinator: TransitionCoordinatorImpl
    ) =
        FragmentCoordinator(fragment.childFragmentManager, R.id.fragmentRoot, transitionCoordinator)

    @PfmScope
    @Provides
    fun providesTransitionCoordinatorImpl(transitions: Set<@JvmSuppressWildcards TransitionDescription>): TransitionCoordinatorImpl =
        TransitionCoordinatorImpl(transitions)

    @PfmScope
    @Provides
    fun providesTransitionCoordinator(transitionCoordinator: TransitionCoordinatorImpl): TransitionCoordinator =
        transitionCoordinator
}
