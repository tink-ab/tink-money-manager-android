package com.tink.moneymanagerui.di

import androidx.lifecycle.ViewModel
import com.tink.moneymanagerui.accounts.details.AccountDetailsViewModel
import com.tink.moneymanagerui.accounts.edit.AccountDetailsEditViewModel
import com.tink.moneymanagerui.accounts.list.AccountsViewModel
import com.tink.moneymanagerui.category.CategorySelectionViewModel
import com.tink.moneymanagerui.overview.OverviewChartViewModel
import com.tink.moneymanagerui.overview.budgets.BudgetsOverviewViewModel
import com.tink.moneymanagerui.overview.charts.PieChartDetailsViewModel
import com.tink.moneymanagerui.overview.latesttransactions.LatestTransactionsViewModel
import com.tink.moneymanagerui.statistics.details.ChartDetailsViewModel
import com.tink.moneymanagerui.statistics.overtime.StatisticsOverTimeViewModel
import com.tink.moneymanagerui.transaction.CategorizationFlowViewModel
import com.tink.moneymanagerui.transaction.SimilarTransactionsViewModel
import com.tink.moneymanagerui.transaction.TransactionListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import se.tink.android.di.viewmodel.ViewModelKey

/**
 *
 * Provides view models for injection
 *
 * @see ViewModelFactory
 */
@Module
internal interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TransactionListViewModel::class)
    fun bindTransactionListViewModel(model: TransactionListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OverviewChartViewModel::class)
    fun bindOverviewChartViewModel(model: OverviewChartViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PieChartDetailsViewModel::class)
    fun bindDetailsViewModel(model: PieChartDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChartDetailsViewModel::class)
    fun bindChartDetailsPagerViewModel(model: ChartDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategorySelectionViewModel::class)
    fun bindCategorySelectionViewModel(model: CategorySelectionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SimilarTransactionsViewModel::class)
    fun bindSimilarTransactionsViewModel(model: SimilarTransactionsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LatestTransactionsViewModel::class)
    fun bindLatestTransactionsViewModel(model: LatestTransactionsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategorizationFlowViewModel::class)
    fun bindCategorizationFlowViewModel(model: CategorizationFlowViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AccountsViewModel::class)
    fun bindAccountsViewModel(model: AccountsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AccountDetailsViewModel::class)
    fun bindAccountDetailsViewModel(model: AccountDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AccountDetailsEditViewModel::class)
    fun bindAccountDetailsEditViewModel(model: AccountDetailsEditViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StatisticsOverTimeViewModel::class)
    fun bindStatisticsOverTimeViewModel(model: StatisticsOverTimeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BudgetsOverviewViewModel::class)
    fun bindOverviewBudgetsViewModel(model: BudgetsOverviewViewModel): ViewModel
}
