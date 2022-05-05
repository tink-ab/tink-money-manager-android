---
title: categorizeTransactions
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.insights.actionhandling](../index.html)/[InsightActionHandler](index.html)/[categorizeTransactions](categorize-transactions.html)



# categorizeTransactions



[androidJvm]\
open fun [categorizeTransactions](categorize-transactions.html)(transactionIds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, onComplete: (isActionDone: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)



Handle action where the user wants to categorize multiple transactions matching a list of ids. This corresponds to [InsightAction.Type.CATEGORIZE_TRANSACTIONS](../../com.tink.model.insights/-insight-action/-type/-c-a-t-e-g-o-r-i-z-e_-t-r-a-n-s-a-c-t-i-o-n-s/index.html) action type.



#### Return



true if the action will be handled, false otherwise



## Parameters


androidJvm

| | |
|---|---|
| transactionIds | List of identifiers for all the transactions that should be categorized |
| onComplete | The lambda block to invoke to indicate if the action has completed successfully or not |




