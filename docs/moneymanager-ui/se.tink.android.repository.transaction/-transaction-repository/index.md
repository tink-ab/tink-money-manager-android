---
title: TransactionRepository
---
//[moneymanager-ui](../../../index.html)/[se.tink.android.repository.transaction](../index.html)/[TransactionRepository](index.html)



# TransactionRepository



[androidJvm]\
@ExperimentalCoroutinesApi



class [TransactionRepository](index.html)@Injectconstructor(transactionService: [TransactionService](../../com.tink.service.transaction/-transaction-service/index.html), appExecutors: [AppExecutors](../../se.tink.android/-app-executors/index.html), transactionUpdateEventBus: [TransactionUpdateEventBus](../-transaction-update-event-bus/index.html), dispatcher: [DispatcherProvider](../../com.tink.service.util/-dispatcher-provider/index.html))



## Constructors


| | |
|---|---|
| [TransactionRepository](-transaction-repository.html) | [androidJvm]<br>@Inject<br>fun [TransactionRepository](-transaction-repository.html)(transactionService: [TransactionService](../../com.tink.service.transaction/-transaction-service/index.html), appExecutors: [AppExecutors](../../se.tink.android/-app-executors/index.html), transactionUpdateEventBus: [TransactionUpdateEventBus](../-transaction-update-event-bus/index.html), dispatcher: [DispatcherProvider](../../com.tink.service.util/-dispatcher-provider/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [allTransactionsForCategoryAndPeriod](all-transactions-for-category-and-period.html) | [androidJvm]<br>fun [allTransactionsForCategoryAndPeriod](all-transactions-for-category-and-period.html)(category: [Category](../../com.tink.model.category/-category/index.html), period: [Period](../../com.tink.model.time/-period/index.html)): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Transaction](../../com.tink.model.transaction/-transaction/index.html)&gt;&gt; |
| [categorizeTransactions](categorize-transactions.html) | [androidJvm]<br>fun [categorizeTransactions](categorize-transactions.html)(transactionIds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, newCategoryId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), onError: ([TinkNetworkError](../../se.tink.android.repository/-tink-network-error/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |
| [fetchById](fetch-by-id.html) | [androidJvm]<br>fun [fetchById](fetch-by-id.html)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [SingleTransactionLiveData](../-single-transaction-live-data/index.html) |
| [fetchSimilarTransactions](fetch-similar-transactions.html) | [androidJvm]<br>fun [fetchSimilarTransactions](fetch-similar-transactions.html)(transactionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Transaction](../../com.tink.model.transaction/-transaction/index.html)&gt;&gt; |
| [forAccountId](for-account-id.html) | [androidJvm]<br>fun [forAccountId](for-account-id.html)(accountId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [TransactionPagesLiveData](../-transaction-pages-live-data/index.html) |
| [fromIdList](from-id-list.html) | [androidJvm]<br>suspend fun [fromIdList](from-id-list.html)(ids: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Transaction](../../com.tink.model.transaction/-transaction/index.html)&gt; |
| [fromIdsAsLiveData](from-ids-as-live-data.html) | [androidJvm]<br>fun [fromIdsAsLiveData](from-ids-as-live-data.html)(ids: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Transaction](../../com.tink.model.transaction/-transaction/index.html)&gt;&gt; |

