package com.tink.pfmui.di

import com.tink.pfmui.FragmentCoordinator
import com.tink.pfmui.R
import com.tink.pfmui.TransitionCoordinatorImpl
import com.tink.pfmui.overview.charts.piechart.FullPieChartFragment
import com.tink.pfmui.overview.charts.piechart.HalfPieChartFragment
import com.tink.pfmui.overview.charts.piechart.PieChartNavigation
import com.tink.pfmui.overview.charts.piechart.TabPieChartFragment
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