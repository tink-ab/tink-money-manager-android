---
title: TransactionsPageable
---
//[moneymanager-ui](../../../index.html)/[se.tink.android.repository.transaction](../index.html)/[TransactionsPageable](index.html)



# TransactionsPageable



[androidJvm]\
class [TransactionsPageable](index.html)(transactionService: [TransactionService](../../com.tink.service.transaction/-transaction-service/index.html), transactionUpdateEventBus: [TransactionUpdateEventBus](../-transaction-update-event-bus/index.html), dispatcher: [DispatcherProvider](../../com.tink.service.util/-dispatcher-provider/index.html), liveData: [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Transaction](../../com.tink.model.transaction/-transaction/index.html)&gt;&gt;, accountId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, categoryId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, period: [Period](../../com.tink.model.time/-period/index.html)? = null) : [Pageable](../../com.tink.service.transaction/-pageable/index.html)



## Constructors


| | |
|---|---|
| [TransactionsPageable](-transactions-pageable.html) | [androidJvm]<br>fun [TransactionsPageable](-transactions-pageable.html)(transactionService: [TransactionService](../../com.tink.service.transaction/-transaction-service/index.html), transactionUpdateEventBus: [TransactionUpdateEventBus](../-transaction-update-event-bus/index.html), dispatcher: [DispatcherProvider](../../com.tink.service.util/-dispatcher-provider/index.html), liveData: [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Transaction](../../com.tink.model.transaction/-transaction/index.html)&gt;&gt;, accountId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, categoryId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, period: [Period](../../com.tink.model.time/-period/index.html)? = null) |


## Functions


| Name | Summary |
|---|---|
| [dispose](dispose.html) | [androidJvm]<br>fun [dispose](dispose.html)() |
| [hasMore](has-more.html) | [androidJvm]<br>open override fun [hasMore](has-more.html)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [next](next.html) | [androidJvm]<br>open override fun [next](next.html)(resultHandler: [ResultHandler](../../com.tink.service.handler/-result-handler/index.html)&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;) |

