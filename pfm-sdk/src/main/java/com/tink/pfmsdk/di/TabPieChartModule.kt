package com.tink.pfmsdk.di

import com.tink.pfmsdk.FragmentCoordinator
import com.tink.pfmsdk.R
import com.tink.pfmsdk.TransitionCoordinatorImpl
import com.tink.pfmsdk.overview.charts.piechart.FullPieChartFragment
import com.tink.pfmsdk.overview.charts.piechart.HalfPieChartFragment
import com.tink.pfmsdk.overview.charts.piechart.PieChartNavigation
import com.tink.pfmsdk.overview.charts.piechart.TabPieChartFragment
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import javax.inject.Named
import javax.inject.Scope

@Scope
annotation class TabPieChart

@Module(includes = [TabPieChartFragmentsModule::class])
class TabPieChartModule {

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
interface TabPieChartFragmentsModule {

    @ContributesAndroidInjector
    fun fullPieChartFragment(): FullPieChartFragment

    @ContributesAndroidInjector
    fun halfPieChartFragment(): HalfPieChartFragment

}