package com.tink.pfmsdk.insights.di

import androidx.lifecycle.ViewModel
import com.tink.pfmsdk.ViewModelFactory
import com.tink.pfmsdk.insights.ArchivedInsightsViewModel
import com.tink.pfmsdk.insights.CurrentInsightsViewModel
import com.tink.pfmsdk.insights.actionhandling.ActionHandler
import com.tink.pfmsdk.insights.actionhandling.CategorizeExpenseActionHandler
import com.tink.pfmsdk.insights.actionhandling.CreateTransferActionHandler
import com.tink.pfmsdk.insights.actionhandling.GeneralActionHandler
import com.tink.pfmsdk.insights.actionhandling.InsightsTracker
import com.tink.pfmsdk.insights.actionhandling.PassiveActionHandler
import com.tink.pfmsdk.insights.actionhandling.ViewBudgetActionHandler
import com.tink.pfmsdk.insights.actionhandling.ViewTransactionsActionHandler
import com.tink.pfmsdk.insights.actionhandling.ViewTransactionsByCategoryActionHandler
import com.tink.pfmsdk.redirection.DefaultRedirectionReceiver
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.IntoSet
import se.tink.android.di.viewmodel.ModelProviders
import se.tink.android.di.viewmodel.ViewModelKey
import se.tink.android.redirection.RedirectionReceiver

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
    fun categorizeExpenseActionHandler(categorizeExpenseActionHandler: CategorizeExpenseActionHandler): ActionHandler =
        categorizeExpenseActionHandler

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
    fun handlerComposite(handlers: Set<@JvmSuppressWildcards ActionHandler>, insightsTracker: InsightsTracker): ActionHandler =
        GeneralActionHandler(handlers = handlers, tracker = insightsTracker)

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

    @Provides
    fun redirectionReceiver(): RedirectionReceiver = DefaultRedirectionReceiver()
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

