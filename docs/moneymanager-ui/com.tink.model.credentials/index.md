---
title: com.tink.model.credentials
---
//[moneymanager-ui](../../index.html)/[com.tink.model.credentials](index.html)



# Package com.tink.model.credentials



## Types


| Name | Summary |
|---|---|
| [Credentials](-credentials/index.html) | [androidJvm]<br>data class [Credentials](-credentials/index.html)(val providerName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val type: [Credentials.Type](-credentials/-type/index.html), val fields: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val status: [Credentials.Status](-credentials/-status/index.html)? = null, val statusPayload: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val supplementalInformation: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Field](../com.tink.model.misc/-field/index.html)&gt; = emptyList(), val statusUpdated: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html) = Instant.EPOCH, val updated: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html) = Instant.EPOCH, val sessionExpiryDate: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html)? = null, val thirdPartyAppAuthentication: [ThirdPartyAppAuthentication](../com.tink.model.authentication/-third-party-app-authentication/index.html)? = null) : [Comparable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-comparable/index.html)&lt;[Credentials](-credentials/index.html)&gt; , [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)<br>This model represents how users are connected to a [Provider](../com.tink.model.provider/-provider/index.html) to access their financial data. |
| [RefreshableItem](-refreshable-item/index.html) | [androidJvm]<br>enum [RefreshableItem](-refreshable-item/index.html) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[RefreshableItem](-refreshable-item/index.html)&gt; <br>Refreshable items are a way to limit which types of data should be aggregated from a provider. |
| [RefreshCredential](-refresh-credential/index.html) | [androidJvm]<br>data class [RefreshCredential](-refresh-credential/index.html)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val provider: [RefreshProvider](../com.tink.model.provider/-refresh-provider/index.html), val sessionExpiryDate: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |


## Functions


| Name | Summary |
|---|---|
| [plus](plus.html) | [androidJvm]<br>operator fun [RefreshableItem](-refreshable-item/index.html).[plus](plus.html)(other: [RefreshableItem](-refreshable-item/index.html)): [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[RefreshableItem](-refreshable-item/index.html)&gt; |

