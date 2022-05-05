---
title: viewTransactionsByCategory
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.insights.actionhandling](../index.html)/[InsightActionHandler](index.html)/[viewTransactionsByCategory](view-transactions-by-category.html)



# viewTransactionsByCategory



[androidJvm]\
open fun [viewTransactionsByCategory](view-transactions-by-category.html)(transactionsByCategory: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;&gt;): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)



Handle action where the user wants to view transactions by categories. This corresponds to [InsightAction.Type.VIEW_TRANSACTIONS_BY_CATEGORY](../../com.tink.model.insights/-insight-action/-type/-v-i-e-w_-t-r-a-n-s-a-c-t-i-o-n-s_-b-y_-c-a-t-e-g-o-r-y/index.html) action type.



#### Return



true if the action will be handled, false otherwise



## Parameters


androidJvm

| | |
|---|---|
| transactionsByCategory | A mapping of category code to list of identifiers for all transactions that belong to that category |




