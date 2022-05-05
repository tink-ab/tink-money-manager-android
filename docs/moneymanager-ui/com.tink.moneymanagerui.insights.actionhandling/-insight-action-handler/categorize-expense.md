---
title: categorizeExpense
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.insights.actionhandling](../index.html)/[InsightActionHandler](index.html)/[categorizeExpense](categorize-expense.html)



# categorizeExpense



[androidJvm]\
open fun [categorizeExpense](categorize-expense.html)(transactionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), onComplete: (isActionDone: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)



Handle action where the user wants to categorize a transaction matching the id. This corresponds to [InsightAction.Type.CATEGORIZE_EXPENSE](../../com.tink.model.insights/-insight-action/-type/-c-a-t-e-g-o-r-i-z-e_-e-x-p-e-n-s-e/index.html) action type.



#### Return



true if the action will be handled, false otherwise



## Parameters


androidJvm

| | |
|---|---|
| transactionId | Identifier for the transaction that should be categorized |
| onComplete | The lambda block to invoke to indicate if the action has completed successfully or not |




