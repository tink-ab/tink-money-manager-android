---
title: viewTransactions
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.insights.actionhandling](../index.html)/[InsightActionHandler](index.html)/[viewTransactions](view-transactions.html)



# viewTransactions



[androidJvm]\
open fun [viewTransactions](view-transactions.html)(transactionIds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)



Handle action where the user wants to view transactions matching the list of ids. This corresponds to InsightAction.Type.VIEW_TRANSACTION and InsightAction.Type.VIEW_TRANSACTIONS action types.



#### Return



`true` if the action will be handled, `false` otherwise



## Parameters


androidJvm

| | |
|---|---|
| transactionIds | List of identifiers for all the transactions that should be shown |




