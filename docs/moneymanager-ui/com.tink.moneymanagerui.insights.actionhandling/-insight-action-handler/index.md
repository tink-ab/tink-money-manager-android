---
title: InsightActionHandler
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.insights.actionhandling](../index.html)/[InsightActionHandler](index.html)



# InsightActionHandler



[androidJvm]\
open class [InsightActionHandler](index.html)

This class contains a set of methods that you can implement to perform the necessary logic when a user selects an action for an insight that requires more user interaction.



All the methods have a [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) return type to indicate if the action is handled by this handler or not. If you are overriding a method to perform custom logic for the action, the method should return `true`.



For methods with a `onComplete` lambda block parameter, you have to invoke the lambda block when the task related to the requested action has completed or been cancelled. If the action has completed successfully, the block can be invoked with the boolean value set to `true`. Eg: onComplete.invoke(true) If the action has failed or is cancelled, the block can be invoked with the boolean value set to `false`. Eg: onComplete.invoke(false) If you don’t invoke the `onComplete` block, the insight will remain in the list and will not be archived.



## Constructors


| | |
|---|---|
| [InsightActionHandler](-insight-action-handler.html) | [androidJvm]<br>fun [InsightActionHandler](-insight-action-handler.html)() |


## Functions


| Name | Summary |
|---|---|
| [categorizeExpense](categorize-expense.html) | [androidJvm]<br>open fun [categorizeExpense](categorize-expense.html)(transactionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), onComplete: (isActionDone: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Handle action where the user wants to categorize a transaction matching the id. This corresponds to InsightAction.Type.CATEGORIZE_EXPENSE action type. |
| [categorizeTransactions](categorize-transactions.html) | [androidJvm]<br>open fun [categorizeTransactions](categorize-transactions.html)(transactionIds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, onComplete: (isActionDone: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Handle action where the user wants to categorize multiple transactions matching a list of ids. This corresponds to InsightAction.Type.CATEGORIZE_TRANSACTIONS action type. |
| [createBudget](create-budget.html) | [androidJvm]<br>open fun [createBudget](create-budget.html)(amount: Amount?, filter: BudgetFilter?, periodicity: BudgetPeriodicity?, onComplete: (isActionDone: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Handle action where the user wants to create a budget. This corresponds to InsightAction.Type.CREATE_BUDGET action type. |
| [initiateTransfer](initiate-transfer.html) | [androidJvm]<br>open fun [initiateTransfer](initiate-transfer.html)(sourceUri: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, sourceAccountNumber: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, destinationUri: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, destinationAccountNumber: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, amount: Amount?, onComplete: (isActionDone: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Handle action where the user wants to make a transfer. This corresponds to InsightAction.Type.CREATE_TRANSFER action type. |
| [refreshCredentials](refresh-credentials.html) | [androidJvm]<br>open fun [refreshCredentials](refresh-credentials.html)(credentialId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), onComplete: (isActionDone: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Handle action where the user requests to refresh an aggregated credential. This corresponds to InsightAction.Type.REFRESH_CREDENTIAL action type. |
| [viewAccount](view-account.html) | [androidJvm]<br>open fun [viewAccount](view-account.html)(accountId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Handle action where the user wants to view account matching the id. This corresponds to InsightAction.Type.VIEW_ACCOUNT and InsightAction.Type.VIEW_TRANSACTIONS action types. |
| [viewBudget](view-budget.html) | [androidJvm]<br>open fun [viewBudget](view-budget.html)(budgetId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), periodStart: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Handle action where the user wants to view information for a budget matching the id and start period. This corresponds to InsightAction.Type.VIEW_BUDGET action type. |
| [viewLeftToSpend](view-left-to-spend.html) | [androidJvm]<br>open fun [viewLeftToSpend](view-left-to-spend.html)(month: YearMonth): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Handle action where the user wants to view the Monthly LEFT_TO_SPEND for the corresponding month. This corresponds to InsightAction.Type.VIEW_LEFT_TO_SPEND action type. |
| [viewTransactions](view-transactions.html) | [androidJvm]<br>open fun [viewTransactions](view-transactions.html)(transactionIds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Handle action where the user wants to view transactions matching the list of ids. This corresponds to InsightAction.Type.VIEW_TRANSACTION and InsightAction.Type.VIEW_TRANSACTIONS action types. |
| [viewTransactionsByCategory](view-transactions-by-category.html) | [androidJvm]<br>open fun [viewTransactionsByCategory](view-transactions-by-category.html)(transactionsByCategory: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;&gt;): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Handle action where the user wants to view transactions by categories. This corresponds to InsightAction.Type.VIEW_TRANSACTIONS_BY_CATEGORY action type. |

