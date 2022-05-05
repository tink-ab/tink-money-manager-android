---
title: StatisticsState
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.repository](../index.html)/[StatisticsState](index.html)



# StatisticsState



[androidJvm]\
data class [StatisticsState](index.html)(val statistics: [ResponseState](../../com.tink.service.network/-response-state/index.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Statistics](../../com.tink.model.statistics/-statistics/index.html)&gt;&gt; = LoadingState, val categories: [ResponseState](../../com.tink.service.network/-response-state/index.html)&lt;[CategoryTree](../../com.tink.model.category/-category-tree/index.html)&gt; = LoadingState, val userProfile: [ResponseState](../../com.tink.service.network/-response-state/index.html)&lt;[UserProfile](../../com.tink.model.user/-user-profile/index.html)&gt; = LoadingState, val period: [ResponseState](../../com.tink.service.network/-response-state/index.html)&lt;[Period](../../com.tink.model.time/-period/index.html)?&gt; = LoadingState)



## Constructors


| | |
|---|---|
| [StatisticsState](-statistics-state.html) | [androidJvm]<br>fun [StatisticsState](-statistics-state.html)(statistics: [ResponseState](../../com.tink.service.network/-response-state/index.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Statistics](../../com.tink.model.statistics/-statistics/index.html)&gt;&gt; = LoadingState, categories: [ResponseState](../../com.tink.service.network/-response-state/index.html)&lt;[CategoryTree](../../com.tink.model.category/-category-tree/index.html)&gt; = LoadingState, userProfile: [ResponseState](../../com.tink.service.network/-response-state/index.html)&lt;[UserProfile](../../com.tink.model.user/-user-profile/index.html)&gt; = LoadingState, period: [ResponseState](../../com.tink.service.network/-response-state/index.html)&lt;[Period](../../com.tink.model.time/-period/index.html)?&gt; = LoadingState) |


## Properties


| Name | Summary |
|---|---|
| [categories](categories.html) | [androidJvm]<br>val [categories](categories.html): [ResponseState](../../com.tink.service.network/-response-state/index.html)&lt;[CategoryTree](../../com.tink.model.category/-category-tree/index.html)&gt; |
| [period](period.html) | [androidJvm]<br>val [period](period.html): [ResponseState](../../com.tink.service.network/-response-state/index.html)&lt;[Period](../../com.tink.model.time/-period/index.html)?&gt; |
| [statistics](statistics.html) | [androidJvm]<br>val [statistics](statistics.html): [ResponseState](../../com.tink.service.network/-response-state/index.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Statistics](../../com.tink.model.statistics/-statistics/index.html)&gt;&gt; |
| [userProfile](user-profile.html) | [androidJvm]<br>val [userProfile](user-profile.html): [ResponseState](../../com.tink.service.network/-response-state/index.html)&lt;[UserProfile](../../com.tink.model.user/-user-profile/index.html)&gt; |

