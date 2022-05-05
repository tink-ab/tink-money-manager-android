---
title: com.tink.service.credentials
---
//[moneymanager-ui](../../index.html)/[com.tink.service.credentials](index.html)



# Package com.tink.service.credentials



## Types


| Name | Summary |
|---|---|
| [CredentialsAuthenticateDescriptor](-credentials-authenticate-descriptor/index.html) | [androidJvm]<br>data class [CredentialsAuthenticateDescriptor](-credentials-authenticate-descriptor/index.html)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val appUri: [Uri](https://developer.android.com/reference/kotlin/android/net/Uri.html)) |
| [CredentialsCreationDescriptor](-credentials-creation-descriptor/index.html) | [androidJvm]<br>data class [CredentialsCreationDescriptor](-credentials-creation-descriptor/index.html)(val providerName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val type: [Credentials.Type](../com.tink.model.credentials/-credentials/-type/index.html), val fields: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, val appUri: [Uri](https://developer.android.com/reference/kotlin/android/net/Uri.html), val refreshableItems: [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)&lt;[RefreshableItem](../com.tink.model.credentials/-refreshable-item/index.html)&gt;? = null) |
| [CredentialsRefreshDescriptor](-credentials-refresh-descriptor/index.html) | [androidJvm]<br>data class [CredentialsRefreshDescriptor](-credentials-refresh-descriptor/index.html)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val refreshableItems: [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)&lt;[RefreshableItem](../com.tink.model.credentials/-refreshable-item/index.html)&gt;? = null, val authenticate: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)? = null) |
| [CredentialsService](-credentials-service/index.html) | [androidJvm]<br>interface [CredentialsService](-credentials-service/index.html) |
| [CredentialsUpdateDescriptor](-credentials-update-descriptor/index.html) | [androidJvm]<br>data class [CredentialsUpdateDescriptor](-credentials-update-descriptor/index.html)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val providerName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val fields: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, val appUri: [Uri](https://developer.android.com/reference/kotlin/android/net/Uri.html)) |


## Functions


| Name | Summary |
|---|---|
| [createThirdPartyAuthForMobileBankId](create-third-party-auth-for-mobile-bank-id.html) | [androidJvm]<br>fun [createThirdPartyAuthForMobileBankId](create-third-party-auth-for-mobile-bank-id.html)(autostartToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): [ThirdPartyAppAuthentication](../com.tink.model.authentication/-third-party-app-authentication/index.html)? |
| [toCoreModel](to-core-model.html) | [androidJvm]<br>fun Credentials.StatusEnum.[toCoreModel](to-core-model.html)(): [Credentials.Status](../com.tink.model.credentials/-credentials/-status/index.html)<br>fun Credentials.ThirdPartyAuthentication.[toCoreModel](to-core-model.html)(): [ThirdPartyAppAuthentication](../com.tink.model.authentication/-third-party-app-authentication/index.html)<br>fun Credentials.TypeEnum.[toCoreModel](to-core-model.html)(): [Credentials.Type](../com.tink.model.credentials/-credentials/-type/index.html) |

