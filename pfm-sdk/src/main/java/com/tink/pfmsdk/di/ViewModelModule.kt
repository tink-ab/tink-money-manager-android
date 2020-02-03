package com.tink.pfmsdk.di

import androidx.lifecycle.ViewModel
import com.tink.pfmsdk.overview.OverviewChartViewModel
import com.tink.pfmsdk.overview.accounts.AccountsViewModel
import com.tink.pfmsdk.overview.charts.CategorySelectionViewModel
import com.tink.pfmsdk.overview.charts.ChartDetailsViewModel
import com.tink.pfmsdk.overview.charts.PieChartDetailsViewModel
import com.tink.pfmsdk.overview.latesttransactions.LatestTransactionsViewModel
import com.tink.pfmsdk.transaction.CategorizationFlowViewModel
import com.tink.pfmsdk.transaction.SimilarTransactionsViewModel
import com.tink.pfmsdk.transaction.TransactionListViewModel
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
interface ViewModelModule {

//    @Binds
//    @IntoMap
//    @ViewModelKey(MainActivityViewModel::class)
//    fun bindMainActivityViewModel(model: MainActivityViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(MainFragmentViewModel::class)
//    fun bindMainFragmentViewModel(model: MainFragmentViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(SplashScreenViewModel::class)
//    fun bindSplashScreenViewModel(model: SplashScreenViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(OnboardingScreenViewModel::class)
//    fun bindOnboardingScreenViewModel(model: OnboardingScreenViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(LoginScreenViewModel::class)
//    fun bindLoginViewModel(model: LoginScreenViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(CreateUserScreenViewModel::class)
//    fun bindCreateUserViewModel(model: CreateUserScreenViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(RegisterWithPasswordScreenViewModel::class)
//    fun bindRegisterWithPasswordViewModel(model: RegisterWithPasswordScreenViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(LoginWithPasswordScreenViewModel::class)
//    fun bindLoginWithPasswordViewModel(model: LoginWithPasswordScreenViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(ForgotPasswordScreenViewModel::class)
//    fun bindForgotPasswordViewModel(model: ForgotPasswordScreenViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(FingerprintAuthScreenViewModel::class)
//    fun bindFingerPrintScreenViewModel(model: FingerprintAuthScreenViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(AddProviderScreenViewModel::class)
//    fun bindAddPrimaryAccountScreenViewModel(model: AddProviderScreenViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(ChooseAccessTypeViewModel::class)
//    fun bindChooseAccessTypeViewModel(model: ChooseAccessTypeViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(OnboardingViewModel::class)
//    fun bindOnboardingViewModel(model: OnboardingViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(NewOrExistingUserViewModel::class)
//    fun bindNewOrExistingUserViewModel(model: NewOrExistingUserViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(OverviewVM::class)
//    fun bindOverviewVM(model: OverviewVM): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(AccountViewModel::class)
//    fun bindAccountViewModel(model: AccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TransactionListViewModel::class)
    fun bindTransactionListViewModel(model: TransactionListViewModel): ViewModel

//    @Binds
//    @IntoMap
//    @ViewModelKey(ProfileViewModel::class)
//    fun bindProfileViewModel(model: ProfileViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(CredentialStatusViewModel::class)
//    fun bindCredentialStatusViewModel(model: CredentialStatusViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(SuggestedProvidersViewModel::class)
//    fun bindSuggestedProvidersViewModel(model: SuggestedProvidersViewModel): ViewModel
//
    @Binds
    @IntoMap
    @ViewModelKey(OverviewChartViewModel::class)
    fun bindOverviewChartViewModel(model: OverviewChartViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PieChartDetailsViewModel::class)
    fun bindDetailsViewModel(model: PieChartDetailsViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(BetaViewModel::class)
//    fun bindBetaViewModel(model: BetaViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(BetaInformationViewModel::class)
//    fun bindBetaInformationViewModel(model: BetaInformationViewModel): ViewModel
//
    @Binds
    @IntoMap
    @ViewModelKey(ChartDetailsViewModel::class)
    fun bindChartDetailsPagerViewModel(model: ChartDetailsViewModel): ViewModel
//
    @Binds
    @IntoMap
    @ViewModelKey(CategorySelectionViewModel::class)
    fun bindCategorySelectionViewModel(model: CategorySelectionViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(OwnPartAmountViewModel::class)
//    fun bindOwnPartAmountViewModel(model: OwnPartAmountViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(OverviewInformationBubbleViewModel::class)
//    fun bindOverviewInformationBubbleViewModel(model: OverviewInformationBubbleViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(OverviewBudgetsViewModel::class)
//    fun bindOverviewBudgetsViewModel(model: OverviewBudgetsViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(KycFlowViewModel::class)
//    fun bindKycViewModel(model: KycFlowViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(SearchViewModel::class)
//    fun bindSearchViewModel(model: SearchViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(OverviewRecurringCostViewModel::class)
//    fun bindOverviewRecurringCostViewModel(model: OverviewRecurringCostViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(RecurringTransactionsGroupViewModel::class)
//    fun bindRecurringTransactionsGroupViewModel(model: RecurringTransactionsGroupViewModel) : ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(TransactionDetailsViewModel::class)
//    fun bindTransactionDetailsViewModel(model: TransactionDetailsViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(ProfileChooseAccessTypeViewModel::class)
//    fun bindProfileChooseAccessTypeViewModel(model: ProfileChooseAccessTypeViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(ProfileUpdateAccessTypeViewModel::class)
//    fun bindProfileUpdateAccessTypeViewModel(model: ProfileUpdateAccessTypeViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(CredentialViewModel::class)
//    fun bindCredentialViewModel(model: CredentialViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(EditCredentialViewModel::class)
//    fun bindEditCredentialViewModel(model: EditCredentialViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(AddCredentialViewModel::class)
//    fun bindAddCredentialViewModel(model: AddCredentialViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(OpenBankingMigrationInfoViewModel::class)
//    fun bindOpenBankingMigrationInfoViewModel(model: OpenBankingMigrationInfoViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(NewIbanRecipientViewModel::class)
//    fun bindNewIbanRecipientViewModel(model: NewIbanRecipientViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(CounterpartSuggestionViewModel::class)
//    fun bindCounterpartSuggestionsViewModel(model: CounterpartSuggestionViewModel): ViewModel

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
}
