---
title: Amount
---
//[moneymanager-ui](../../../index.html)/[com.tink.model.misc](../index.html)/[Amount](index.html)



# Amount



[androidJvm]\
data class [Amount](index.html)(val value: [ExactNumber](../-exact-number/index.html), val currencyCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)



## Constructors


| | |
|---|---|
| [Amount](-amount.html) | [androidJvm]<br>fun [Amount](-amount.html)(value: [ExactNumber](../-exact-number/index.html), currencyCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [describeContents](../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [describeContents](../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [writeToParcel](../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458)(p0: [Parcel](https://developer.android.com/reference/kotlin/android/os/Parcel.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [currencyCode](currency-code.html) | [androidJvm]<br>val [currencyCode](currency-code.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [value](value.html) | [androidJvm]<br>val [value](value.html): [ExactNumber](../-exact-number/index.html) |


## Extensions


| Name | Summary |
|---|---|
| [abs](../../se.tink.commons.extensions/abs.html) | [androidJvm]<br>fun [Amount](index.html).[abs](../../se.tink.commons.extensions/abs.html)(): [Amount](index.html) |
| [compareTo](../../se.tink.commons.extensions/compare-to.html) | [androidJvm]<br>operator fun [Amount](index.html).[compareTo](../../se.tink.commons.extensions/compare-to.html)(other: [Amount](index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [div](../../se.tink.commons.extensions/div.html) | [androidJvm]<br>operator fun [Amount](index.html).[div](../../se.tink.commons.extensions/div.html)(int: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Amount](index.html)<br>operator fun [Amount](index.html).[div](../../se.tink.commons.extensions/div.html)(double: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)): [Amount](index.html) |
| [isValid](../../se.tink.commons.extensions/is-valid.html) | [androidJvm]<br>val [Amount](index.html).[isValid](../../se.tink.commons.extensions/is-valid.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [minus](../../se.tink.commons.extensions/minus.html) | [androidJvm]<br>operator fun [Amount](index.html).[minus](../../se.tink.commons.extensions/minus.html)(other: [Amount](index.html)): [Amount](index.html) |
| [plus](../../se.tink.commons.extensions/plus.html) | [androidJvm]<br>operator fun [Amount](index.html).[plus](../../se.tink.commons.extensions/plus.html)(other: [Amount](index.html)): [Amount](index.html) |
| [toDto](../../com.tink.service.misc/to-dto.html) | [androidJvm]<br>fun [Amount](index.html).[toDto](../../com.tink.service.misc/to-dto.html)(): CurrencyDenominatedAmount |

