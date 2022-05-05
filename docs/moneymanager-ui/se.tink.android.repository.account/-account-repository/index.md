---
title: AccountRepository
---
//[moneymanager-ui](../../../index.html)/[se.tink.android.repository.account](../index.html)/[AccountRepository](index.html)



# AccountRepository



[androidJvm]\
class [AccountRepository](index.html)@Injectconstructor(accountService: [AccountService](../../com.tink.service.account/-account-service/index.html), dataRefreshHandler: [DataRefreshHandler](../../se.tink.android.repository.service/-data-refresh-handler/index.html), dispatcher: [DispatcherProvider](../../com.tink.service.util/-dispatcher-provider/index.html)) : [Refreshable](../../se.tink.android.repository.service/-refreshable/index.html)



## Constructors


| | |
|---|---|
| [AccountRepository](-account-repository.html) | [androidJvm]<br>@Inject<br>fun [AccountRepository](-account-repository.html)(accountService: [AccountService](../../com.tink.service.account/-account-service/index.html), dataRefreshHandler: [DataRefreshHandler](../../se.tink.android.repository.service/-data-refresh-handler/index.html), dispatcher: [DispatcherProvider](../../com.tink.service.util/-dispatcher-provider/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [accountById](account-by-id.html) | [androidJvm]<br>fun [accountById](account-by-id.html)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Account](../../com.tink.model.account/-account/index.html)?&gt; |
| [accountByIdState](account-by-id-state.html) | [androidJvm]<br>fun [accountByIdState](account-by-id-state.html)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[ResponseState](../../com.tink.service.network/-response-state/index.html)&lt;[Account](../../com.tink.model.account/-account/index.html)&gt;&gt; |
| [refresh](refresh.html) | [androidJvm]<br>open override fun [refresh](refresh.html)() |
| [updateAccountState](update-account-state.html) | [androidJvm]<br>suspend fun [updateAccountState](update-account-state.html)(descriptor: [UpdateAccountDescriptor](../../com.tink.service.account/-update-account-descriptor/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [accounts](accounts.html) | [androidJvm]<br>val [accounts](accounts.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Account](../../com.tink.model.account/-account/index.html)&gt;&gt; |
| [accountsState](accounts-state.html) | [androidJvm]<br>val [accountsState](accounts-state.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[ResponseState](../../com.tink.service.network/-response-state/index.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Account](../../com.tink.model.account/-account/index.html)&gt;&gt;&gt; |

