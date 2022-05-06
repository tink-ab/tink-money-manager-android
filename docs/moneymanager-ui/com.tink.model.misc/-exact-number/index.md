---
title: ExactNumber
---
//[moneymanager-ui](../../../index.html)/[com.tink.model.misc](../index.html)/[ExactNumber](index.html)



# ExactNumber



[androidJvm]\
data class [ExactNumber](index.html)(val unscaledValue: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), val scale: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)) : [Comparable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-comparable/index.html)&lt;[ExactNumber](index.html)&gt; , [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)



## Constructors


| | |
|---|---|
| [ExactNumber](-exact-number.html) | [androidJvm]<br>fun [ExactNumber](-exact-number.html)(double: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)) |
| [ExactNumber](-exact-number.html) | [androidJvm]<br>fun [ExactNumber](-exact-number.html)(long: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)) |
| [ExactNumber](-exact-number.html) | [androidJvm]<br>fun [ExactNumber](-exact-number.html)(bigDecimal: [BigDecimal](https://developer.android.com/reference/kotlin/java/math/BigDecimal.html)) |
| [ExactNumber](-exact-number.html) | [androidJvm]<br>fun [ExactNumber](-exact-number.html)(unscaledValue: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), scale: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [compareTo](compare-to.html) | [androidJvm]<br>open operator override fun [compareTo](compare-to.html)(other: [ExactNumber](index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [describeContents](../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [describeContents](../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [toBigDecimal](to-big-decimal.html) | [androidJvm]<br>fun [toBigDecimal](to-big-decimal.html)(): [BigDecimal](https://developer.android.com/reference/kotlin/java/math/BigDecimal.html) |
| [writeToParcel](../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [writeToParcel](../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458)(p0: [Parcel](https://developer.android.com/reference/kotlin/android/os/Parcel.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [scale](scale.html) | [androidJvm]<br>val [scale](scale.html): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [unscaledValue](unscaled-value.html) | [androidJvm]<br>val [unscaledValue](unscaled-value.html): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |


## Extensions


| Name | Summary |
|---|---|
| [absValue](../../se.tink.commons.extensions/abs-value.html) | [androidJvm]<br>fun [ExactNumber](index.html).[absValue](../../se.tink.commons.extensions/abs-value.html)(): [ExactNumber](index.html) |
| [add](../../se.tink.commons.extensions/add.html) | [androidJvm]<br>fun [ExactNumber](index.html).[add](../../se.tink.commons.extensions/add.html)(other: [ExactNumber](index.html)): [ExactNumber](index.html) |
| [divide](../../se.tink.commons.extensions/divide.html) | [androidJvm]<br>fun [ExactNumber](index.html).[divide](../../se.tink.commons.extensions/divide.html)(other: [ExactNumber](index.html)): [ExactNumber](index.html) |
| [doubleValue](../../se.tink.commons.extensions/double-value.html) | [androidJvm]<br>fun [ExactNumber](index.html).[doubleValue](../../se.tink.commons.extensions/double-value.html)(): [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |
| [floatValue](../../se.tink.commons.extensions/float-value.html) | [androidJvm]<br>fun [ExactNumber](index.html).[floatValue](../../se.tink.commons.extensions/float-value.html)(): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |
| [isBiggerThan](../../se.tink.commons.extensions/is-bigger-than.html) | [androidJvm]<br>fun [ExactNumber](index.html).[isBiggerThan](../../se.tink.commons.extensions/is-bigger-than.html)(other: [ExactNumber](index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isInteger](../../se.tink.commons.extensions/is-integer.html) | [androidJvm]<br>fun [ExactNumber](index.html).[isInteger](../../se.tink.commons.extensions/is-integer.html)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isSmallerThan](../../se.tink.commons.extensions/is-smaller-than.html) | [androidJvm]<br>fun [ExactNumber](index.html).[isSmallerThan](../../se.tink.commons.extensions/is-smaller-than.html)(other: [ExactNumber](index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [longValue](../../se.tink.commons.extensions/long-value.html) | [androidJvm]<br>fun [ExactNumber](index.html).[longValue](../../se.tink.commons.extensions/long-value.html)(): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [multiply](../../se.tink.commons.extensions/multiply.html) | [androidJvm]<br>fun [ExactNumber](index.html).[multiply](../../se.tink.commons.extensions/multiply.html)(other: [ExactNumber](index.html)): [ExactNumber](index.html) |
| [round](../../se.tink.commons.extensions/round.html) | [androidJvm]<br>fun [ExactNumber](index.html).[round](../../se.tink.commons.extensions/round.html)(decimals: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [ExactNumber](index.html) |
| [subtract](../../se.tink.commons.extensions/subtract.html) | [androidJvm]<br>fun [ExactNumber](index.html).[subtract](../../se.tink.commons.extensions/subtract.html)(other: [ExactNumber](index.html)): [ExactNumber](index.html) |

