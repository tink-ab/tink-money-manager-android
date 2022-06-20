---
title: TabPieChartDetailsState
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.statistics.models](../index.html)/[TabPieChartDetailsState](index.html)



# TabPieChartDetailsState



[androidJvm]\
data class [TabPieChartDetailsState](index.html)(val selectedPeriod: [ResponseState](../../com.tink.service.network/-response-state/index.html)&lt;[Period](../../com.tink.model.time/-period/index.html)&gt; = LoadingState, val periods: [ResponseState](../../com.tink.service.network/-response-state/index.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Period](../../com.tink.model.time/-period/index.html)&gt;&gt; = LoadingState, val category: [ResponseState](../../com.tink.service.network/-response-state/index.html)&lt;[Category](../../com.tink.model.category/-category/index.html)&gt; = LoadingState, val userProfile: [ResponseState](../../com.tink.service.network/-response-state/index.html)&lt;[UserProfile](../../com.tink.model.user/-user-profile/index.html)&gt; = LoadingState)



## Constructors


| | |
|---|---|
| [TabPieChartDetailsState](-tab-pie-chart-details-state.html) | [androidJvm]<br>fun [TabPieChartDetailsState](-tab-pie-chart-details-state.html)(selectedPeriod: [ResponseState](../../com.tink.service.network/-response-state/index.html)&lt;[Period](../../com.tink.model.time/-period/index.html)&gt; = LoadingState, periods: [ResponseState](../../com.tink.service.network/-response-state/index.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Period](../../com.tink.model.time/-period/index.html)&gt;&gt; = LoadingState, category: [ResponseState](../../com.tink.service.network/-response-state/index.html)&lt;[Category](../../com.tink.model.category/-category/index.html)&gt; = LoadingState, userProfile: [ResponseState](../../com.tink.service.network/-response-state/index.html)&lt;[UserProfile](../../com.tink.model.user/-user-profile/index.html)&gt; = LoadingState) |


## Properties


| Name | Summary |
|---|---|
| [category](category.html) | [androidJvm]<br>val [category](category.html): [ResponseState](../../com.tink.service.network/-response-state/index.html)&lt;[Category](../../com.tink.model.category/-category/index.html)&gt; |
| [periods](periods.html) | [androidJvm]<br>val [periods](periods.html): [ResponseState](../../com.tink.service.network/-response-state/index.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Period](../../com.tink.model.time/-period/index.html)&gt;&gt; |
| [selectedPeriod](selected-period.html) | [androidJvm]<br>val [selectedPeriod](selected-period.html): [ResponseState](../../com.tink.service.network/-response-state/index.html)&lt;[Period](../../com.tink.model.time/-period/index.html)&gt; |
| [userProfile](user-profile.html) | [androidJvm]<br>val [userProfile](user-profile.html): [ResponseState](../../com.tink.service.network/-response-state/index.html)&lt;[UserProfile](../../com.tink.model.user/-user-profile/index.html)&gt; |

