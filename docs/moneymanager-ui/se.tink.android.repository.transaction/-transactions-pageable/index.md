---
title: TransactionsPageable
---
//[moneymanager-ui](../../../index.html)/[se.tink.android.repository.transaction](../index.html)/[TransactionsPageable](index.html)



# TransactionsPageable



[androidJvm]\
class [TransactionsPageable](index.html)(transactionService: TransactionService, transactionUpdateEventBus: [TransactionUpdateEventBus](../-transaction-update-event-bus/index.html), dispatcher: DispatcherProvider, liveData: [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Transaction&gt;&gt;, accountId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, categoryId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, period: Period? = null) : Pageable



## Constructors


| | |
|---|---|
| [TransactionsPageable](-transactions-pageable.html) | [androidJvm]<br>fun [TransactionsPageable](-transactions-pageable.html)(transactionService: TransactionService, transactionUpdateEventBus: [TransactionUpdateEventBus](../-transaction-update-event-bus/index.html), dispatcher: DispatcherProvider, liveData: [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Transaction&gt;&gt;, accountId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, categoryId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, period: Period? = null) |


## Functions


| Name | Summary |
|---|---|
| [dispose](dispose.html) | [androidJvm]<br>fun [dispose](dispose.html)() |
| [hasMore](has-more.html) | [androidJvm]<br>open override fun [hasMore](has-more.html)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [next](next.html) | [androidJvm]<br>open override fun [next](next.html)(resultHandler: ResultHandler&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;) |

