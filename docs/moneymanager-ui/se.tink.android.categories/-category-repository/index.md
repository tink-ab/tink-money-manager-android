---
title: CategoryRepository
---
//[moneymanager-ui](../../../index.html)/[se.tink.android.categories](../index.html)/[CategoryRepository](index.html)



# CategoryRepository



[androidJvm]\
class [CategoryRepository](index.html)@Injectconstructor(service: CategoryService, dispatcher: DispatcherProvider)



## Constructors


| | |
|---|---|
| [CategoryRepository](-category-repository.html) | [androidJvm]<br>@Inject<br>fun [CategoryRepository](-category-repository.html)(service: CategoryService, dispatcher: DispatcherProvider) |


## Functions


| Name | Summary |
|---|---|
| [refresh](refresh.html) | [androidJvm]<br>fun [refresh](refresh.html)() |
| [refreshState](refresh-state.html) | [androidJvm]<br>fun [refreshState](refresh-state.html)() |


## Properties


| Name | Summary |
|---|---|
| [categories](categories.html) | [androidJvm]<br>val [categories](categories.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;CategoryTree&gt; |
| [categoriesState](categories-state.html) | [androidJvm]<br>val [categoriesState](categories-state.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;ResponseState&lt;CategoryTree&gt;&gt; |

