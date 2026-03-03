---
title: handleThirdPartyCallbackResult
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui](../index.html)/[BaseFragment](index.html)/[handleThirdPartyCallbackResult](handle-third-party-callback-result.html)



# handleThirdPartyCallbackResult



[androidJvm]\
open fun [handleThirdPartyCallbackResult](handle-third-party-callback-result.html)(state: [String](https://developer.android.com/reference/kotlin/java/lang/String.html), parameters: [Map](https://developer.android.com/reference/kotlin/java/util/Map.html)&lt;[String](https://developer.android.com/reference/kotlin/java/lang/String.html), [String](https://developer.android.com/reference/kotlin/java/lang/String.html)&gt;): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)



Called when a third party callback uri is received by the app. Fragments that pass the callback parameters to the service should override this.



#### Return



return true if this is handled by the current fragment, false otherwise



## Parameters


androidJvm

| | |
|---|---|
| state | The state string from the callback url |
| parameters | The parameters map from the callback url |




