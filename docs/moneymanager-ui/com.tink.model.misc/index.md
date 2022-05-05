---
title: com.tink.model.misc
---
//[moneymanager-ui](../../index.html)/[com.tink.model.misc](index.html)



# Package com.tink.model.misc



## Types


| Name | Summary |
|---|---|
| [Amount](-amount/index.html) | [androidJvm]<br>data class [Amount](-amount/index.html)(val value: [ExactNumber](-exact-number/index.html), val currencyCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |
| [ExactNumber](-exact-number/index.html) | [androidJvm]<br>data class [ExactNumber](-exact-number/index.html)(val unscaledValue: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), val scale: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)) : [Comparable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-comparable/index.html)&lt;[ExactNumber](-exact-number/index.html)&gt; , [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |
| [Field](-field/index.html) | [androidJvm]<br>data class [Field](-field/index.html)(val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val validationRules: [Field.ValidationRules](-field/-validation-rules/index.html), val attributes: [Field.Attributes](-field/-attributes/index.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)<br>This model represents a specific input, usually as a text field) that the user needs to fill during the authentication flow. |

