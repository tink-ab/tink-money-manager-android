---
title: PollingHandler
---
//[moneymanager-ui](../../../index.html)/[com.tink.service.streaming](../index.html)/[PollingHandler](index.html)



# PollingHandler



[androidJvm]\
class [PollingHandler](index.html)&lt;[T](index.html)&gt;(pollingAction: suspend ([StreamObserver](../../com.tink.service.streaming.publisher/-stream-observer/index.html)&lt;[T](index.html)&gt;) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) : [Stream](../../com.tink.service.streaming.publisher/-stream/index.html)&lt;[T](index.html)&gt;



## Constructors


| | |
|---|---|
| [PollingHandler](-polling-handler.html) | [androidJvm]<br>fun &lt;[T](index.html)&gt; [PollingHandler](-polling-handler.html)(pollingAction: suspend ([StreamObserver](../../com.tink.service.streaming.publisher/-stream-observer/index.html)&lt;[T](index.html)&gt;) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [subscribe](subscribe.html) | [androidJvm]<br>open override fun [subscribe](subscribe.html)(observer: [StreamObserver](../../com.tink.service.streaming.publisher/-stream-observer/index.html)&lt;[T](index.html)&gt;): [StreamSubscription](../../com.tink.service.streaming.publisher/-stream-subscription/index.html) |
