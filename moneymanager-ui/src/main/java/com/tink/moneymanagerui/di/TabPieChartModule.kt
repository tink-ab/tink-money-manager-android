package com.tink.moneymanagerui.di

import com.tink.moneymanagerui.FragmentCoordinator
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.TransitionCoordinatorImpl
import com.tink.moneymanagerui.statistics.piechart.FullPieChartFragment
import com.tink.moneymanagerui.statistics.piechart.HalfPieChartFragment
import com.tink.moneymanagerui.statistics.piechart.PieChartNavigation
import com.tink.moneymanagerui.statistics.piechart.TabPieChartFragment
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import javax.inject.Named
import javax.inject.Scope

@Scope
internal annotation class TabPieChart

@Module(includes = [TabPieChartFragmentsModule::class])
internal class TabPieChartModule {

    @Provides
    @TabPieChart
    @Named("tabpiechart")
    fun provideLocalFragmentCoordinator(
        fragment: TabPieChartFragment,
        transitionCoordinator: TransitionCoordinatorImpl
    ): FragmentCoordinator {
        return FragmentCoordinator(
            fragment.childFragmentManager,
            R.id.pieChartContainer,
            transitionCoordinator
        )
    }

    @Provides
    @TabPieChart
    fun provideNavigation(@Named("tabpiechart") coordinator: FragmentCoordinator): PieChartNavigation {
        return PieChartNavigation(coordinator)
    }
}

@Module
internal interface TabPieChartFragmentsModule {

    @ContributesAndroidInjector
    fun fullPieChartFragment(): FullPieChartFragment

    @ContributesAndroidInjector
    fun halfPieChartFragment(): HalfPieChartFragment
}
