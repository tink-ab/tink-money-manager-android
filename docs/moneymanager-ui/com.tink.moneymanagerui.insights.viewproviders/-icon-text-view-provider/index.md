---
title: IconTextViewProvider
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.insights.viewproviders](../index.html)/[IconTextViewProvider](index.html)



# IconTextViewProvider



[androidJvm]\
@ContributesInsightViewProvider



class [IconTextViewProvider](index.html)@Injectconstructor : [InsightViewProvider](../-insight-view-provider/index.html)



## Constructors


| | |
|---|---|
| [IconTextViewProvider](-icon-text-view-provider.html) | [androidJvm]<br>@Inject<br>fun [IconTextViewProvider](-icon-text-view-provider.html)() |


## Types


| Name | Summary |
|---|---|
| [IconTextViewDataHolder](-icon-text-view-data-holder/index.html) | [androidJvm]<br>data class [IconTextViewDataHolder](-icon-text-view-data-holder/index.html)(val title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val icon: [IconResource](../../se.tink.commons.icons/-icon-resource/index.html), @[AttrRes](https://developer.android.com/reference/kotlin/androidx/annotation/AttrRes.html)val colorAttr: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val state: [InsightState](../../com.tink.model.insights/-insight-state/index.html), val actions: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[InsightAction](../../com.tink.model.insights/-insight-action/index.html)&gt;, val isCritical: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) : [InsightDataHolder](../-insight-data-holder/index.html) |
| [IconTextViewHolder](-icon-text-view-holder/index.html) | [androidJvm]<br>class [IconTextViewHolder](-icon-text-view-holder/index.html)(parent: [ViewGroup](https://developer.android.com/reference/kotlin/android/view/ViewGroup.html), val actionHandler: [ActionHandler](../../com.tink.moneymanagerui.insights.actionhandling/-action-handler/index.html)) : [InsightViewHolder](../-insight-view-holder/index.html), [InsightCommonBottomPart](../-insight-common-bottom-part/index.html) |


## Functions


| Name | Summary |
|---|---|
| [getDataHolder](get-data-holder.html) | [androidJvm]<br>open override fun [getDataHolder](get-data-holder.html)(insight: [Insight](../../com.tink.model.insights/-insight/index.html)): [InsightDataHolder](../-insight-data-holder/index.html) |
| [viewHolder](view-holder.html) | [androidJvm]<br>open override fun [viewHolder](view-holder.html)(parent: [ViewGroup](https://developer.android.com/reference/kotlin/android/view/ViewGroup.html), actionHandler: [ActionHandler](../../com.tink.moneymanagerui.insights.actionhandling/-action-handler/index.html)): [InsightViewHolder](../-insight-view-holder/index.html) |


## Properties


| Name | Summary |
|---|---|
| [supportedInsightTypes](supported-insight-types.html) | [androidJvm]<br>open override val [supportedInsightTypes](supported-insight-types.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[InsightType](../../com.tink.model.insights/-insight-type/index.html)&gt; |
| [viewType](view-type.html) | [androidJvm]<br>open override val [viewType](view-type.html): &lt;ERROR CLASS&gt; |

