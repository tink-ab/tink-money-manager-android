---
title: com.tink.service.account
---
//[moneymanager-ui](../../index.html)/[com.tink.service.account](index.html)



# Package com.tink.service.account



## Types


| Name | Summary |
|---|---|
| [AccountService](-account-service/index.html) | [androidJvm]<br>interface [AccountService](-account-service/index.html) |
| [AccountServiceImpl](-account-service-impl/index.html) | [androidJvm]<br>class [AccountServiceImpl](-account-service-impl/index.html)@Injectconstructor(api: AccountApi) : [AccountService](-account-service/index.html) |
| [UpdateAccountDescriptor](-update-account-descriptor/index.html) | [androidJvm]<br>data class [UpdateAccountDescriptor](-update-account-descriptor/index.html)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val accountExclusion: [Account.AccountExclusion](../com.tink.model.account/-account/-account-exclusion/index.html)? = null, val favored: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)? = null, val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val ownership: [ExactNumber](../com.tink.model.misc/-exact-number/index.html)? = null, val type: [Account.Type](../com.tink.model.account/-account/-type/index.html)? = null) |


## Functions


| Name | Summary |
|---|---|
| [toCoreModel](to-core-model.html) | [androidJvm]<br>fun Account.[toCoreModel](to-core-model.html)(): [Account](../com.tink.model.account/-account/index.html) |

