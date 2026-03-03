---
title: categorizeExpense
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.insights.actionhandling](../index.html)/[JavaInsightActionHandler](index.html)/[categorizeExpense](categorize-expense.html)



# categorizeExpense



[androidJvm]\
open fun [categorizeExpense](categorize-expense.html)(transactionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), onHandled: [OnInsightHandled](../../com.tink.moneymanagerui.insights.actionhandling.callbacks/-on-insight-handled/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)



Handle action where the user wants to categorize a transaction matching the id. This corresponds to InsightAction.Type.CATEGORIZE_EXPENSE action type.



#### Return



`true` if the action will be handled, `false` otherwise



## Parameters


androidJvm

| | |
|---|---|
| transactionId | Identifier for the transaction that should be categorized |
| onHandled | The [OnInsightHandled](../../com.tink.moneymanagerui.insights.actionhandling.callbacks/-on-insight-handled/index.html) to invoke to indicate if the action has completed successfully or not |




