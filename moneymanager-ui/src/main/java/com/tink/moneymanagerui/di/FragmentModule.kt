package com.tink.moneymanagerui.di

import com.tink.annotations.PfmScope
import com.tink.moneymanagerui.FinanceOverviewFragment
import com.tink.moneymanagerui.FragmentCoordinator
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.TransitionCoordinator
import com.tink.moneymanagerui.TransitionCoordinatorImpl
import com.tink.moneymanagerui.TransitionDescription
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
