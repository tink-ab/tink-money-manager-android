package com.tink.moneymanagerui.di

import com.tink.moneymanagerui.TransitionDescription
import com.tink.moneymanagerui.overview.OverviewChartTransition
import com.tink.moneymanagerui.overview.charts.piechart.FullToHalfChartTransition
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
}