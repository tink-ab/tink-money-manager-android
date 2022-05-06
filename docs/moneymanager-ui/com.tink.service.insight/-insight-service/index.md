---
title: InsightService
---
//[moneymanager-ui](../../../index.html)/[com.tink.service.insight](../index.html)/[InsightService](index.html)



# InsightService



[androidJvm]\
interface [InsightService](index.html)



## Functions


| Name | Summary |
|---|---|
| [listArchived](list-archived.html) | [androidJvm]<br>abstract suspend fun [listArchived](list-archived.html)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Insight](../../com.tink.model.insights/-insight/index.html)&gt; |
| [listInsights](list-insights.html) | [androidJvm]<br>abstract suspend fun [listInsights](list-insights.html)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Insight](../../com.tink.model.insights/-insight/index.html)&gt; |
| [selectAction](select-action.html) | [androidJvm]<br>abstract suspend fun [selectAction](select-action.html)(performedAction: [PerformedInsightAction](../../com.tink.model.insights/-performed-insight-action/index.html)) |


## Inheritors


| Name |
|---|
| [InsightServiceImpl](../-insight-service-impl/index.html) |

