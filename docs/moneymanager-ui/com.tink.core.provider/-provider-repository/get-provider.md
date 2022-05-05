---
title: getProvider
---
//[moneymanager-ui](../../../index.html)/[com.tink.core.provider](../index.html)/[ProviderRepository](index.html)/[getProvider](get-provider.html)



# getProvider



[androidJvm]\
fun [getProvider](get-provider.html)(providerName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), resultHandler: [ResultHandler](../../com.tink.service.handler/-result-handler/index.html)&lt;[Provider](../../com.tink.model.provider/-provider/index.html)?&gt;)



Get the provider with the specified [name](../../com.tink.model.provider/-provider/name.html). null will be passed to the [resultHandler](get-provider.html) in case no provider with this name could be found.




