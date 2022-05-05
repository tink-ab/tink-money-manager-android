---
title: com.tink.model.user
---
//[moneymanager-ui](../../index.html)/[com.tink.model.user](index.html)



# Package com.tink.model.user



## Types


| Name | Summary |
|---|---|
| [Authorization](-authorization/index.html) | [androidJvm]<br>sealed class [Authorization](-authorization/index.html) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |
| [Scope](-scope/index.html) | [androidJvm]<br>sealed class [Scope](-scope/index.html) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |
| [User](-user/index.html) | [androidJvm]<br>data class [User](-user/index.html)(val authorization: [Authorization](-authorization/index.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |
| [UserInfo](-user-info/index.html) | [androidJvm]<br>data class [UserInfo](-user-info/index.html)(val created: [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html) = Instant.EPOCH, val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val appId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val profile: [UserProfile](-user-profile/index.html), val flags: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; = emptyList(), val username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val nationalId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |
| [UserProfile](-user-profile/index.html) | [androidJvm]<br>data class [UserProfile](-user-profile/index.html)(val locale: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val market: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val timeZone: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val currency: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val periodMode: [UserProfile.PeriodMode](-user-profile/-period-mode/index.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |

