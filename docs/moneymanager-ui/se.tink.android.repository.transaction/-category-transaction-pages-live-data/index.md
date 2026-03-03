---
title: CategoryTransactionPagesLiveData
---
//[moneymanager-ui](../../../index.html)/[se.tink.android.repository.transaction](../index.html)/[CategoryTransactionPagesLiveData](index.html)



# CategoryTransactionPagesLiveData



[androidJvm]\
class [CategoryTransactionPagesLiveData](index.html)(categoryId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val appExecutors: [AppExecutors](../../se.tink.android/-app-executors/index.html), val transactionService: TransactionService, transactionUpdateEventBus: [TransactionUpdateEventBus](../-transaction-update-event-bus/index.html), dispatcher: DispatcherProvider, val period: Period?) : [AbstractTransactionPagesLiveData](../-abstract-transaction-pages-live-data/index.html)



## Constructors


| | |
|---|---|
| [CategoryTransactionPagesLiveData](-category-transaction-pages-live-data.html) | [androidJvm]<br>fun [CategoryTransactionPagesLiveData](-category-transaction-pages-live-data.html)(categoryId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), appExecutors: [AppExecutors](../../se.tink.android/-app-executors/index.html), transactionService: TransactionService, transactionUpdateEventBus: [TransactionUpdateEventBus](../-transaction-update-event-bus/index.html), dispatcher: DispatcherProvider, period: Period?) |


## Functions


| Name | Summary |
|---|---|
| [dispose](../-abstract-transaction-pages-live-data/dispose.html) | [androidJvm]<br>open override fun [dispose](../-abstract-transaction-pages-live-data/dispose.html)() |
| [getValue](index.html#685674515%2FFunctions%2F1000845458) | [androidJvm]<br>@[Nullable](https://developer.android.com/reference/kotlin/androidx/annotation/Nullable.html)<br>open fun [getValue](index.html#685674515%2FFunctions%2F1000845458)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Transaction&gt;? |
| [hasActiveObservers](index.html#-1328333103%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [hasActiveObservers](index.html#-1328333103%2FFunctions%2F1000845458)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hasObservers](index.html#-1046544021%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [hasObservers](index.html#-1046544021%2FFunctions%2F1000845458)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [loadMoreItems](../-abstract-transaction-pages-live-data/load-more-items.html) | [androidJvm]<br>open override fun [loadMoreItems](../-abstract-transaction-pages-live-data/load-more-items.html)() |
| [observe](index.html#-1386863726%2FFunctions%2F1000845458) | [androidJvm]<br>@[MainThread](https://developer.android.com/reference/kotlin/androidx/annotation/MainThread.html)<br>open fun [observe](index.html#-1386863726%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [LifecycleOwner](https://developer.android.com/reference/kotlin/androidx/lifecycle/LifecycleOwner.html), @[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p1: [Observer](https://developer.android.com/reference/kotlin/androidx/lifecycle/Observer.html)&lt;in [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Transaction&gt;&gt;) |
| [observeForever](index.html#989084662%2FFunctions%2F1000845458) | [androidJvm]<br>@[MainThread](https://developer.android.com/reference/kotlin/androidx/annotation/MainThread.html)<br>open fun [observeForever](index.html#989084662%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Observer](https://developer.android.com/reference/kotlin/androidx/lifecycle/Observer.html)&lt;in [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Transaction&gt;&gt;) |
| [postValue](index.html#1535280525%2FFunctions%2F1000845458) | [androidJvm]<br>open override fun [postValue](index.html#1535280525%2FFunctions%2F1000845458)(p0: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Transaction&gt;) |
| [removeObserver](index.html#-1954534265%2FFunctions%2F1000845458) | [androidJvm]<br>@[MainThread](https://developer.android.com/reference/kotlin/androidx/annotation/MainThread.html)<br>open fun [removeObserver](index.html#-1954534265%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Observer](https://developer.android.com/reference/kotlin/androidx/lifecycle/Observer.html)&lt;in [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Transaction&gt;&gt;) |
| [removeObservers](index.html#1487287389%2FFunctions%2F1000845458) | [androidJvm]<br>@[MainThread](https://developer.android.com/reference/kotlin/androidx/annotation/MainThread.html)<br>open fun [removeObservers](index.html#1487287389%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [LifecycleOwner](https://developer.android.com/reference/kotlin/androidx/lifecycle/LifecycleOwner.html)) |
| [setValue](index.html#158311269%2FFunctions%2F1000845458) | [androidJvm]<br>open override fun [setValue](index.html#158311269%2FFunctions%2F1000845458)(p0: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Transaction&gt;) |


## Properties


| Name | Summary |
|---|---|
| [appExecutors](../-abstract-transaction-pages-live-data/app-executors.html) | [androidJvm]<br>val [appExecutors](../-abstract-transaction-pages-live-data/app-executors.html): [AppExecutors](../../se.tink.android/-app-executors/index.html) |
| [period](period.html) | [androidJvm]<br>val [period](period.html): Period? |
| [transactionService](../-abstract-transaction-pages-live-data/transaction-service.html) | [androidJvm]<br>val [transactionService](../-abstract-transaction-pages-live-data/transaction-service.html): TransactionService |

