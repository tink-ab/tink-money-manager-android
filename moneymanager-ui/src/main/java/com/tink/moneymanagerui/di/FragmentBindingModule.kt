package com.tink.moneymanagerui.di

import com.tink.moneymanagerui.accounts.list.AccountDetailsListFragment
import com.tink.moneymanagerui.budgets.creation.BudgetCreationFragment
import com.tink.moneymanagerui.budgets.creation.di.BudgetCreation
import com.tink.moneymanagerui.budgets.creation.di.BudgetCreationModule
import com.tink.moneymanagerui.budgets.details.BudgetDetailsFragment
import com.tink.moneymanagerui.budgets.details.di.BudgetDetails
import com.tink.moneymanagerui.budgets.details.di.BudgetDetailsModule
import com.tink.moneymanagerui.insights.di.InsightsModule
import com.tink.moneymanagerui.insights.fragments.ArchivedInsightsFragment
import com.tink.moneymanagerui.insights.fragments.InsightsFragment
import com.tink.moneymanagerui.insights.fragments.OverviewInsightsFragment
import com.tink.moneymanagerui.overview.OverviewChartFragment
import com.tink.moneymanagerui.overview.OverviewFragment
import com.tink.moneymanagerui.overview.accounts.AccountDetailsFragment
import com.tink.moneymanagerui.overview.accounts.AccountsListFragment
import com.tink.moneymanagerui.overview.budgets.BudgetsOverviewFragment
import com.tink.moneymanagerui.overview.charts.CategorySelectionFragment
import com.tink.moneymanagerui.overview.charts.ChartDetailsPagerFragment
import com.tink.moneymanagerui.overview.charts.StatisticsOverTimeFragment
import com.tink.moneymanagerui.overview.charts.piechart.TabPieChartFragment
import com.tink.moneymanagerui.overview.latesttransactions.LatestTransactionsFragment
import com.tink.moneymanagerui.transaction.CategorizationFlowFragment
import com.tink.moneymanagerui.transaction.SimilarTransactionsFragment
import com.tink.moneymanagerui.transaction.TransactionsListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal interface FragmentBindingModule {

    @ContributesAndroidInjector
    fun  overviewChartFragment(): OverviewChartFragment

    @ContributesAndroidInjector
    fun chartDetailsPagerFragment(): ChartDetailsPagerFragment

    @TabPieChart
    @ContributesAndroidInjector(modules = [TabPieChartModule::class])
    fun tabPieChartFragment(): TabPieChartFragment

    @ContributesAndroidInjector
    fun latestTransactionsFragment(): LatestTransactionsFragment

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
    fun accountListFragment(): AccountDetailsListFragment

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

    @BudgetCreation
    @ContributesAndroidInjector(modules = [BudgetCreationModule::class])
    fun budgetCreationFragment(): BudgetCreationFragment

    @BudgetDetails
    @ContributesAndroidInjector(modules = [BudgetDetailsModule::class])
    fun budgetDetailsFragment(): BudgetDetailsFragment

    @ContributesAndroidInjector
    fun budgetsOverviewFragment(): BudgetsOverviewFragment
}
