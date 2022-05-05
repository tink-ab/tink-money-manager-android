---
title: com.tink.model.transfer
---
//[moneymanager-ui](../../index.html)/[com.tink.model.transfer](index.html)



# Package com.tink.model.transfer



## Types


| Name | Summary |
|---|---|
| [Beneficiary](-beneficiary/index.html) | [androidJvm]<br>data class [Beneficiary](-beneficiary/index.html)(val ownerAccountId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val accountNumber: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val accountNumberType: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)<br>A beneficiary is a payment or transfer destination account which has been authorized by the bank. Each beneficiary belongs to an account, which means that the given account can send money to that beneficiary. |
| [SignableOperation](-signable-operation/index.html) | [androidJvm]<br>data class [SignableOperation](-signable-operation/index.html)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val credentialsId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val created: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html), val status: [SignableOperation.Status](-signable-operation/-status/index.html), val statusMessage: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val type: [SignableOperation.Type](-signable-operation/-type/index.html), val underlyingId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val updated: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html), val userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |

