---
title: com.tink.service.transaction
---
//[moneymanager-ui](../../index.html)/[com.tink.service.transaction](index.html)



# Package com.tink.service.transaction



## Types


| Name | Summary |
|---|---|
| [Pageable](-pageable/index.html) | [androidJvm]<br>interface [Pageable](-pageable/index.html) |
| [TransactionService](-transaction-service/index.html) | [androidJvm]<br>interface [TransactionService](-transaction-service/index.html) |
| [TransactionUpdateDescriptor](-transaction-update-descriptor/index.html) | [androidJvm]<br>data class [TransactionUpdateDescriptor](-transaction-update-descriptor/index.html)(val transactionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val date: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html)? = null, val notes: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val tags: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Tag](../com.tink.model.transaction/-tag/index.html)&gt;? = null) |


## Functions


| Name | Summary |
|---|---|
| [toCoreModel](to-core-model.html) | [androidJvm]<br>fun Transaction.[toCoreModel](to-core-model.html)(): [Transaction](../com.tink.model.transaction/-transaction/index.html)<br>fun TransactionResponse.[toCoreModel](to-core-model.html)(): [Transaction](../com.tink.model.transaction/-transaction/index.html) |

