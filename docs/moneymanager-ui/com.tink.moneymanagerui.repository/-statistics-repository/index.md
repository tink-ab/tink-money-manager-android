---
title: StatisticsRepository
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.repository](../index.html)/[StatisticsRepository](index.html)



# StatisticsRepository



[androidJvm]\
@ExperimentalCoroutinesApi



class [StatisticsRepository](index.html)@Injectconstructor(statisticsService: StatisticsService, dataRefreshHandler: [DataRefreshHandler](../../se.tink.android.repository.service/-data-refresh-handler/index.html), userRepository: [UserRepository](../../se.tink.android.repository.user/-user-repository/index.html), transactionUpdateEventBus: [TransactionUpdateEventBus](../../se.tink.android.repository.transaction/-transaction-update-event-bus/index.html), dispatcher: DispatcherProvider) : [Refreshable](../../se.tink.android.repository.service/-refreshable/index.html)



## Constructors


| | |
|---|---|
| [StatisticsRepository](-statistics-repository.html) | [androidJvm]<br>@Inject<br>fun [StatisticsRepository](-statistics-repository.html)(statisticsService: StatisticsService, dataRefreshHandler: [DataRefreshHandler](../../se.tink.android.repository.service/-data-refresh-handler/index.html), userRepository: [UserRepository](../../se.tink.android.repository.user/-user-repository/index.html), transactionUpdateEventBus: [TransactionUpdateEventBus](../../se.tink.android.repository.transaction/-transaction-update-event-bus/index.html), dispatcher: DispatcherProvider) |


## Functions


| Name | Summary |
|---|---|
| [refresh](refresh.html) | [androidJvm]<br>open override fun [refresh](refresh.html)() |
| [refreshDelayed](refresh-delayed.html) | [androidJvm]<br>suspend fun [refreshDelayed](refresh-delayed.html)(delayMills: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) = 2000)<br>Refreshing imediately might cause problems since the statistics api take some time to update. This refreshes after a given delay to ensure that we fetch updated data. |


## Properties


| Name | Summary |
|---|---|
| [currentPeriod](current-period.html) | [androidJvm]<br>val [currentPeriod](current-period.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;Period&gt; |
| [currentPeriodState](current-period-state.html) | [androidJvm]<br>val [currentPeriodState](current-period-state.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;ResponseState&lt;Period&gt;&gt; |
| [fetchedStatistics](fetched-statistics.html) | [androidJvm]<br>val [fetchedStatistics](fetched-statistics.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Statistics&gt;&gt; |
| [periods](periods.html) | [androidJvm]<br>val [periods](periods.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Period&gt;&gt; |
| [periodsState](periods-state.html) | [androidJvm]<br>val [periodsState](periods-state.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;ResponseState&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Period&gt;&gt;&gt; |
| [statistics](statistics.html) | [androidJvm]<br>val [statistics](statistics.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Statistics&gt;&gt; |
| [statisticsState](statistics-state.html) | [androidJvm]<br>val [statisticsState](statistics-state.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;ResponseState&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Statistics&gt;&gt;&gt; |

