---
title: AllTransactionPagesLiveData
---
//[moneymanager-ui](../../../index.html)/[se.tink.android.repository.transaction](../index.html)/[AllTransactionPagesLiveData](index.html)



# AllTransactionPagesLiveData



[androidJvm]\
class [AllTransactionPagesLiveData](index.html)(val appExecutors: [AppExecutors](../../se.tink.android/-app-executors/index.html), val transactionService: TransactionService, transactionUpdateEventBus: [TransactionUpdateEventBus](../-transaction-update-event-bus/index.html), dispatcher: DispatcherProvider) : [AbstractTransactionPagesLiveData](../-abstract-transaction-pages-live-data/index.html)



## Constructors


| | |
|---|---|
| [AllTransactionPagesLiveData](-all-transaction-pages-live-data.html) | [androidJvm]<br>fun [AllTransactionPagesLiveData](-all-transaction-pages-live-data.html)(appExecutors: [AppExecutors](../../se.tink.android/-app-executors/index.html), transactionService: TransactionService, transactionUpdateEventBus: [TransactionUpdateEventBus](../-transaction-update-event-bus/index.html), dispatcher: DispatcherProvider) |


## Functions


| Name | Summary |
|---|---|
| [dispose](../-abstract-transaction-pages-live-data/dispose.html) | [androidJvm]<br>open override fun [dispose](../-abstract-transaction-pages-live-data/dispose.html)() |
| [getValue](../-category-transaction-pages-live-data/index.html#685674515%2FFunctions%2F1000845458) | [androidJvm]<br>@[Nullable](https://developer.android.com/reference/kotlin/androidx/annotation/Nullable.html)<br>open fun [getValue](../-category-transaction-pages-live-data/index.html#685674515%2FFunctions%2F1000845458)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Transaction&gt;? |
| [hasActiveObservers](../-category-transaction-pages-live-data/index.html#-1328333103%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [hasActiveObservers](../-category-transaction-pages-live-data/index.html#-1328333103%2FFunctions%2F1000845458)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hasObservers](../-category-transaction-pages-live-data/index.html#-1046544021%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [hasObservers](../-category-transaction-pages-live-data/index.html#-1046544021%2FFunctions%2F1000845458)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [loadMoreItems](../-abstract-transaction-pages-live-data/load-more-items.html) | [androidJvm]<br>open override fun [loadMoreItems](../-abstract-transaction-pages-live-data/load-more-items.html)() |
| [observe](../-category-transaction-pages-live-data/index.html#-1386863726%2FFunctions%2F1000845458) | [androidJvm]<br>@[MainThread](https://developer.android.com/reference/kotlin/androidx/annotation/MainThread.html)<br>open fun [observe](../-category-transaction-pages-live-data/index.html#-1386863726%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [LifecycleOwner](https://developer.android.com/reference/kotlin/androidx/lifecycle/LifecycleOwner.html), @[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p1: [Observer](https://developer.android.com/reference/kotlin/androidx/lifecycle/Observer.html)&lt;in [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Transaction&gt;&gt;) |
| [observeForever](../-category-transaction-pages-live-data/index.html#989084662%2FFunctions%2F1000845458) | [androidJvm]<br>@[MainThread](https://developer.android.com/reference/kotlin/androidx/annotation/MainThread.html)<br>open fun [observeForever](../-category-transaction-pages-live-data/index.html#989084662%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Observer](https://developer.android.com/reference/kotlin/androidx/lifecycle/Observer.html)&lt;in [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Transaction&gt;&gt;) |
| [postValue](../-category-transaction-pages-live-data/index.html#1535280525%2FFunctions%2F1000845458) | [androidJvm]<br>open override fun [postValue](../-category-transaction-pages-live-data/index.html#1535280525%2FFunctions%2F1000845458)(p0: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Transaction&gt;) |
| [removeObserver](../-category-transaction-pages-live-data/index.html#-1954534265%2FFunctions%2F1000845458) | [androidJvm]<br>@[MainThread](https://developer.android.com/reference/kotlin/androidx/annotation/MainThread.html)<br>open fun [removeObserver](../-category-transaction-pages-live-data/index.html#-1954534265%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Observer](https://developer.android.com/reference/kotlin/androidx/lifecycle/Observer.html)&lt;in [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Transaction&gt;&gt;) |
| [removeObservers](../-category-transaction-pages-live-data/index.html#1487287389%2FFunctions%2F1000845458) | [androidJvm]<br>@[MainThread](https://developer.android.com/reference/kotlin/androidx/annotation/MainThread.html)<br>open fun [removeObservers](../-category-transaction-pages-live-data/index.html#1487287389%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [LifecycleOwner](https://developer.android.com/reference/kotlin/androidx/lifecycle/LifecycleOwner.html)) |
| [setValue](../-category-transaction-pages-live-data/index.html#158311269%2FFunctions%2F1000845458) | [androidJvm]<br>open override fun [setValue](../-category-transaction-pages-live-data/index.html#158311269%2FFunctions%2F1000845458)(p0: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Transaction&gt;) |


## Properties


| Name | Summary |
|---|---|
| [appExecutors](../-abstract-transaction-pages-live-data/app-executors.html) | [androidJvm]<br>val [appExecutors](../-abstract-transaction-pages-live-data/app-executors.html): [AppExecutors](../../se.tink.android/-app-executors/index.html) |
| [transactionService](../-abstract-transaction-pages-live-data/transaction-service.html) | [androidJvm]<br>val [transactionService](../-abstract-transaction-pages-live-data/transaction-service.html): TransactionService |

