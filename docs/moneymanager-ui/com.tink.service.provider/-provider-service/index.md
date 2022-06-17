---
title: ProviderService
---
//[moneymanager-ui](../../../index.html)/[com.tink.service.provider](../index.html)/[ProviderService](index.html)



# ProviderService



[androidJvm]\
interface [ProviderService](index.html)



## Functions


| Name | Summary |
|---|---|
| [getProvider](get-provider.html) | [androidJvm]<br>abstract suspend fun [getProvider](get-provider.html)(providerName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Provider](../../com.tink.model.provider/-provider/index.html)? |
| [listProviders](list-providers.html) | [androidJvm]<br>abstract suspend fun [listProviders](list-providers.html)(filter: [ProviderFilter](../-provider-filter/index.html)?): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Provider](../../com.tink.model.provider/-provider/index.html)&gt; |
| [listSuggestions](list-suggestions.html) | [androidJvm]<br>abstract suspend fun [listSuggestions](list-suggestions.html)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Provider](../../com.tink.model.provider/-provider/index.html)&gt; |
