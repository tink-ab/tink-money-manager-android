package com.tink.pfmui.insights.di

import androidx.lifecycle.ViewModel
import com.tink.pfmui.ViewModelFactory
import com.tink.pfmui.insights.ArchivedInsightsViewModel
import com.tink.pfmui.insights.CurrentInsightsViewModel
import com.tink.pfmui.insights.actionhandling.*
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

