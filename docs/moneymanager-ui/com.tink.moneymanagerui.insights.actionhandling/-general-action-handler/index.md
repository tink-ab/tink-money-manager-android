---
title: GeneralActionHandler
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.insights.actionhandling](../index.html)/[GeneralActionHandler](index.html)



# GeneralActionHandler



[androidJvm]\
class [GeneralActionHandler](index.html)@Injectconstructor(val handlers: [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[ActionHandler](../-action-handler/index.html)&gt;, tracker: [InsightsTracker](../-insights-tracker/index.html), actionEventBus: [ActionEventBus](../-action-event-bus/index.html)) : [ActionHandler](../-action-handler/index.html)



## Constructors


| | |
|---|---|
| [GeneralActionHandler](-general-action-handler.html) | [androidJvm]<br>@Inject<br>fun [GeneralActionHandler](-general-action-handler.html)(handlers: [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[ActionHandler](../-action-handler/index.html)&gt;, tracker: [InsightsTracker](../-insights-tracker/index.html), actionEventBus: [ActionEventBus](../-action-event-bus/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [handle](handle.html) | [androidJvm]<br>open override fun [handle](handle.html)(action: [InsightAction](../../com.tink.model.insights/-insight-action/index.html), insight: [Insight](../../com.tink.model.insights/-insight/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |


## Properties


| Name | Summary |
|---|---|
| [handlers](handlers.html) | [androidJvm]<br>val [handlers](handlers.html): [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[ActionHandler](../-action-handler/index.html)&gt; |

