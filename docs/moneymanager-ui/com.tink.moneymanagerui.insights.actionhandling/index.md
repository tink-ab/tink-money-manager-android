---
title: com.tink.moneymanagerui.insights.actionhandling
---
//[moneymanager-ui](../../index.html)/[com.tink.moneymanagerui.insights.actionhandling](index.html)



# Package com.tink.moneymanagerui.insights.actionhandling



## Types


| Name | Summary |
|---|---|
| [ActionEventBus](-action-event-bus/index.html) | [androidJvm]<br>class [ActionEventBus](-action-event-bus/index.html)@Injectconstructor |
| [ActionHandler](-action-handler/index.html) | [androidJvm]<br>interface [ActionHandler](-action-handler/index.html) |
| [CategorizeExpenseActionHandler](-categorize-expense-action-handler/index.html) | [androidJvm]<br>class [CategorizeExpenseActionHandler](-categorize-expense-action-handler/index.html)@Injectconstructor(redirectionReceiver: [RedirectionReceiver](../se.tink.android.redirection/-redirection-receiver/index.html)) : [ActionHandler](-action-handler/index.html) |
| [CategorizeTransactionsActionHandler](-categorize-transactions-action-handler/index.html) | [androidJvm]<br>class [CategorizeTransactionsActionHandler](-categorize-transactions-action-handler/index.html)@Injectconstructor(redirectionReceiver: [RedirectionReceiver](../se.tink.android.redirection/-redirection-receiver/index.html)) : [ActionHandler](-action-handler/index.html) |
| [CreateBudgetActionHandler](-create-budget-action-handler/index.html) | [androidJvm]<br>class [CreateBudgetActionHandler](-create-budget-action-handler/index.html)@Injectconstructor(redirectionReceiver: [RedirectionReceiver](../se.tink.android.redirection/-redirection-receiver/index.html)) : [ActionHandler](-action-handler/index.html) |
| [CreateTransferActionHandler](-create-transfer-action-handler/index.html) | [androidJvm]<br>class [CreateTransferActionHandler](-create-transfer-action-handler/index.html)@Injectconstructor(redirectionReceiver: [RedirectionReceiver](../se.tink.android.redirection/-redirection-receiver/index.html)) : [ActionHandler](-action-handler/index.html) |
| [GeneralActionHandler](-general-action-handler/index.html) | [androidJvm]<br>class [GeneralActionHandler](-general-action-handler/index.html)@Injectconstructor(val handlers: [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[ActionHandler](-action-handler/index.html)&gt;, tracker: [InsightsTracker](-insights-tracker/index.html), actionEventBus: [ActionEventBus](-action-event-bus/index.html)) : [ActionHandler](-action-handler/index.html) |
| [InsightActionHandler](-insight-action-handler/index.html) | [androidJvm]<br>open class [InsightActionHandler](-insight-action-handler/index.html)<br>This class contains a set of methods that you can implement to perform the necessary logic when a user selects an action for an insight that requires more user interaction. |
| [InsightsTracker](-insights-tracker/index.html) | [androidJvm]<br>interface [InsightsTracker](-insights-tracker/index.html) |
| [JavaInsightActionHandler](-java-insight-action-handler/index.html) | [androidJvm]<br>open class [JavaInsightActionHandler](-java-insight-action-handler/index.html)<br>This class is only meant to be used if you can't use [InsightActionHandler](-insight-action-handler/index.html). It has the same methods as [InsightActionHandler](-insight-action-handler/index.html) but uses explicit callbacks instead of lambdas, intended to be used if you're not using Kotlin. If will internally create an [InsightActionHandler](-insight-action-handler/index.html) |
| [PassiveActionHandler](-passive-action-handler/index.html) | [androidJvm]<br>class [PassiveActionHandler](-passive-action-handler/index.html)@Injectconstructor : [ActionHandler](-action-handler/index.html) |
| [PerformedActionNotifier](-performed-action-notifier/index.html) | [androidJvm]<br>class [PerformedActionNotifier](-performed-action-notifier/index.html)@Injectconstructor(eventBus: [ActionEventBus](-action-event-bus/index.html), insightService: [InsightService](../com.tink.service.insight/-insight-service/index.html), dispatcher: [DispatcherProvider](../com.tink.service.util/-dispatcher-provider/index.html)) |
| [ViewAccountActionHandler](-view-account-action-handler/index.html) | [androidJvm]<br>class [ViewAccountActionHandler](-view-account-action-handler/index.html)@Injectconstructor(redirectionReceiver: [RedirectionReceiver](../se.tink.android.redirection/-redirection-receiver/index.html)) : [ActionHandler](-action-handler/index.html) |
| [ViewBudgetActionHandler](-view-budget-action-handler/index.html) | [androidJvm]<br>class [ViewBudgetActionHandler](-view-budget-action-handler/index.html)@Injectconstructor(redirectionReceiver: [RedirectionReceiver](../se.tink.android.redirection/-redirection-receiver/index.html)) : [ActionHandler](-action-handler/index.html) |
| [ViewTransactionsActionHandler](-view-transactions-action-handler/index.html) | [androidJvm]<br>class [ViewTransactionsActionHandler](-view-transactions-action-handler/index.html)@Injectconstructor(redirectionReceiver: [RedirectionReceiver](../se.tink.android.redirection/-redirection-receiver/index.html)) : [ActionHandler](-action-handler/index.html) |
| [ViewTransactionsByCategoryActionHandler](-view-transactions-by-category-action-handler/index.html) | [androidJvm]<br>class [ViewTransactionsByCategoryActionHandler](-view-transactions-by-category-action-handler/index.html)@Injectconstructor(redirectionReceiver: [RedirectionReceiver](../se.tink.android.redirection/-redirection-receiver/index.html)) : [ActionHandler](-action-handler/index.html) |

