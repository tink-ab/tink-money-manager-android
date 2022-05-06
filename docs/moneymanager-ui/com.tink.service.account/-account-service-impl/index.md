---
title: AccountServiceImpl
---
//[moneymanager-ui](../../../index.html)/[com.tink.service.account](../index.html)/[AccountServiceImpl](index.html)



# AccountServiceImpl



[androidJvm]\
class [AccountServiceImpl](index.html)@Injectconstructor(api: AccountApi) : [AccountService](../-account-service/index.html)



## Constructors


| | |
|---|---|
| [AccountServiceImpl](-account-service-impl.html) | [androidJvm]<br>@Inject<br>fun [AccountServiceImpl](-account-service-impl.html)(api: AccountApi) |


## Functions


| Name | Summary |
|---|---|
| [listAccounts](list-accounts.html) | [androidJvm]<br>open suspend override fun [listAccounts](list-accounts.html)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Account](../../com.tink.model.account/-account/index.html)&gt; |
| [update](update.html) | [androidJvm]<br>open suspend override fun [update](update.html)(descriptor: [UpdateAccountDescriptor](../-update-account-descriptor/index.html)): [Account](../../com.tink.model.account/-account/index.html) |

