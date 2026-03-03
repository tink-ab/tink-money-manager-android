---
title: com.tink.moneymanagerui.repository
---
//[moneymanager-ui](../../index.html)/[com.tink.moneymanagerui.repository](index.html)



# Package com.tink.moneymanagerui.repository



## Types


| Name | Summary |
|---|---|
| [StatisticsRepository](-statistics-repository/index.html) | [androidJvm]<br>@ExperimentalCoroutinesApi<br>class [StatisticsRepository](-statistics-repository/index.html)@Injectconstructor(statisticsService: StatisticsService, dataRefreshHandler: [DataRefreshHandler](../se.tink.android.repository.service/-data-refresh-handler/index.html), userRepository: [UserRepository](../se.tink.android.repository.user/-user-repository/index.html), transactionUpdateEventBus: [TransactionUpdateEventBus](../se.tink.android.repository.transaction/-transaction-update-event-bus/index.html), dispatcher: DispatcherProvider) : [Refreshable](../se.tink.android.repository.service/-refreshable/index.html) |
| [StatisticsState](-statistics-state/index.html) | [androidJvm]<br>data class [StatisticsState](-statistics-state/index.html)(val statistics: ResponseState&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Statistics&gt;&gt; = LoadingState, val categories: ResponseState&lt;CategoryTree&gt; = LoadingState, val userProfile: ResponseState&lt;UserProfile&gt; = LoadingState, val period: ResponseState&lt;Period?&gt; = LoadingState) |

