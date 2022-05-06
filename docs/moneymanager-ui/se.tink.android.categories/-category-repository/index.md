---
title: CategoryRepository
---
//[moneymanager-ui](../../../index.html)/[se.tink.android.categories](../index.html)/[CategoryRepository](index.html)



# CategoryRepository



[androidJvm]\
class [CategoryRepository](index.html)@Injectconstructor(service: [CategoryService](../../com.tink.service.category/-category-service/index.html), dispatcher: [DispatcherProvider](../../com.tink.service.util/-dispatcher-provider/index.html))



## Constructors


| | |
|---|---|
| [CategoryRepository](-category-repository.html) | [androidJvm]<br>@Inject<br>fun [CategoryRepository](-category-repository.html)(service: [CategoryService](../../com.tink.service.category/-category-service/index.html), dispatcher: [DispatcherProvider](../../com.tink.service.util/-dispatcher-provider/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [refresh](refresh.html) | [androidJvm]<br>fun [refresh](refresh.html)() |
| [refreshState](refresh-state.html) | [androidJvm]<br>fun [refreshState](refresh-state.html)() |


## Properties


| Name | Summary |
|---|---|
| [categories](categories.html) | [androidJvm]<br>val [categories](categories.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[CategoryTree](../../com.tink.model.category/-category-tree/index.html)&gt; |
| [categoriesState](categories-state.html) | [androidJvm]<br>val [categoriesState](categories-state.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[ResponseState](../../com.tink.service.network/-response-state/index.html)&lt;[CategoryTree](../../com.tink.model.category/-category-tree/index.html)&gt;&gt; |

