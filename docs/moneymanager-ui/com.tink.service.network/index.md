---
title: com.tink.service.network
---
//[moneymanager-ui](../../index.html)/[com.tink.service.network](index.html)



# Package com.tink.service.network



## Types


| Name | Summary |
|---|---|
| [Environment](-environment/index.html) | [androidJvm]<br>sealed class [Environment](-environment/index.html)<br>Represents the environment you want to connect to. |
| [ErrorState](-error-state/index.html) | [androidJvm]<br>data class [ErrorState](-error-state/index.html)&lt;[T](-error-state/index.html)&gt;(val errorMessage: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null) : [ResponseState](-response-state/index.html)&lt;[T](-error-state/index.html)&gt; |
| [LoadingState](-loading-state/index.html) | [androidJvm]<br>object [LoadingState](-loading-state/index.html) : [ResponseState](-response-state/index.html)&lt;[Nothing](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-nothing/index.html)&gt; |
| [ResponseState](-response-state/index.html) | [androidJvm]<br>sealed class [ResponseState](-response-state/index.html)&lt;out [T](-response-state/index.html)&gt; |
| [SdkClient](-sdk-client/index.html) | [androidJvm]<br>enum [SdkClient](-sdk-client/index.html) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[SdkClient](-sdk-client/index.html)&gt; |
| [SdkInformation](-sdk-information/index.html) | [androidJvm]<br>data class [SdkInformation](-sdk-information/index.html)(val sdkClient: [SdkClient](-sdk-client/index.html), val version: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [SuccessState](-success-state/index.html) | [androidJvm]<br>data class [SuccessState](-success-state/index.html)&lt;[T](-success-state/index.html)&gt;(val data: [T](-success-state/index.html)) : [ResponseState](-response-state/index.html)&lt;[T](-success-state/index.html)&gt; |
| [TinkConfiguration](-tink-configuration/index.html) | [androidJvm]<br>data class [TinkConfiguration](-tink-configuration/index.html)@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)constructor(val environment: [Environment](-environment/index.html), val oAuthClientId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val redirectUri: [Uri](https://developer.android.com/reference/kotlin/android/net/Uri.html), val callbackUri: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null)<br>Configuration for Tink Link |


## Functions


| Name | Summary |
|---|---|
| [map](map.html) | [androidJvm]<br>fun &lt;[T](map.html), [R](map.html)&gt; [ResponseState](-response-state/index.html)&lt;[T](map.html)&gt;.[map](map.html)(f: ([T](map.html)) -&gt; [R](map.html)): [ResponseState](-response-state/index.html)&lt;[R](map.html)&gt; |
| [withSslKey](with-ssl-key.html) | [androidJvm]<br>fun [Environment.Production](-environment/-production/index.html).[withSslKey](with-ssl-key.html)(sslCertificate: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Environment.Custom](-environment/-custom/index.html)<br>Creates an environment with the [Environment.Production](-environment/-production/index.html) rest URL and the specified [sslCertificate](with-ssl-key.html). |


## Properties


| Name | Summary |
|---|---|
| [coreSdkInformation](core-sdk-information.html) | [androidJvm]<br>var [coreSdkInformation](core-sdk-information.html): [SdkInformation](-sdk-information/index.html)? = null |

