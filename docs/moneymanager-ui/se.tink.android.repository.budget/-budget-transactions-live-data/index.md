---
title: BudgetTransactionsLiveData
---
//[moneymanager-ui](../../../index.html)/[se.tink.android.repository.budget](../index.html)/[BudgetTransactionsLiveData](index.html)



# BudgetTransactionsLiveData



[androidJvm]\
@ExperimentalCoroutinesApi



class [BudgetTransactionsLiveData](index.html)(budgetService: BudgetService, budgetId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), start: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html), end: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html), dispatcher: DispatcherProvider, transactionUpdateEventBus: [TransactionUpdateEventBus](../../se.tink.android.repository.transaction/-transaction-update-event-bus/index.html)) : [MediatorLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MediatorLiveData.html)&lt;[ErrorOrValue](../../se.tink.android.livedata/-error-or-value/index.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;BudgetTransaction&gt;&gt;&gt;



## Constructors


| | |
|---|---|
| [BudgetTransactionsLiveData](-budget-transactions-live-data.html) | [androidJvm]<br>fun [BudgetTransactionsLiveData](-budget-transactions-live-data.html)(budgetService: BudgetService, budgetId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), start: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html), end: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html), dispatcher: DispatcherProvider, transactionUpdateEventBus: [TransactionUpdateEventBus](../../se.tink.android.repository.transaction/-transaction-update-event-bus/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [addSource](index.html#-64157780%2FFunctions%2F1000845458) | [androidJvm]<br>@[MainThread](https://developer.android.com/reference/kotlin/androidx/annotation/MainThread.html)<br>open fun &lt;[S](index.html#-64157780%2FFunctions%2F1000845458) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [addSource](index.html#-64157780%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[S](index.html#-64157780%2FFunctions%2F1000845458)&gt;, @[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p1: [Observer](https://developer.android.com/reference/kotlin/androidx/lifecycle/Observer.html)&lt;in [S](index.html#-64157780%2FFunctions%2F1000845458)&gt;) |
| [dispose](dispose.html) | [androidJvm]<br>fun [dispose](dispose.html)() |
| [getValue](../../se.tink.android.repository.transaction/-category-transaction-pages-live-data/index.html#685674515%2FFunctions%2F1000845458) | [androidJvm]<br>@[Nullable](https://developer.android.com/reference/kotlin/androidx/annotation/Nullable.html)<br>open fun [getValue](../../se.tink.android.repository.transaction/-category-transaction-pages-live-data/index.html#685674515%2FFunctions%2F1000845458)(): [ErrorOrValue](../../se.tink.android.livedata/-error-or-value/index.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;BudgetTransaction&gt;&gt;? |
| [hasActiveObservers](../../se.tink.android.repository.transaction/-category-transaction-pages-live-data/index.html#-1328333103%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [hasActiveObservers](../../se.tink.android.repository.transaction/-category-transaction-pages-live-data/index.html#-1328333103%2FFunctions%2F1000845458)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hasObservers](../../se.tink.android.repository.transaction/-category-transaction-pages-live-data/index.html#-1046544021%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [hasObservers](../../se.tink.android.repository.transaction/-category-transaction-pages-live-data/index.html#-1046544021%2FFunctions%2F1000845458)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [observe](index.html#749313535%2FFunctions%2F1000845458) | [androidJvm]<br>@[MainThread](https://developer.android.com/reference/kotlin/androidx/annotation/MainThread.html)<br>open fun [observe](index.html#749313535%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [LifecycleOwner](https://developer.android.com/reference/kotlin/androidx/lifecycle/LifecycleOwner.html), @[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p1: [Observer](https://developer.android.com/reference/kotlin/androidx/lifecycle/Observer.html)&lt;in [ErrorOrValue](../../se.tink.android.livedata/-error-or-value/index.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;BudgetTransaction&gt;&gt;&gt;) |
| [observeForever](index.html#1766802971%2FFunctions%2F1000845458) | [androidJvm]<br>@[MainThread](https://developer.android.com/reference/kotlin/androidx/annotation/MainThread.html)<br>open fun [observeForever](index.html#1766802971%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Observer](https://developer.android.com/reference/kotlin/androidx/lifecycle/Observer.html)&lt;in [ErrorOrValue](../../se.tink.android.livedata/-error-or-value/index.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;BudgetTransaction&gt;&gt;&gt;) |
| [postValue](index.html#-21393588%2FFunctions%2F1000845458) | [androidJvm]<br>open override fun [postValue](index.html#-21393588%2FFunctions%2F1000845458)(p0: [ErrorOrValue](../../se.tink.android.livedata/-error-or-value/index.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;BudgetTransaction&gt;&gt;) |
| [removeObserver](index.html#54815914%2FFunctions%2F1000845458) | [androidJvm]<br>@[MainThread](https://developer.android.com/reference/kotlin/androidx/annotation/MainThread.html)<br>open fun [removeObserver](index.html#54815914%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Observer](https://developer.android.com/reference/kotlin/androidx/lifecycle/Observer.html)&lt;in [ErrorOrValue](../../se.tink.android.livedata/-error-or-value/index.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;BudgetTransaction&gt;&gt;&gt;) |
| [removeObservers](../../se.tink.android.repository.transaction/-category-transaction-pages-live-data/index.html#1487287389%2FFunctions%2F1000845458) | [androidJvm]<br>@[MainThread](https://developer.android.com/reference/kotlin/androidx/annotation/MainThread.html)<br>open fun [removeObservers](../../se.tink.android.repository.transaction/-category-transaction-pages-live-data/index.html#1487287389%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [LifecycleOwner](https://developer.android.com/reference/kotlin/androidx/lifecycle/LifecycleOwner.html)) |
| [removeSource](index.html#-676371886%2FFunctions%2F1000845458) | [androidJvm]<br>@[MainThread](https://developer.android.com/reference/kotlin/androidx/annotation/MainThread.html)<br>open fun &lt;[S](index.html#-676371886%2FFunctions%2F1000845458) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [removeSource](index.html#-676371886%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[S](index.html#-676371886%2FFunctions%2F1000845458)&gt;) |
| [setValue](index.html#-740957068%2FFunctions%2F1000845458) | [androidJvm]<br>open override fun [setValue](index.html#-740957068%2FFunctions%2F1000845458)(p0: [ErrorOrValue](../../se.tink.android.livedata/-error-or-value/index.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;BudgetTransaction&gt;&gt;) |

