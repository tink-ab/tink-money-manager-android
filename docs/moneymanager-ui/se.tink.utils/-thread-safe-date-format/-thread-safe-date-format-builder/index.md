---
title: ThreadSafeDateFormatBuilder
---
//[moneymanager-ui](../../../../index.html)/[se.tink.utils](../../index.html)/[ThreadSafeDateFormat](../index.html)/[ThreadSafeDateFormatBuilder](index.html)



# ThreadSafeDateFormatBuilder



[androidJvm]\
class [ThreadSafeDateFormatBuilder](index.html)(pattern: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), locale: [Locale](https://developer.android.com/reference/kotlin/java/util/Locale.html), timezone: DateTimeZone) : [Cloneable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-cloneable/index.html)

A builder used to construct [ThreadSafeDateFormat](../index.html)s



## Constructors


| | |
|---|---|
| [ThreadSafeDateFormatBuilder](-thread-safe-date-format-builder.html) | [androidJvm]<br>fun [ThreadSafeDateFormatBuilder](-thread-safe-date-format-builder.html)(pattern: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), locale: [Locale](https://developer.android.com/reference/kotlin/java/util/Locale.html), timezone: DateTimeZone) |


## Functions


| Name | Summary |
|---|---|
| [build](build.html) | [androidJvm]<br>fun [build](build.html)(): [ThreadSafeDateFormat](../index.html) |
| [clone](clone.html) | [androidJvm]<br>open override fun [clone](clone.html)(): [ThreadSafeDateFormat.ThreadSafeDateFormatBuilder](index.html) |
| [setLocale](set-locale.html) | [androidJvm]<br>fun [setLocale](set-locale.html)(locale: [Locale](https://developer.android.com/reference/kotlin/java/util/Locale.html)): [ThreadSafeDateFormat.ThreadSafeDateFormatBuilder](index.html) |
| [setPattern](set-pattern.html) | [androidJvm]<br>fun [setPattern](set-pattern.html)(pattern: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [ThreadSafeDateFormat.ThreadSafeDateFormatBuilder](index.html) |


## Properties


| Name | Summary |
|---|---|
| [locale](locale.html) | [androidJvm]<br>val [locale](locale.html): [Locale](https://developer.android.com/reference/kotlin/java/util/Locale.html)? = null |
| [pattern](pattern.html) | [androidJvm]<br>val [pattern](pattern.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [timezone](timezone.html) | [androidJvm]<br>val [timezone](timezone.html): DateTimeZone? = null |

