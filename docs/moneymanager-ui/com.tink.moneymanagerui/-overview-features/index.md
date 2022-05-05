---
title: OverviewFeatures
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui](../index.html)/[OverviewFeatures](index.html)



# OverviewFeatures



[androidJvm]\
data class [OverviewFeatures](index.html)(val features: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[OverviewFeature](../-overview-feature/index.html)&gt;) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)

A wrapper class containing a list of the [OverviewFeature](../-overview-feature/index.html) objects that users can pass as input while creating an instance of the [FinanceOverviewFragment](../-finance-overview-fragment/index.html).



Users can also add all the available features by simply calling [OverviewFeatures.ALL](-companion/-a-l-l.html).



## Constructors


| | |
|---|---|
| [OverviewFeatures](-overview-features.html) | [androidJvm]<br>fun [OverviewFeatures](-overview-features.html)(features: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[OverviewFeature](../-overview-feature/index.html)&gt;) |


## Types


| Name | Summary |
|---|---|
| [Companion](-companion/index.html) | [androidJvm]<br>object [Companion](-companion/index.html) |


## Functions


| Name | Summary |
|---|---|
| [describeContents](../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [describeContents](../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [writeToParcel](../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458)(p0: [Parcel](https://developer.android.com/reference/kotlin/android/os/Parcel.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [features](features.html) | [androidJvm]<br>val [features](features.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[OverviewFeature](../-overview-feature/index.html)&gt; |

