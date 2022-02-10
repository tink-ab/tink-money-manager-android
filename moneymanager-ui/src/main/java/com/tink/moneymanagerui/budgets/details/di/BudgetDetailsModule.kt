package com.tink.moneymanagerui.budgets.details.di

import androidx.lifecycle.ViewModel
import com.tink.moneymanagerui.BuildConfig
import com.tink.moneymanagerui.FragmentCoordinator
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.ViewModelFactory
import com.tink.moneymanagerui.budgets.details.BudgetDetailsChartFragment
import com.tink.moneymanagerui.budgets.details.BudgetDetailsDataHolder
import com.tink.moneymanagerui.budgets.details.BudgetDetailsFragment
import com.tink.moneymanagerui.budgets.details.BudgetDetailsNavigation
import com.tink.moneymanagerui.budgets.details.BudgetDetailsViewModel
import com.tink.moneymanagerui.budgets.details.BudgetSelectionController
import com.tink.moneymanagerui.budgets.details.BudgetSelectionControllerNew
import com.tink.moneymanagerui.budgets.details.transactions.BudgetTransactionListViewModel
import com.tink.moneymanagerui.budgets.details.transactions.BudgetTransactionsListFragment
import com.tink.moneymanagerui.repository.StatisticsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import org.joda.time.DateTime
import se.tink.android.di.viewmodel.ModelProviders
import se.tink.android.di.viewmodel.ViewModelKey
import se.tink.android.repository.ExceptionTracker
import se.tink.android.repository.budget.BudgetsRepository
import se.tink.android.repository.transaction.TransactionUpdateEventBus
import se.tink.utils.DateUtils
import javax.inject.Named
import javax.inject.Scope

@Scope
annotation class BudgetDetails

const val BUDGET_DETAIL = "budgetDetail"

@Module(includes = [BudgetDetailsFragmentsModule::class, BudgetDetailsViewModelModule::class])
internal class BudgetDetailsModule {

    @Provides
    @BudgetDetails
    @Named(BUDGET_DETAIL)
    fun provideLocalFragmentCoordinator(fragment: BudgetDetailsFragment): FragmentCoordinator {
        return FragmentCoordinator(fragment.childFragmentManager, R.id.container)
    }

    @Provides
    @BudgetDetails
    fun provideNavigation(
        @Named(BUDGET_DETAIL) coordinator: FragmentCoordinator,
        fragment: BudgetDetailsFragment
    ): BudgetDetailsNavigation {
        return BudgetDetailsNavigation(coordinator, fragment)
    }

    @Provides
    @BudgetDetails
    fun providesBudgetDetailsDataHolder(
        budgetSelectionController: BudgetSelectionController,
        dateUtils: DateUtils
    ): BudgetDetailsDataHolder {
        return BudgetDetailsDataHolder(budgetSelectionController, dateUtils)
    }

    @Provides
    @BudgetDetails
    fun providesBudgetSelectionController(
        fragment: BudgetDetailsFragment,
        budgetsRepository: BudgetsRepository,
        statisticsRepository: StatisticsRepository,
        exceptionTracker: ExceptionTracker,
        transactionUpdateEventBus: TransactionUpdateEventBus
    ): BudgetSelectionController {

        val periodStart = try {
            fragment.arguments
                ?.getString(BudgetDetailsFragment.PERIOD_START)
                ?.let(DateTime::parse)
                ?.plusDays(1)
        } catch (e: IllegalArgumentException) {
            if (!BuildConfig.DEBUG) {
                exceptionTracker.exceptionThrown(e)
            } else {
                throw e
            }
            null
        }

        return BudgetSelectionController(
            requireNotNull(fragment.arguments?.getString(BudgetDetailsFragment.BUDGET_ID)),
            budgetsRepository,
            statisticsRepository,
            fragment.lifecycle,
            periodStart,
            transactionUpdateEventBus
        )
    }

    @Provides
    @BudgetDetails
    fun providesBudgetSelectionControllerNew(
        fragment: BudgetDetailsFragment,
        budgetsRepository: BudgetsRepository,
        statisticsRepository: StatisticsRepository,
        exceptionTracker: ExceptionTracker,
        transactionUpdateEventBus: TransactionUpdateEventBus
    ): BudgetSelectionControllerNew {

        val periodStart = try {
            fragment.arguments
                ?.getString(BudgetDetailsFragment.PERIOD_START)
                ?.let(DateTime::parse)
                ?.plusDays(1)
        } catch (e: IllegalArgumentException) {
            if (!BuildConfig.DEBUG) {
                exceptionTracker.exceptionThrown(e)
            } else {
                throw e
            }
            null
        }

        return BudgetSelectionControllerNew(
            requireNotNull(fragment.arguments?.getString(BudgetDetailsFragment.BUDGET_ID)),
            budgetsRepository,
            statisticsRepository,
            fragment.lifecycle,
            periodStart,
            transactionUpdateEventBus
        )
    }

    @Provides
    @BudgetDetails
    fun providesViewModelFactory(
        providers: ModelProviders
    ): BudgetDetailsViewModelFactory {
        return BudgetDetailsViewModelFactory(providers)
    }
}

@Module
internal interface BudgetDetailsFragmentsModule {

    @ContributesAndroidInjector
    fun budgetDetailsChartFragment(): BudgetDetailsChartFragment

    @ContributesAndroidInjector
    fun budgetTransactionsListFragment(): BudgetTransactionsListFragment

}

@Module
internal interface BudgetDetailsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(BudgetDetailsViewModel::class)
    fun bindBudgetDetailsViewModel(model: BudgetDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BudgetTransactionListViewModel::class)
    fun bindBudgetTransactionListViewModel(model: BudgetTransactionListViewModel): ViewModel
}

@BudgetDetails
internal class BudgetDetailsViewModelFactory(providers: ModelProviders) : ViewModelFactory(providers)
