---
title: PathDispatcher
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.testutil](../index.html)/[PathDispatcher](index.html)



# PathDispatcher



[androidJvm]\
class [PathDispatcher](index.html)(pathToResponseMap: [MutableMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), &lt;ERROR CLASS&gt;&gt; = mutableMapOf(), pathToBodyFilterMap: [MutableMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; = mutableMapOf())



## Constructors


| | |
|---|---|
| [PathDispatcher](-path-dispatcher.html) | [androidJvm]<br>fun [PathDispatcher](-path-dispatcher.html)(pathToResponseMap: [MutableMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), &lt;ERROR CLASS&gt;&gt; = mutableMapOf(), pathToBodyFilterMap: [MutableMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; = mutableMapOf()) |


## Functions


| Name | Summary |
|---|---|
| [addFilter](add-filter.html) | [androidJvm]<br>fun [addFilter](add-filter.html)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), filter: ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [addFilters](add-filters.html) | [androidJvm]<br>fun [addFilters](add-filters.html)(filters: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt;) |
| [addResponse](add-response.html) | [androidJvm]<br>fun [addResponse](add-response.html)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), mockResponse: &lt;ERROR CLASS&gt;) |
| [addResponses](add-responses.html) | [androidJvm]<br>fun [addResponses](add-responses.html)(responses: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), &lt;ERROR CLASS&gt;&gt;) |
| [clearResponses](clear-responses.html) | [androidJvm]<br>fun [clearResponses](clear-responses.html)() |
| [dispatch](dispatch.html) | [androidJvm]<br>open fun [dispatch](dispatch.html)(request: &lt;ERROR CLASS&gt;): &lt;ERROR CLASS&gt; |

