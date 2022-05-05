---
title: com.tink.model.transaction
---
//[moneymanager-ui](../../index.html)/[com.tink.model.transaction](index.html)



# Package com.tink.model.transaction



## Types


| Name | Summary |
|---|---|
| [CategoryType](-category-type/index.html) | [androidJvm]<br>enum [CategoryType](-category-type/index.html) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[CategoryType](-category-type/index.html)&gt; , [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |
| [Tag](-tag/index.html) | [androidJvm]<br>data class [Tag](-tag/index.html)(val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |
| [Transaction](-transaction/index.html) | [androidJvm]<br>data class [Transaction](-transaction/index.html)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val amount: [Amount](../com.tink.model.misc/-amount/index.html), val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val categoryId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val date: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html), val accountId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val notes: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val tags: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Tag](-tag/index.html)&gt;, val upcoming: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), val pending: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), val originalDate: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html), val originalDescription: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val originalAmount: [Amount](../com.tink.model.misc/-amount/index.html), val inserted: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html), val categoryType: [CategoryType](-category-type/index.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |

