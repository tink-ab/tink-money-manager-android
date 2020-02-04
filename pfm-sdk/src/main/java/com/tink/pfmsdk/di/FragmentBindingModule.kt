package com.tink.pfmsdk.di

import com.tink.pfmsdk.overview.OverviewChartFragment
import com.tink.pfmsdk.overview.OverviewFragment
import com.tink.pfmsdk.overview.accounts.AccountsListFragment
import com.tink.pfmsdk.overview.charts.CategorySelectionFragment
import com.tink.pfmsdk.overview.charts.ChartDetailsPagerFragment
import com.tink.pfmsdk.overview.charts.TabExpensesBarChartFragment
import com.tink.pfmsdk.overview.charts.TabIncomeBarChartFragment
import com.tink.pfmsdk.overview.charts.TabLeftToSpendFragment
import com.tink.pfmsdk.overview.charts.piechart.TabPieChartFragment
import com.tink.pfmsdk.overview.latesttransactions.LatestTransactionsFragment
import com.tink.pfmsdk.transaction.CategorizationFlowFragment
import com.tink.pfmsdk.transaction.SimilarTransactionsFragment
import com.tink.pfmsdk.overview.accounts.AccountDetailsFragment
import com.tink.pfmsdk.transaction.TransactionsListFragment
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
    fun tabExpensesBarChartFragment(): TabExpensesBarChartFragment

    @ContributesAndroidInjector
    fun latestTransactionsFragment(): LatestTransactionsFragment

    @ContributesAndroidInjector
    fun tabLeftToSpendFragment(): TabLeftToSpendFragment

    @ContributesAndroidInjector
    fun tabIncomeBarChartFragment(): TabIncomeBarChartFragment

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
}
