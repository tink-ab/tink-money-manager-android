---
title: InsightsRepository
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.insights.repository](../index.html)/[InsightsRepository](index.html)



# InsightsRepository



[androidJvm]\
class [InsightsRepository](index.html)@Injectconstructor(insightService: [InsightService](../../com.tink.service.insight/-insight-service/index.html), dispatcher: [DispatcherProvider](../../com.tink.service.util/-dispatcher-provider/index.html), insightProviderFactory: [InsightViewProviderFactory](../../com.tink.moneymanagerui.insights.viewproviders/-insight-view-provider-factory/index.html))



## Constructors


| | |
|---|---|
| [InsightsRepository](-insights-repository.html) | [androidJvm]<br>@Inject<br>fun [InsightsRepository](-insights-repository.html)(insightService: [InsightService](../../com.tink.service.insight/-insight-service/index.html), dispatcher: [DispatcherProvider](../../com.tink.service.util/-dispatcher-provider/index.html), insightProviderFactory: [InsightViewProviderFactory](../../com.tink.moneymanagerui.insights.viewproviders/-insight-view-provider-factory/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [refreshArchived](refresh-archived.html) | [androidJvm]<br>fun [refreshArchived](refresh-archived.html)() |
| [refreshInsights](refresh-insights.html) | [androidJvm]<br>fun [refreshInsights](refresh-insights.html)() |


## Properties


| Name | Summary |
|---|---|
| [archivedInsights](archived-insights.html) | [androidJvm]<br>val [archivedInsights](archived-insights.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Insight](../../com.tink.model.insights/-insight/index.html)&gt;&gt; |
| [archivedInsightsErrors](archived-insights-errors.html) | [androidJvm]<br>val [archivedInsightsErrors](archived-insights-errors.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Event](../../se.tink.commons.livedata/-event/index.html)&lt;[TinkNetworkError](../../se.tink.android.repository/-tink-network-error/index.html)&gt;?&gt; |
| [insightErrors](insight-errors.html) | [androidJvm]<br>val [insightErrors](insight-errors.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Event](../../se.tink.commons.livedata/-event/index.html)&lt;[TinkNetworkError](../../se.tink.android.repository/-tink-network-error/index.html)&gt;?&gt; |
| [insights](insights.html) | [androidJvm]<br>val [insights](insights.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Insight](../../com.tink.model.insights/-insight/index.html)&gt;&gt; |

