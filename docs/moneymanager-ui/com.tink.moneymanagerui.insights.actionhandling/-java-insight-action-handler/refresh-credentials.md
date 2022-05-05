---
title: refreshCredentials
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.insights.actionhandling](../index.html)/[JavaInsightActionHandler](index.html)/[refreshCredentials](refresh-credentials.html)



# refreshCredentials



[androidJvm]\
open fun [refreshCredentials](refresh-credentials.html)(credentialId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), onHandled: [OnInsightHandled](../../com.tink.moneymanagerui.insights.actionhandling.callbacks/-on-insight-handled/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)



Handle action where the user requests to refresh an aggregated credential. This corresponds to [InsightAction.Type.REFRESH_CREDENTIAL](../../com.tink.model.insights/-insight-action/-type/-r-e-f-r-e-s-h_-c-r-e-d-e-n-t-i-a-l/index.html) action type.



#### Return



true if the action will be handled, false otherwise



## Parameters


androidJvm

| | |
|---|---|
| credentialId | The id of the aggregated credential |
| onHandled | The [OnInsightHandled](../../com.tink.moneymanagerui.insights.actionhandling.callbacks/-on-insight-handled/index.html) to invoke to indicate if the action has completed successfully or not |




