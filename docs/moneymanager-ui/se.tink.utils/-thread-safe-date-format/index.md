---
title: ThreadSafeDateFormat
---
//[moneymanager-ui](../../../index.html)/[se.tink.utils](../index.html)/[ThreadSafeDateFormat](index.html)



# ThreadSafeDateFormat



[androidJvm]\
class [ThreadSafeDateFormat](index.html)

Immutable thread safe date format.



Uses the Memento design pattern to easily clone preexisting static thread safe date formatters.



## Constructors


| | |
|---|---|
| [ThreadSafeDateFormat](-thread-safe-date-format.html) | [androidJvm]<br>fun [ThreadSafeDateFormat](-thread-safe-date-format.html)(pattern: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, locale: [Locale](https://developer.android.com/reference/kotlin/java/util/Locale.html), timeZoneId: [ZoneId](https://developer.android.com/reference/kotlin/java/time/ZoneId.html)) |


## Types


| Name | Summary |
|---|---|
| [Companion](-companion/index.html) | [androidJvm]<br>object [Companion](-companion/index.html) |
| [ThreadSafeDateFormatBuilder](-thread-safe-date-format-builder/index.html) | [androidJvm]<br>class [ThreadSafeDateFormatBuilder](-thread-safe-date-format-builder/index.html)(pattern: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), locale: [Locale](https://developer.android.com/reference/kotlin/java/util/Locale.html), timeZoneId: [ZoneId](https://developer.android.com/reference/kotlin/java/time/ZoneId.html)) : [Cloneable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-cloneable/index.html)<br>A builder used to construct [ThreadSafeDateFormat](index.html)s |


## Functions


| Name | Summary |
|---|---|
| [format](format.html) | [androidJvm]<br>fun [format](format.html)(date: [LocalDate](https://developer.android.com/reference/kotlin/java/time/LocalDate.html)?): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>fun [format](format.html)(date: [LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html)?): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

