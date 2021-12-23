package com.tink.moneymanagerui.insights.di

import androidx.lifecycle.ViewModel
import com.tink.moneymanagerui.ViewModelFactory
import com.tink.moneymanagerui.insights.ArchivedInsightsViewModel
import com.tink.moneymanagerui.insights.CurrentInsightsViewModel
import com.tink.moneymanagerui.insights.actionhandling.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.IntoSet
import se.tink.android.di.viewmodel.ModelProviders
import se.tink.android.di.viewmodel.ViewModelKey

@Module(
    includes = [
        InsightsViewProviderBindingModule::class,
        InsightEnrichmentModule::class,
        InsightsViewModelModule::class
    ]
)
class InsightsModule {

    @Provides
    @IntoSet
    fun createTransferActionHandler(createTransferActionHandler: CreateTransferActionHandler): ActionHandler =
        createTransferActionHandler

    @Provides
    @IntoSet
    fun viewBudgetActionHandler(viewBudgetActionHandler: ViewBudgetActionHandler): ActionHandler =
        viewBudgetActionHandler

    @Provides
    @IntoSet
    fun createBudgetActionHandler(createBudgetActionHandler: CreateBudgetActionHandler): ActionHandler =
        createBudgetActionHandler

    @Provides
    @IntoSet
    fun categorizeExpenseActionHandler(categorizeExpenseActionHandler: CategorizeExpenseActionHandler): ActionHandler =
        categorizeExpenseActionHandler

    @Provides
    @IntoSet
    fun categorizeTransactionsActionHandler(categorizeTransactionsActionHandler: CategorizeTransactionsActionHandler): ActionHandler =
        categorizeTransactionsActionHandler

    @Provides
    @IntoSet
    fun refreshCredentialActionHandler(refreshCredentialActionHandler: RefreshCredentialActionHandler): ActionHandler =
        refreshCredentialActionHandler

    @Provides
    @IntoSet
    fun viewTransactionsActionHandler(viewTransactionsActionHandler: ViewTransactionsActionHandler): ActionHandler =
        viewTransactionsActionHandler

    @Provides
    @IntoSet
    fun viewTransactionsByCategoryHandler(
        viewTransactionsByCategoryActionHandler: ViewTransactionsByCategoryActionHandler
    ): ActionHandler = viewTransactionsByCategoryActionHandler

    @Provides
    @IntoSet
    fun passiveActionHandler(passiveActionHandler: PassiveActionHandler): ActionHandler =
        passiveActionHandler

    @Provides
    fun handlerComposite(handlers: Set<@JvmSuppressWildcards ActionHandler>, insightsTracker: InsightsTracker, actionEventBus: ActionEventBus): ActionHandler =
        GeneralActionHandler(handlers = handlers, tracker = insightsTracker, actionEventBus = actionEventBus)

    @Provides
    fun viewModelFactory(providers: ModelProviders): InsightsViewModelFactory =
        InsightsViewModelFactory(providers)

    @Provides
    fun insightsTracker(): InsightsTracker {
        return object : InsightsTracker {
            override fun trackButtonPressEvent() {
                // TODO: Remove the tracker logic where it's used before removing this provider logic
                //analytics.track(AnalyticsEvent.EVENTS_BUTTON_PRESSED_EVENT)
            }
        }
    }
}

@Module
interface InsightsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CurrentInsightsViewModel::class)
    fun bindInsightsViewModel(model: CurrentInsightsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ArchivedInsightsViewModel::class)
    fun bindArchivedInsightsViewModel(model: ArchivedInsightsViewModel): ViewModel
}

class InsightsViewModelFactory(providers: ModelProviders) : ViewModelFactory(providers)

