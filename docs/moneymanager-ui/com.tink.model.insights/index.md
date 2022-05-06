---
title: com.tink.model.insights
---
//[moneymanager-ui](../../index.html)/[com.tink.model.insights](index.html)



# Package com.tink.model.insights



## Types


| Name | Summary |
|---|---|
| [Insight](-insight/index.html) | [androidJvm]<br>data class [Insight](-insight/index.html)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val type: [InsightType](-insight-type/index.html), val title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val created: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html), val actions: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[InsightAction](-insight-action/index.html)&gt;, val state: [InsightState](-insight-state/index.html), val data: [InsightData](-insight-data/index.html), var viewDetails: [Insight.ViewDetails](-insight/-view-details/index.html)? = null) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |
| [InsightAction](-insight-action/index.html) | [androidJvm]<br>data class [InsightAction](-insight-action/index.html)(val label: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val actionType: [InsightAction.Type](-insight-action/-type/index.html), val data: [InsightAction.Data](-insight-action/-data/index.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |
| [InsightData](-insight-data/index.html) | [androidJvm]<br>sealed class [InsightData](-insight-data/index.html) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)<br>All subclasses should be data classes or provide a meaningful equals() function |
| [InsightState](-insight-state/index.html) | [androidJvm]<br>sealed class [InsightState](-insight-state/index.html) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |
| [InsightType](-insight-type/index.html) | [androidJvm]<br>enum [InsightType](-insight-type/index.html) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[InsightType](-insight-type/index.html)&gt; |
| [PerformedInsightAction](-performed-insight-action/index.html) | [androidJvm]<br>data class [PerformedInsightAction](-performed-insight-action/index.html)(val insightId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val actionType: [InsightAction.Type](-insight-action/-type/index.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |

