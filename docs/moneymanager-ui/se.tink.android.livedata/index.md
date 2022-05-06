---
title: se.tink.android.livedata
---
//[moneymanager-ui](../../index.html)/[se.tink.android.livedata](index.html)



# Package se.tink.android.livedata



## Types


| Name | Summary |
|---|---|
| [AutoFetchLiveData](-auto-fetch-live-data/index.html) | [androidJvm]<br>class [AutoFetchLiveData](-auto-fetch-live-data/index.html)&lt;[T](-auto-fetch-live-data/index.html)&gt;(fetch: ([AutoFetchLiveData](-auto-fetch-live-data/index.html)&lt;[T](-auto-fetch-live-data/index.html)&gt;) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) : [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[T](-auto-fetch-live-data/index.html)&gt; |
| [ErrorOrValue](-error-or-value/index.html) | [androidJvm]<br>class [ErrorOrValue](-error-or-value/index.html)&lt;[T](-error-or-value/index.html)&gt; |
| [ListChangeObserver](-list-change-observer/index.html) | [androidJvm]<br>interface [ListChangeObserver](-list-change-observer/index.html)&lt;[T](-list-change-observer/index.html)&gt; : [ChangeObserver](../com.tink.service.observer/-change-observer/index.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[T](-list-change-observer/index.html)&gt;&gt; |


## Functions


| Name | Summary |
|---|---|
| [createChangeObserver](create-change-observer.html) | [androidJvm]<br>fun &lt;[T](create-change-observer.html)&gt; [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[T](create-change-observer.html)&gt;&gt;.[createChangeObserver](create-change-observer.html)(executors: [AppExecutors](../se.tink.android/-app-executors/index.html)): [ListChangeObserver](-list-change-observer/index.html)&lt;[T](create-change-observer.html)&gt; |
| [createResultHandler](create-result-handler.html) | [androidJvm]<br>fun &lt;[T](create-result-handler.html)&gt; [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[ErrorOrValue](-error-or-value/index.html)&lt;[T](create-result-handler.html)&gt;&gt;.[createResultHandler](create-result-handler.html)(): [ResultHandler](../com.tink.service.handler/-result-handler/index.html)&lt;[T](create-result-handler.html)&gt; |
| [map](map.html) | [androidJvm]<br>fun &lt;[X](map.html), [Y](map.html)&gt; [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[X](map.html)&gt;.[map](map.html)(mapFunction: ([X](map.html)) -&gt; [Y](map.html)): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Y](map.html)&gt; |
| [mapDistinct](map-distinct.html) | [androidJvm]<br>fun &lt;[T](map-distinct.html), [R](map-distinct.html)&gt; [mapDistinct](map-distinct.html)(source: [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[T](map-distinct.html)&gt;, map: ([T](map-distinct.html)) -&gt; [R](map-distinct.html)): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[R](map-distinct.html)&gt; |
| [observe](observe.html) | [androidJvm]<br>fun &lt;[T](observe.html) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[T](observe.html)&gt;.[observe](observe.html)(owner: [LifecycleOwner](https://developer.android.com/reference/kotlin/androidx/lifecycle/LifecycleOwner.html), observer: ([T](observe.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |
| [switchMap](switch-map.html) | [androidJvm]<br>fun &lt;[X](switch-map.html), [Y](switch-map.html)&gt; [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[X](switch-map.html)&gt;.[switchMap](switch-map.html)(mapFunction: ([X](switch-map.html)) -&gt; [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Y](switch-map.html)&gt;): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Y](switch-map.html)&gt; |


## Properties


| Name | Summary |
|---|---|
| [requireValue](require-value.html) | [androidJvm]<br>val &lt;[T](require-value.html)&gt; [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[T](require-value.html)&gt;.[requireValue](require-value.html): [T](require-value.html) |

