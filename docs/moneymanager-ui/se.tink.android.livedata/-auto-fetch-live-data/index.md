---
title: AutoFetchLiveData
---
//[moneymanager-ui](../../../index.html)/[se.tink.android.livedata](../index.html)/[AutoFetchLiveData](index.html)



# AutoFetchLiveData



[androidJvm]\
class [AutoFetchLiveData](index.html)&lt;[T](index.html)&gt;(fetch: ([AutoFetchLiveData](index.html)&lt;[T](index.html)&gt;) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) : [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[T](index.html)&gt;



## Constructors


| | |
|---|---|
| [AutoFetchLiveData](-auto-fetch-live-data.html) | [androidJvm]<br>fun &lt;[T](index.html)&gt; [AutoFetchLiveData](-auto-fetch-live-data.html)(fetch: ([AutoFetchLiveData](index.html)&lt;[T](index.html)&gt;) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [getValue](../../se.tink.android.repository.transaction/-category-transaction-pages-live-data/index.html#685674515%2FFunctions%2F1000845458) | [androidJvm]<br>@[Nullable](https://developer.android.com/reference/kotlin/androidx/annotation/Nullable.html)<br>open fun [getValue](../../se.tink.android.repository.transaction/-category-transaction-pages-live-data/index.html#685674515%2FFunctions%2F1000845458)(): [T](index.html)? |
| [hasActiveObservers](../../se.tink.android.repository.transaction/-category-transaction-pages-live-data/index.html#-1328333103%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [hasActiveObservers](../../se.tink.android.repository.transaction/-category-transaction-pages-live-data/index.html#-1328333103%2FFunctions%2F1000845458)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hasObservers](../../se.tink.android.repository.transaction/-category-transaction-pages-live-data/index.html#-1046544021%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [hasObservers](../../se.tink.android.repository.transaction/-category-transaction-pages-live-data/index.html#-1046544021%2FFunctions%2F1000845458)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [observe](index.html#185768746%2FFunctions%2F1000845458) | [androidJvm]<br>@[MainThread](https://developer.android.com/reference/kotlin/androidx/annotation/MainThread.html)<br>open fun [observe](index.html#185768746%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [LifecycleOwner](https://developer.android.com/reference/kotlin/androidx/lifecycle/LifecycleOwner.html), @[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p1: [Observer](https://developer.android.com/reference/kotlin/androidx/lifecycle/Observer.html)&lt;in [T](index.html)&gt;) |
| [observeForever](index.html#-1123335282%2FFunctions%2F1000845458) | [androidJvm]<br>@[MainThread](https://developer.android.com/reference/kotlin/androidx/annotation/MainThread.html)<br>open fun [observeForever](index.html#-1123335282%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Observer](https://developer.android.com/reference/kotlin/androidx/lifecycle/Observer.html)&lt;in [T](index.html)&gt;) |
| [postValue](index.html#1536303861%2FFunctions%2F1000845458) | [androidJvm]<br>open override fun [postValue](index.html#1536303861%2FFunctions%2F1000845458)(p0: [T](index.html)) |
| [removeObserver](index.html#758495263%2FFunctions%2F1000845458) | [androidJvm]<br>@[MainThread](https://developer.android.com/reference/kotlin/androidx/annotation/MainThread.html)<br>open fun [removeObserver](index.html#758495263%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Observer](https://developer.android.com/reference/kotlin/androidx/lifecycle/Observer.html)&lt;in [T](index.html)&gt;) |
| [removeObservers](../../se.tink.android.repository.transaction/-category-transaction-pages-live-data/index.html#1487287389%2FFunctions%2F1000845458) | [androidJvm]<br>@[MainThread](https://developer.android.com/reference/kotlin/androidx/annotation/MainThread.html)<br>open fun [removeObservers](../../se.tink.android.repository.transaction/-category-transaction-pages-live-data/index.html#1487287389%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [LifecycleOwner](https://developer.android.com/reference/kotlin/androidx/lifecycle/LifecycleOwner.html)) |
| [setValue](index.html#1823252685%2FFunctions%2F1000845458) | [androidJvm]<br>open override fun [setValue](index.html#1823252685%2FFunctions%2F1000845458)(p0: [T](index.html)) |
| [update](update.html) | [androidJvm]<br>fun [update](update.html)() |

