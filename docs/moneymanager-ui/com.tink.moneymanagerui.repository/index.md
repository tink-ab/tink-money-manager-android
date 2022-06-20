---
title: com.tink.moneymanagerui.repository
---
//[moneymanager-ui](../../index.html)/[com.tink.moneymanagerui.repository](index.html)



# Package com.tink.moneymanagerui.repository



## Types


| Name | Summary |
|---|---|
| [StatisticsRepository](-statistics-repository/index.html) | [androidJvm]<br>@ExperimentalCoroutinesApi<br>class [StatisticsRepository](-statistics-repository/index.html)@Injectconstructor(statisticsService: [StatisticsService](../com.tink.service.statistics/-statistics-service/index.html), dataRefreshHandler: [DataRefreshHandler](../se.tink.android.repository.service/-data-refresh-handler/index.html), userRepository: [UserRepository](../se.tink.android.repository.user/-user-repository/index.html), transactionUpdateEventBus: [TransactionUpdateEventBus](../se.tink.android.repository.transaction/-transaction-update-event-bus/index.html), dispatcher: [DispatcherProvider](../com.tink.service.util/-dispatcher-provider/index.html)) : [Refreshable](../se.tink.android.repository.service/-refreshable/index.html) |
| [StatisticsState](-statistics-state/index.html) | [androidJvm]<br>data class [StatisticsState](-statistics-state/index.html)(val statistics: [ResponseState](../com.tink.service.network/-response-state/index.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Statistics](../com.tink.model.statistics/-statistics/index.html)&gt;&gt; = LoadingState, val categories: [ResponseState](../com.tink.service.network/-response-state/index.html)&lt;[CategoryTree](../com.tink.model.category/-category-tree/index.html)&gt; = LoadingState, val userProfile: [ResponseState](../com.tink.service.network/-response-state/index.html)&lt;[UserProfile](../com.tink.model.user/-user-profile/index.html)&gt; = LoadingState, val period: [ResponseState](../com.tink.service.network/-response-state/index.html)&lt;[Period](../com.tink.model.time/-period/index.html)?&gt; = LoadingState) |

