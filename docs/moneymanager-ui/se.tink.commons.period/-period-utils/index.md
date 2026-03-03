---
title: PeriodUtils
---
//[moneymanager-ui](../../../index.html)/[se.tink.commons.period](../index.html)/[PeriodUtils](index.html)



# PeriodUtils



[androidJvm]\
class [PeriodUtils](index.html)@Injectconstructor(val dateUtils: [DateUtils](../../se.tink.utils/-date-utils/index.html))



## Constructors


| | |
|---|---|
| [PeriodUtils](-period-utils.html) | [androidJvm]<br>@Inject<br>fun [PeriodUtils](-period-utils.html)(dateUtils: [DateUtils](../../se.tink.utils/-date-utils/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [getCurrentSelectedPeriod](get-current-selected-period.html) | [androidJvm]<br>fun [getCurrentSelectedPeriod](get-current-selected-period.html)(statistics: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Statistics&gt;): Period?<br>Get selected period from statistics |
| [getMonthName](get-month-name.html) | [androidJvm]<br>fun [getMonthName](get-month-name.html)(period: Period): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Get month name for given period |
| [getMonthNameCompact](get-month-name-compact.html) | [androidJvm]<br>fun [getMonthNameCompact](get-month-name-compact.html)(period: Period): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Get 3 letter month name for given period |
| [getPeriods](get-periods.html) | [androidJvm]<br>fun [getPeriods](get-periods.html)(statistics: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Statistics&gt;, duration: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Period&gt;<br>Get all periods from statistics |
| [getPeriodString](get-period-string.html) | [androidJvm]<br>fun [getPeriodString](get-period-string.html)(period: Period, tillToday: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Get formatted period range (Start date - End date) |


## Properties


| Name | Summary |
|---|---|
| [dateUtils](date-utils.html) | [androidJvm]<br>val [dateUtils](date-utils.html): [DateUtils](../../se.tink.utils/-date-utils/index.html) |
| [periodFormatter](period-formatter.html) | [androidJvm]<br>val [periodFormatter](period-formatter.html): (Period) -&gt; [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

