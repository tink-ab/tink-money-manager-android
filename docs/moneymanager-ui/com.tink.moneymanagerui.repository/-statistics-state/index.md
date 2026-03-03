---
title: StatisticsState
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.repository](../index.html)/[StatisticsState](index.html)



# StatisticsState



[androidJvm]\
data class [StatisticsState](index.html)(val statistics: ResponseState&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Statistics&gt;&gt; = LoadingState, val categories: ResponseState&lt;CategoryTree&gt; = LoadingState, val userProfile: ResponseState&lt;UserProfile&gt; = LoadingState, val period: ResponseState&lt;Period?&gt; = LoadingState)



## Constructors


| | |
|---|---|
| [StatisticsState](-statistics-state.html) | [androidJvm]<br>fun [StatisticsState](-statistics-state.html)(statistics: ResponseState&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Statistics&gt;&gt; = LoadingState, categories: ResponseState&lt;CategoryTree&gt; = LoadingState, userProfile: ResponseState&lt;UserProfile&gt; = LoadingState, period: ResponseState&lt;Period?&gt; = LoadingState) |


## Properties


| Name | Summary |
|---|---|
| [categories](categories.html) | [androidJvm]<br>val [categories](categories.html): ResponseState&lt;CategoryTree&gt; |
| [period](period.html) | [androidJvm]<br>val [period](period.html): ResponseState&lt;Period?&gt; |
| [statistics](statistics.html) | [androidJvm]<br>val [statistics](statistics.html): ResponseState&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Statistics&gt;&gt; |
| [userProfile](user-profile.html) | [androidJvm]<br>val [userProfile](user-profile.html): ResponseState&lt;UserProfile&gt; |

