---
title: ViewModelFactory
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui](../index.html)/[ViewModelFactory](index.html)



# ViewModelFactory



[androidJvm]\
open class [ViewModelFactory](index.html)@Injectconstructor(providers: ModelProviders) : [ViewModelProvider.Factory](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModelProvider.Factory.html)

View model provider factory allows injection into view models



## Constructors


| | |
|---|---|
| [ViewModelFactory](-view-model-factory.html) | [androidJvm]<br>@Inject<br>fun [ViewModelFactory](-view-model-factory.html)(providers: ModelProviders) |


## Functions


| Name | Summary |
|---|---|
| [create](create.html) | [androidJvm]<br>open override fun &lt;[T](create.html) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)&gt; [create](create.html)(modelClass: [Class](https://developer.android.com/reference/kotlin/java/lang/Class.html)&lt;[T](create.html)&gt;): [T](create.html)<br>open fun &lt;[T](../../com.tink.moneymanagerui.insights.di/-insights-view-model-factory/index.html#79759200%2FFunctions%2F1000845458) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)&gt; [create](../../com.tink.moneymanagerui.insights.di/-insights-view-model-factory/index.html#79759200%2FFunctions%2F1000845458)(modelClass: [Class](https://developer.android.com/reference/kotlin/java/lang/Class.html)&lt;[T](../../com.tink.moneymanagerui.insights.di/-insights-view-model-factory/index.html#79759200%2FFunctions%2F1000845458)&gt;, extras: [CreationExtras](https://developer.android.com/reference/kotlin/androidx/lifecycle/viewmodel/CreationExtras.html)): [T](../../com.tink.moneymanagerui.insights.di/-insights-view-model-factory/index.html#79759200%2FFunctions%2F1000845458) |


## Inheritors


| Name |
|---|
| [InsightsViewModelFactory](../../com.tink.moneymanagerui.insights.di/-insights-view-model-factory/index.html) |

