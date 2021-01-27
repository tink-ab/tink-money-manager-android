package com.tink.pfmui.budgets.creation.di

import androidx.lifecycle.ViewModel
import com.tink.pfmui.FragmentCoordinator
import com.tink.pfmui.R
import com.tink.pfmui.TransitionCoordinatorImpl
import com.tink.pfmui.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import com.tink.pfmui.budgets.creation.BudgetCreationDataHolder
import com.tink.pfmui.budgets.creation.BudgetCreationFragment
import com.tink.pfmui.budgets.creation.BudgetCreationNavigation
import com.tink.pfmui.budgets.creation.filterselection.BudgetCreationFilterSelectionFragment
import com.tink.pfmui.budgets.creation.filterselection.BudgetCreationFilterSelectionViewModel
import com.tink.pfmui.budgets.creation.search.BudgetCreationSearchFragment
import com.tink.pfmui.budgets.creation.search.BudgetCreationSearchViewModel
import com.tink.pfmui.budgets.creation.specification.BudgetCreationSpecificationFragment
import com.tink.pfmui.budgets.creation.specification.BudgetCreationSpecificationViewModel
import se.tink.android.di.viewmodel.ModelProviders
import se.tink.android.di.viewmodel.ViewModelKey
import javax.inject.Named
import javax.inject.Scope

@Scope
annotation class BudgetCreation

private const val BUDGET_CREATION = "budgetCreation"

@Module(includes = [BudgetCreationFragmentsModule::class, BudgetCreationViewModelModule::class])
internal class BudgetCreationModule {

    @Provides
    @BudgetCreation
    @Named(BUDGET_CREATION)
    fun provideLocalFragmentCoordinator(
        fragment: BudgetCreationFragment,
        transitionCoordinator: TransitionCoordinatorImpl
    ): FragmentCoordinator {
        return FragmentCoordinator(
            fragment.childFragmentManager,
            R.id.container,
            transitionCoordinator
        )
    }

    @Provides
    @BudgetCreation
    fun provideNavigation(
        @Named(BUDGET_CREATION) coordinator: FragmentCoordinator
    ): BudgetCreationNavigation {
        return BudgetCreationNavigation(coordinator)
    }

    @Provides
    @BudgetCreation
    fun provideDataHolder(): BudgetCreationDataHolder = BudgetCreationDataHolder()

    @Provides
    @BudgetCreation
    fun providesViewModelFactory(
        providers: ModelProviders
    ): BudgetCreationViewModelFactory {
        return BudgetCreationViewModelFactory(providers)
    }
}

@Module
internal interface BudgetCreationFragmentsModule {

    @ContributesAndroidInjector
    fun filterSelectionFragment(): BudgetCreationFilterSelectionFragment

    @ContributesAndroidInjector
    fun searchFragment(): BudgetCreationSearchFragment

    @ContributesAndroidInjector
    fun specificationFragment(): BudgetCreationSpecificationFragment

}

@Module
internal interface BudgetCreationViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(BudgetCreationFilterSelectionViewModel::class)
    fun bindFilterSelectionViewModel(model: BudgetCreationFilterSelectionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BudgetCreationSearchViewModel::class)
    fun bindSearchViewModel(model: BudgetCreationSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BudgetCreationSpecificationViewModel::class)
    fun bindSpecificationViewModel(model: BudgetCreationSpecificationViewModel): ViewModel
}

@BudgetCreation
internal class BudgetCreationViewModelFactory(providers: ModelProviders) : ViewModelFactory(providers)