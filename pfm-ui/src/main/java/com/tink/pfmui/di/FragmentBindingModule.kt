package com.tink.pfmui.di

import com.tink.pfmui.insights.di.InsightsModule
import com.tink.pfmui.insights.fragments.ArchivedInsightsFragment
import com.tink.pfmui.insights.fragments.InsightsFragment
import com.tink.pfmui.insights.fragments.OverviewInsightsFragment
import com.tink.pfmui.overview.OverviewChartFragment
import com.tink.pfmui.overview.OverviewFragment
import com.tink.pfmui.overview.accounts.AccountDetailsFragment
import com.tink.pfmui.overview.accounts.AccountsListFragment
import com.tink.pfmui.overview.charts.CategorySelectionFragment
import com.tink.pfmui.overview.charts.ChartDetailsPagerFragment
import com.tink.pfmui.overview.charts.StatisticsOverTimeFragment
import com.tink.pfmui.overview.charts.TabLeftToSpendFragment
import com.tink.pfmui.overview.charts.piechart.TabPieChartFragment
import com.tink.pfmui.overview.latesttransactions.LatestTransactionsFragment
import com.tink.pfmui.transaction.CategorizationFlowFragment
import com.tink.pfmui.transaction.SimilarTransactionsFragment
import com.tink.pfmui.transaction.TransactionsListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal interface FragmentBindingModule {

    @ContributesAndroidInjector
    fun overviewChartFragment(): OverviewChartFragment

    @ContributesAndroidInjector
    fun chartDetailsPagerFragment(): ChartDetailsPagerFragment

    @TabPieChart
    @ContributesAndroidInjector(modules = [TabPieChartModule::class])
    fun tabPieChartFragment(): TabPieChartFragment

    @ContributesAndroidInjector
    fun latestTransactionsFragment(): LatestTransactionsFragment

    @ContributesAndroidInjector
    fun tabLeftToSpendFragment(): TabLeftToSpendFragment

    @ContributesAndroidInjector
    fun categoryListFragment(): CategorySelectionFragment

    @ContributesAndroidInjector
    fun transactionListFragment(): TransactionsListFragment

    @ContributesAndroidInjector
    fun categorizationFlowFragment(): CategorizationFlowFragment

    @ContributesAndroidInjector
    fun similarTransactionsFragment(): SimilarTransactionsFragment

    @ContributesAndroidInjector
    fun overviewFragment(): OverviewFragment

    @ContributesAndroidInjector
    fun accountsListFragment(): AccountsListFragment

    @ContributesAndroidInjector
    fun accountDetailsFragment(): AccountDetailsFragment

    @ContributesAndroidInjector(modules = [InsightsModule::class])
    fun overviewInsightsFragment(): OverviewInsightsFragment

    @ContributesAndroidInjector(modules = [InsightsModule::class])
    fun insightsFragment(): InsightsFragment

    @ContributesAndroidInjector(modules = [InsightsModule::class])
    fun archivedInsightsFragment(): ArchivedInsightsFragment

    @ContributesAndroidInjector
    fun statisticsOverTimeFragment(): StatisticsOverTimeFragment
}
