---
title: refreshCredentials
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.insights.actionhandling](../index.html)/[InsightActionHandler](index.html)/[refreshCredentials](refresh-credentials.html)



# refreshCredentials



[androidJvm]\
open fun [refreshCredentials](refresh-credentials.html)(credentialId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), onComplete: (isActionDone: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)



Handle action where the user requests to refresh an aggregated credential. This corresponds to InsightAction.Type.REFRESH_CREDENTIAL action type.



#### Return



`true` if the action will be handled, `false` otherwise



## Parameters


androidJvm

| | |
|---|---|
| credentialId | The id of the aggregated credential |
| onComplete | The lambda block to invoke to indicate if the action has completed successfully or not |




