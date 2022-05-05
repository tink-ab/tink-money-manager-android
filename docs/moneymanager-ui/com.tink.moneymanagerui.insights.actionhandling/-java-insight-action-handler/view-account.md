---
title: viewAccount
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.insights.actionhandling](../index.html)/[JavaInsightActionHandler](index.html)/[viewAccount](view-account.html)



# viewAccount



[androidJvm]\
open fun [viewAccount](view-account.html)(accountId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)



Handle action where the user wants to view account matching the id. This corresponds to [InsightAction.Type.VIEW_ACCOUNT](../../com.tink.model.insights/-insight-action/-type/-v-i-e-w_-a-c-c-o-u-n-t/index.html) and [InsightAction.Type.VIEW_TRANSACTIONS](../../com.tink.model.insights/-insight-action/-type/-v-i-e-w_-t-r-a-n-s-a-c-t-i-o-n-s/index.html) action types.



#### Return



true if the action will be handled, false otherwise



## Parameters


androidJvm

| | |
|---|---|
| accountId | Identifiers for the account that should be shown |




