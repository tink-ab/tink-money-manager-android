package com.tink.pfmsdk.di

import com.tink.pfmsdk.TransitionDescription
import com.tink.pfmsdk.overview.OverviewChartTransition
import com.tink.pfmsdk.overview.charts.ChartToCategoryTransition
import com.tink.pfmsdk.overview.charts.piechart.FullToHalfChartTransition
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