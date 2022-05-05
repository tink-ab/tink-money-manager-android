---
title: InsightsModule
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.insights.di](../index.html)/[InsightsModule](index.html)



# InsightsModule



[androidJvm]\
@Module(includes = [[InsightsViewProviderBindingModule::class](../-insights-view-provider-binding-module/index.html), [InsightEnrichmentModule::class](../-insight-enrichment-module/index.html), [InsightsViewModelModule::class](../-insights-view-model-module/index.html)])



class [InsightsModule](index.html)



## Constructors


| | |
|---|---|
| [InsightsModule](-insights-module.html) | [androidJvm]<br>fun [InsightsModule](-insights-module.html)() |


## Functions


| Name | Summary |
|---|---|
| [categorizeExpenseActionHandler](categorize-expense-action-handler.html) | [androidJvm]<br>@Provides<br>@IntoSet<br>fun [categorizeExpenseActionHandler](categorize-expense-action-handler.html)(categorizeExpenseActionHandler: [CategorizeExpenseActionHandler](../../com.tink.moneymanagerui.insights.actionhandling/-categorize-expense-action-handler/index.html)): [ActionHandler](../../com.tink.moneymanagerui.insights.actionhandling/-action-handler/index.html) |
| [categorizeTransactionsActionHandler](categorize-transactions-action-handler.html) | [androidJvm]<br>@Provides<br>@IntoSet<br>fun [categorizeTransactionsActionHandler](categorize-transactions-action-handler.html)(categorizeTransactionsActionHandler: [CategorizeTransactionsActionHandler](../../com.tink.moneymanagerui.insights.actionhandling/-categorize-transactions-action-handler/index.html)): [ActionHandler](../../com.tink.moneymanagerui.insights.actionhandling/-action-handler/index.html) |
| [createBudgetActionHandler](create-budget-action-handler.html) | [androidJvm]<br>@Provides<br>@IntoSet<br>fun [createBudgetActionHandler](create-budget-action-handler.html)(createBudgetActionHandler: [CreateBudgetActionHandler](../../com.tink.moneymanagerui.insights.actionhandling/-create-budget-action-handler/index.html)): [ActionHandler](../../com.tink.moneymanagerui.insights.actionhandling/-action-handler/index.html) |
| [createTransferActionHandler](create-transfer-action-handler.html) | [androidJvm]<br>@Provides<br>@IntoSet<br>fun [createTransferActionHandler](create-transfer-action-handler.html)(createTransferActionHandler: [CreateTransferActionHandler](../../com.tink.moneymanagerui.insights.actionhandling/-create-transfer-action-handler/index.html)): [ActionHandler](../../com.tink.moneymanagerui.insights.actionhandling/-action-handler/index.html) |
| [handlerComposite](handler-composite.html) | [androidJvm]<br>@Provides<br>fun [handlerComposite](handler-composite.html)(handlers: [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;@[JvmSuppressWildcards](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-suppress-wildcards/index.html)[ActionHandler](../../com.tink.moneymanagerui.insights.actionhandling/-action-handler/index.html)&gt;, insightsTracker: [InsightsTracker](../../com.tink.moneymanagerui.insights.actionhandling/-insights-tracker/index.html), actionEventBus: [ActionEventBus](../../com.tink.moneymanagerui.insights.actionhandling/-action-event-bus/index.html)): [ActionHandler](../../com.tink.moneymanagerui.insights.actionhandling/-action-handler/index.html) |
| [insightsTracker](insights-tracker.html) | [androidJvm]<br>@Provides<br>fun [insightsTracker](insights-tracker.html)(): [InsightsTracker](../../com.tink.moneymanagerui.insights.actionhandling/-insights-tracker/index.html) |
| [passiveActionHandler](passive-action-handler.html) | [androidJvm]<br>@Provides<br>@IntoSet<br>fun [passiveActionHandler](passive-action-handler.html)(passiveActionHandler: [PassiveActionHandler](../../com.tink.moneymanagerui.insights.actionhandling/-passive-action-handler/index.html)): [ActionHandler](../../com.tink.moneymanagerui.insights.actionhandling/-action-handler/index.html) |
| [viewAccountActionHandler](view-account-action-handler.html) | [androidJvm]<br>@Provides<br>@IntoSet<br>fun [viewAccountActionHandler](view-account-action-handler.html)(viewAccountActionHandler: [ViewAccountActionHandler](../../com.tink.moneymanagerui.insights.actionhandling/-view-account-action-handler/index.html)): [ActionHandler](../../com.tink.moneymanagerui.insights.actionhandling/-action-handler/index.html) |
| [viewBudgetActionHandler](view-budget-action-handler.html) | [androidJvm]<br>@Provides<br>@IntoSet<br>fun [viewBudgetActionHandler](view-budget-action-handler.html)(viewBudgetActionHandler: [ViewBudgetActionHandler](../../com.tink.moneymanagerui.insights.actionhandling/-view-budget-action-handler/index.html)): [ActionHandler](../../com.tink.moneymanagerui.insights.actionhandling/-action-handler/index.html) |
| [viewModelFactory](view-model-factory.html) | [androidJvm]<br>@Provides<br>fun [viewModelFactory](view-model-factory.html)(providers: [ModelProviders](../../se.tink.android.di.viewmodel/index.html#664847139%2FClasslikes%2F1000845458)): [InsightsViewModelFactory](../-insights-view-model-factory/index.html) |
| [viewTransactionsActionHandler](view-transactions-action-handler.html) | [androidJvm]<br>@Provides<br>@IntoSet<br>fun [viewTransactionsActionHandler](view-transactions-action-handler.html)(viewTransactionsActionHandler: [ViewTransactionsActionHandler](../../com.tink.moneymanagerui.insights.actionhandling/-view-transactions-action-handler/index.html)): [ActionHandler](../../com.tink.moneymanagerui.insights.actionhandling/-action-handler/index.html) |
| [viewTransactionsByCategoryHandler](view-transactions-by-category-handler.html) | [androidJvm]<br>@Provides<br>@IntoSet<br>fun [viewTransactionsByCategoryHandler](view-transactions-by-category-handler.html)(viewTransactionsByCategoryActionHandler: [ViewTransactionsByCategoryActionHandler](../../com.tink.moneymanagerui.insights.actionhandling/-view-transactions-by-category-action-handler/index.html)): [ActionHandler](../../com.tink.moneymanagerui.insights.actionhandling/-action-handler/index.html) |

