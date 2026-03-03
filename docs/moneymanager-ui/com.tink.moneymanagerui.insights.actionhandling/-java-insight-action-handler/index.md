---
title: JavaInsightActionHandler
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.insights.actionhandling](../index.html)/[JavaInsightActionHandler](index.html)



# JavaInsightActionHandler



[androidJvm]\
open class [JavaInsightActionHandler](index.html)

This class is only meant to be used if you can't use [InsightActionHandler](../-insight-action-handler/index.html). It has the same methods as [InsightActionHandler](../-insight-action-handler/index.html) but uses explicit callbacks instead of lambdas, intended to be used if you're not using Kotlin. If will internally create an [InsightActionHandler](../-insight-action-handler/index.html)



## Constructors


| | |
|---|---|
| [JavaInsightActionHandler](-java-insight-action-handler.html) | [androidJvm]<br>fun [JavaInsightActionHandler](-java-insight-action-handler.html)() |


## Functions


| Name | Summary |
|---|---|
| [categorizeExpense](categorize-expense.html) | [androidJvm]<br>open fun [categorizeExpense](categorize-expense.html)(transactionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), onHandled: [OnInsightHandled](../../com.tink.moneymanagerui.insights.actionhandling.callbacks/-on-insight-handled/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Handle action where the user wants to categorize a transaction matching the id. This corresponds to InsightAction.Type.CATEGORIZE_EXPENSE action type. |
| [categorizeTransactions](categorize-transactions.html) | [androidJvm]<br>open fun [categorizeTransactions](categorize-transactions.html)(transactionIds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, onHandled: [OnInsightHandled](../../com.tink.moneymanagerui.insights.actionhandling.callbacks/-on-insight-handled/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Handle action where the user wants to categorize multiple transactions matching a list of ids. This corresponds to InsightAction.Type.CATEGORIZE_TRANSACTIONS action type. |
| [createBudget](create-budget.html) | [androidJvm]<br>open fun [createBudget](create-budget.html)(amount: Amount?, filter: BudgetFilter?, periodicity: BudgetPeriodicity?, onHandled: [OnInsightHandled](../../com.tink.moneymanagerui.insights.actionhandling.callbacks/-on-insight-handled/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Handle action where the user wants to create a budget. This corresponds to InsightAction.Type.CREATE_BUDGET action type. |
| [initiateTransfer](initiate-transfer.html) | [androidJvm]<br>open fun [initiateTransfer](initiate-transfer.html)(sourceUri: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, sourceAccountNumber: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, destinationUri: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, destinationAccountNumber: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, amount: Amount?, onHandled: [OnInsightHandled](../../com.tink.moneymanagerui.insights.actionhandling.callbacks/-on-insight-handled/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Handle action where the user wants to make a transfer. This corresponds to InsightAction.Type.CREATE_TRANSFER action type. |
| [refreshCredentials](refresh-credentials.html) | [androidJvm]<br>open fun [refreshCredentials](refresh-credentials.html)(credentialId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), onHandled: [OnInsightHandled](../../com.tink.moneymanagerui.insights.actionhandling.callbacks/-on-insight-handled/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Handle action where the user requests to refresh an aggregated credential. This corresponds to InsightAction.Type.REFRESH_CREDENTIAL action type. |
| [viewAccount](view-account.html) | [androidJvm]<br>open fun [viewAccount](view-account.html)(accountId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Handle action where the user wants to view account matching the id. This corresponds to InsightAction.Type.VIEW_ACCOUNT and InsightAction.Type.VIEW_TRANSACTIONS action types. |
| [viewBudget](view-budget.html) | [androidJvm]<br>open fun [viewBudget](view-budget.html)(budgetId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), periodStart: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Handle action where the user wants to view information for a budget matching the id and start period. This corresponds to InsightAction.Type.VIEW_BUDGET action type. |
| [viewTransactions](view-transactions.html) | [androidJvm]<br>open fun [viewTransactions](view-transactions.html)(transactionIds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Handle action where the user wants to view transactions matching the list of ids. This corresponds to InsightAction.Type.VIEW_TRANSACTION and InsightAction.Type.VIEW_TRANSACTIONS action types. |
| [viewTransactionsByCategory](view-transactions-by-category.html) | [androidJvm]<br>open fun [viewTransactionsByCategory](view-transactions-by-category.html)(transactionsByCategory: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;&gt;): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Handle action where the user wants to view transactions by categories. This corresponds to InsightAction.Type.VIEW_TRANSACTIONS_BY_CATEGORY action type. |

