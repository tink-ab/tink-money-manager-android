---
title: CustomContainerView
---
//[moneymanager-ui](../../../../index.html)/[com.tink.moneymanagerui](../../index.html)/[OverviewFeature](../index.html)/[CustomContainerView](index.html)



# CustomContainerView



[androidJvm]\
class [CustomContainerView](index.html)(val containerViewId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val width: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val height: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) : [OverviewFeature](../index.html)

Represents a custom view container that can be added to the overview screen.



This container view will be added as a FrameLayout in the overview screen which can be used by users to add their own custom views.



## Parameters


androidJvm

| | |
|---|---|
| containerViewId | The custom view container id |
| width | The width of the container view, either MATCH_PARENT, WRAP_CONTENT or a fixed size in pixels |
| height | The height of the container view, either MATCH_PARENT, WRAP_CONTENT or a fixed size in pixels |



## Constructors


| | |
|---|---|
| [CustomContainerView](-custom-container-view.html) | [androidJvm]<br>fun [CustomContainerView](-custom-container-view.html)(containerViewId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), width: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), height: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [describeContents](../../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [describeContents](../../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](../../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [writeToParcel](../../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458)(p0: [Parcel](https://developer.android.com/reference/kotlin/android/os/Parcel.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [containerViewId](container-view-id.html) | [androidJvm]<br>val [containerViewId](container-view-id.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [height](height.html) | [androidJvm]<br>val [height](height.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [width](width.html) | [androidJvm]<br>val [width](width.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

