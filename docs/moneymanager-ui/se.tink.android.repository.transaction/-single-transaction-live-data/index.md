---
title: SingleTransactionLiveData
---
//[moneymanager-ui](../../../index.html)/[se.tink.android.repository.transaction](../index.html)/[SingleTransactionLiveData](index.html)



# SingleTransactionLiveData



[androidJvm]\
@ExperimentalCoroutinesApi



class [SingleTransactionLiveData](index.html)(transactionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val transactionService: [TransactionService](../../com.tink.service.transaction/-transaction-service/index.html), val transactionUpdateEventBus: [TransactionUpdateEventBus](../-transaction-update-event-bus/index.html), dispatcher: [DispatcherProvider](../../com.tink.service.util/-dispatcher-provider/index.html)) : [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[TransactionResult](../-transaction-result/index.html)&gt;



## Constructors


| | |
|---|---|
| [SingleTransactionLiveData](-single-transaction-live-data.html) | [androidJvm]<br>fun [SingleTransactionLiveData](-single-transaction-live-data.html)(transaction: [Transaction](../../com.tink.model.transaction/-transaction/index.html), transactionService: [TransactionService](../../com.tink.service.transaction/-transaction-service/index.html), transactionUpdateEventBus: [TransactionUpdateEventBus](../-transaction-update-event-bus/index.html), dispatcher: [DispatcherProvider](../../com.tink.service.util/-dispatcher-provider/index.html)) |
| [SingleTransactionLiveData](-single-transaction-live-data.html) | [androidJvm]<br>fun [SingleTransactionLiveData](-single-transaction-live-data.html)(transactionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), transactionService: [TransactionService](../../com.tink.service.transaction/-transaction-service/index.html), transactionUpdateEventBus: [TransactionUpdateEventBus](../-transaction-update-event-bus/index.html), dispatcher: [DispatcherProvider](../../com.tink.service.util/-dispatcher-provider/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [changeActiveCounter](../-category-transaction-pages-live-data/index.html#-1482381820%2FFunctions%2F1000845458) | [androidJvm]<br>@[MainThread](https://developer.android.com/reference/kotlin/androidx/annotation/MainThread.html)<br>open fun [changeActiveCounter](../-category-transaction-pages-live-data/index.html#-1482381820%2FFunctions%2F1000845458)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [dispatchingValue](index.html#1520490864%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [dispatchingValue](index.html#1520490864%2FFunctions%2F1000845458)(@[Nullable](https://developer.android.com/reference/kotlin/androidx/annotation/Nullable.html)p0: [LiveData.ObserverWrapper](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.ObserverWrapper.html)&lt;[TransactionResult](../-transaction-result/index.html)&gt;?) |
| [dispose](dispose.html) | [androidJvm]<br>fun [dispose](dispose.html)() |
| [getValue](../-category-transaction-pages-live-data/index.html#685674515%2FFunctions%2F1000845458) | [androidJvm]<br>@[Nullable](https://developer.android.com/reference/kotlin/androidx/annotation/Nullable.html)<br>open fun [getValue](../-category-transaction-pages-live-data/index.html#685674515%2FFunctions%2F1000845458)(): [TransactionResult](../-transaction-result/index.html)? |
| [getVersion](../-category-transaction-pages-live-data/index.html#-256882484%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [getVersion](../-category-transaction-pages-live-data/index.html#-256882484%2FFunctions%2F1000845458)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [hasActiveObservers](../-category-transaction-pages-live-data/index.html#-1328333103%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [hasActiveObservers](../-category-transaction-pages-live-data/index.html#-1328333103%2FFunctions%2F1000845458)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hasObservers](../-category-transaction-pages-live-data/index.html#-1046544021%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [hasObservers](../-category-transaction-pages-live-data/index.html#-1046544021%2FFunctions%2F1000845458)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [observe](index.html#-801824696%2FFunctions%2F1000845458) | [androidJvm]<br>@[MainThread](https://developer.android.com/reference/kotlin/androidx/annotation/MainThread.html)<br>open fun [observe](index.html#-801824696%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [LifecycleOwner](https://developer.android.com/reference/kotlin/androidx/lifecycle/LifecycleOwner.html), @[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p1: [Observer](https://developer.android.com/reference/kotlin/androidx/lifecycle/Observer.html)&lt;in [TransactionResult](../-transaction-result/index.html)&gt;) |
| [observeForever](index.html#481161316%2FFunctions%2F1000845458) | [androidJvm]<br>@[MainThread](https://developer.android.com/reference/kotlin/androidx/annotation/MainThread.html)<br>open fun [observeForever](index.html#481161316%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Observer](https://developer.android.com/reference/kotlin/androidx/lifecycle/Observer.html)&lt;in [TransactionResult](../-transaction-result/index.html)&gt;) |
| [onActive](../-category-transaction-pages-live-data/index.html#931098953%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [onActive](../-category-transaction-pages-live-data/index.html#931098953%2FFunctions%2F1000845458)() |
| [onInactive](../-category-transaction-pages-live-data/index.html#989844228%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [onInactive](../-category-transaction-pages-live-data/index.html#989844228%2FFunctions%2F1000845458)() |
| [postValue](index.html#1370194915%2FFunctions%2F1000845458) | [androidJvm]<br>open override fun [postValue](index.html#1370194915%2FFunctions%2F1000845458)(p0: [TransactionResult](../-transaction-result/index.html)) |
| [removeObserver](index.html#-712634125%2FFunctions%2F1000845458) | [androidJvm]<br>@[MainThread](https://developer.android.com/reference/kotlin/androidx/annotation/MainThread.html)<br>open fun [removeObserver](index.html#-712634125%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Observer](https://developer.android.com/reference/kotlin/androidx/lifecycle/Observer.html)&lt;in [TransactionResult](../-transaction-result/index.html)&gt;) |
| [removeObservers](../-category-transaction-pages-live-data/index.html#1487287389%2FFunctions%2F1000845458) | [androidJvm]<br>@[MainThread](https://developer.android.com/reference/kotlin/androidx/annotation/MainThread.html)<br>open fun [removeObservers](../-category-transaction-pages-live-data/index.html#1487287389%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [LifecycleOwner](https://developer.android.com/reference/kotlin/androidx/lifecycle/LifecycleOwner.html)) |
| [setValue](index.html#2126891275%2FFunctions%2F1000845458) | [androidJvm]<br>open override fun [setValue](index.html#2126891275%2FFunctions%2F1000845458)(p0: [TransactionResult](../-transaction-result/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [mActiveCount](../-category-transaction-pages-live-data/index.html#-163308686%2FProperties%2F1000845458) | [androidJvm]<br>val [mActiveCount](../-category-transaction-pages-live-data/index.html#-163308686%2FProperties%2F1000845458): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [mDataLock](../-category-transaction-pages-live-data/index.html#-1918813974%2FProperties%2F1000845458) | [androidJvm]<br>val [mDataLock](../-category-transaction-pages-live-data/index.html#-1918813974%2FProperties%2F1000845458): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html) |
| [mPendingData](../-category-transaction-pages-live-data/index.html#230544954%2FProperties%2F1000845458) | [androidJvm]<br>val [mPendingData](../-category-transaction-pages-live-data/index.html#230544954%2FProperties%2F1000845458): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html) |
| [transactionService](transaction-service.html) | [androidJvm]<br>val [transactionService](transaction-service.html): [TransactionService](../../com.tink.service.transaction/-transaction-service/index.html) |
| [transactionUpdateEventBus](transaction-update-event-bus.html) | [androidJvm]<br>val [transactionUpdateEventBus](transaction-update-event-bus.html): [TransactionUpdateEventBus](../-transaction-update-event-bus/index.html) |

