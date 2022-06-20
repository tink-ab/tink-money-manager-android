---
title: com.tink.moneymanagerui.statistics.models
---
//[moneymanager-ui](../../index.html)/[com.tink.moneymanagerui.statistics.models](index.html)



# Package com.tink.moneymanagerui.statistics.models



## Types


| Name | Summary |
|---|---|
| [ChartDetailsSourceState](-chart-details-source-state/index.html) | [androidJvm]<br>data class [ChartDetailsSourceState](-chart-details-source-state/index.html)(val period: [ResponseState](../com.tink.service.network/-response-state/index.html)&lt;[Period](../com.tink.model.time/-period/index.html)&gt; = LoadingState, val category: [ResponseState](../com.tink.service.network/-response-state/index.html)&lt;[Category](../com.tink.model.category/-category/index.html)&gt; = LoadingState, val currency: [ResponseState](../com.tink.service.network/-response-state/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; = LoadingState) |
| [PeriodBalance](-period-balance/index.html) | [androidJvm]<br>data class [PeriodBalance](-period-balance/index.html)(var period: [Period](../com.tink.model.time/-period/index.html)?, var amount: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)) |
| [TabPieChartDetailsState](-tab-pie-chart-details-state/index.html) | [androidJvm]<br>data class [TabPieChartDetailsState](-tab-pie-chart-details-state/index.html)(val selectedPeriod: [ResponseState](../com.tink.service.network/-response-state/index.html)&lt;[Period](../com.tink.model.time/-period/index.html)&gt; = LoadingState, val periods: [ResponseState](../com.tink.service.network/-response-state/index.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Period](../com.tink.model.time/-period/index.html)&gt;&gt; = LoadingState, val category: [ResponseState](../com.tink.service.network/-response-state/index.html)&lt;[Category](../com.tink.model.category/-category/index.html)&gt; = LoadingState, val userProfile: [ResponseState](../com.tink.service.network/-response-state/index.html)&lt;[UserProfile](../com.tink.model.user/-user-profile/index.html)&gt; = LoadingState) |

