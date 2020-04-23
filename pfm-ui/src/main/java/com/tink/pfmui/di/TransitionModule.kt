package com.tink.pfmui.di

import com.tink.pfmui.TransitionDescription
import com.tink.pfmui.overview.OverviewChartTransition
import com.tink.pfmui.overview.charts.ChartToCategoryTransition
import com.tink.pfmui.overview.charts.piechart.FullToHalfChartTransition
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet

@Module
internal interface TransitionModule {

    @Binds
    @IntoSet
    fun overviewChartToPieChartTransition(model: OverviewChartTransition): TransitionDescription

    @Binds
    @IntoSet
    fun fullChartToHalfChartTransition(model: FullToHalfChartTransition): TransitionDescription

    @Binds
    @IntoSet
    fun chartToCategoriesTransition(model: ChartToCategoryTransition): TransitionDescription
}