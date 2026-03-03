---
title: TransactionMockFactory
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.mock](../index.html)/[TransactionMockFactory](index.html)



# TransactionMockFactory



[androidJvm]\
object [TransactionMockFactory](index.html)



## Functions


| Name | Summary |
|---|---|
| [getTransaction](get-transaction.html) | [androidJvm]<br>fun [getTransaction](get-transaction.html)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;8a703fa458d144f9b802b09b26a43e89&quot;, accountId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;4f034cc4629b4f72b6199d1d128af472&quot;, amount: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;-100.0&quot;, description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;Netflix&quot;, timestampAsLong: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) = BASE_TRANSACTION_TIMESTAMP, currencyCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;SEK&quot;, isPending: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true): [JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html) |
| [getTransactionsForAccount](get-transactions-for-account.html) | [androidJvm]<br>fun [getTransactionsForAccount](get-transactions-for-account.html)(accountId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;4f034cc4629b4f72b6199d1d128af472&quot;, numberOfTransactions: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 1, isPending: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; = listOf(false)): [JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html) |


## Properties


| Name | Summary |
|---|---|
| [BASE_TRANSACTION_TIMESTAMP](-b-a-s-e_-t-r-a-n-s-a-c-t-i-o-n_-t-i-m-e-s-t-a-m-p.html) | [androidJvm]<br>val [BASE_TRANSACTION_TIMESTAMP](-b-a-s-e_-t-r-a-n-s-a-c-t-i-o-n_-t-i-m-e-s-t-a-m-p.html): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) = 1641038400000 |

