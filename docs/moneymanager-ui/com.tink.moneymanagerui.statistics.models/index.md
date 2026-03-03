---
title: com.tink.moneymanagerui.statistics.models
---
//[moneymanager-ui](../../index.html)/[com.tink.moneymanagerui.statistics.models](index.html)



# Package com.tink.moneymanagerui.statistics.models



## Types


| Name | Summary |
|---|---|
| [ChartDetailsSourceState](-chart-details-source-state/index.html) | [androidJvm]<br>data class [ChartDetailsSourceState](-chart-details-source-state/index.html)(val period: ResponseState&lt;Period&gt; = LoadingState, val category: ResponseState&lt;Category&gt; = LoadingState, val currency: ResponseState&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; = LoadingState) |
| [PeriodBalance](-period-balance/index.html) | [androidJvm]<br>data class [PeriodBalance](-period-balance/index.html)(var period: Period?, var amount: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)) |
| [TabPieChartDetailsState](-tab-pie-chart-details-state/index.html) | [androidJvm]<br>data class [TabPieChartDetailsState](-tab-pie-chart-details-state/index.html)(val selectedPeriod: ResponseState&lt;Period&gt; = LoadingState, val periods: ResponseState&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Period&gt;&gt; = LoadingState, val category: ResponseState&lt;Category&gt; = LoadingState, val userProfile: ResponseState&lt;UserProfile&gt; = LoadingState) |

