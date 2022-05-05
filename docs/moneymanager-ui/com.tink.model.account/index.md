---
title: com.tink.model.account
---
//[moneymanager-ui](../../index.html)/[com.tink.model.account](index.html)



# Package com.tink.model.account



## Types


| Name | Summary |
|---|---|
| [Account](-account/index.html) | [androidJvm]<br>data class [Account](-account/index.html)(val accountNumber: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val balance: [Amount](../com.tink.model.misc/-amount/index.html), val credentialsId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val excluded: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), val favored: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), val closed: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val holderName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val accountDetails: [AccountDetails](-account-details/index.html)? = null, val ownership: [ExactNumber](../com.tink.model.misc/-exact-number/index.html), val type: [Account.Type](-account/-type/index.html), val flags: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Account.Flags](-account/-flags/index.html)&gt;, val accountExclusion: [Account.AccountExclusion](-account/-account-exclusion/index.html), val refreshed: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html) = Instant.EPOCH, val financialInstitutionID: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val identifiers: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, val transferDestinations: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, val firstSeen: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |
| [AccountDetails](-account-details/index.html) | [androidJvm]<br>data class [AccountDetails](-account-details/index.html)(val interest: [ExactNumber](../com.tink.model.misc/-exact-number/index.html)? = null, val numberOfMonthsBound: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, val type: [AccountDetails.Type](-account-details/-type/index.html)?, val nextDayOfTermsChange: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html)? = null) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |
| [AccountWithName](-account-with-name/index.html) | [androidJvm]<br>data class [AccountWithName](-account-with-name/index.html)(val accountId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val accountName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |

