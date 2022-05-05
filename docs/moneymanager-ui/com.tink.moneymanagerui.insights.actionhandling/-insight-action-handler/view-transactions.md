---
title: viewTransactions
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.insights.actionhandling](../index.html)/[InsightActionHandler](index.html)/[viewTransactions](view-transactions.html)



# viewTransactions



[androidJvm]\
open fun [viewTransactions](view-transactions.html)(transactionIds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)



Handle action where the user wants to view transactions matching the list of ids. This corresponds to [InsightAction.Type.VIEW_TRANSACTION](../../com.tink.model.insights/-insight-action/-type/-v-i-e-w_-t-r-a-n-s-a-c-t-i-o-n/index.html) and [InsightAction.Type.VIEW_TRANSACTIONS](../../com.tink.model.insights/-insight-action/-type/-v-i-e-w_-t-r-a-n-s-a-c-t-i-o-n-s/index.html) action types.



#### Return



true if the action will be handled, false otherwise



## Parameters


androidJvm

| | |
|---|---|
| transactionIds | List of identifiers for all the transactions that should be shown |




