---
title: TransactionService
---
//[moneymanager-ui](../../../index.html)/[com.tink.service.transaction](../index.html)/[TransactionService](index.html)



# TransactionService



[androidJvm]\
interface [TransactionService](index.html)



## Functions


| Name | Summary |
|---|---|
| [categorizeTransactions](categorize-transactions.html) | [androidJvm]<br>abstract suspend fun [categorizeTransactions](categorize-transactions.html)(transactionIds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, newCategoryId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [getSimilarTransactions](get-similar-transactions.html) | [androidJvm]<br>abstract suspend fun [getSimilarTransactions](get-similar-transactions.html)(transactionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Transaction](../../com.tink.model.transaction/-transaction/index.html)&gt; |
| [getTransaction](get-transaction.html) | [androidJvm]<br>abstract suspend fun [getTransaction](get-transaction.html)(transactionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Transaction](../../com.tink.model.transaction/-transaction/index.html) |
| [listTransactions](list-transactions.html) | [androidJvm]<br>abstract suspend fun [listTransactions](list-transactions.html)(accountId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, categoryId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, period: [Period](../../com.tink.model.time/-period/index.html)? = null, offset: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Transaction](../../com.tink.model.transaction/-transaction/index.html)&gt; |


## Extensions


| Name | Summary |
|---|---|
| [fetchAllTransactions](../../se.tink.android.repository.transaction/fetch-all-transactions.html) | [androidJvm]<br>suspend fun [TransactionService](index.html).[fetchAllTransactions](../../se.tink.android.repository.transaction/fetch-all-transactions.html)(period: [Period](../../com.tink.model.time/-period/index.html), categoryId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Transaction](../../com.tink.model.transaction/-transaction/index.html)&gt; |

