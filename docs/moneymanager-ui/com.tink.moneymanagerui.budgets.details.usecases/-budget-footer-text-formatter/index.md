---
title: BudgetFooterTextFormatter
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.budgets.details.usecases](../index.html)/[BudgetFooterTextFormatter](index.html)



# BudgetFooterTextFormatter



[androidJvm]\
class [BudgetFooterTextFormatter](index.html)@Injectconstructor(@[ApplicationScoped](../../se.tink.android.di.application/-application-scoped/index.html)val resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html))



## Constructors


| | |
|---|---|
| [BudgetFooterTextFormatter](-budget-footer-text-formatter.html) | [androidJvm]<br>@Inject<br>fun [BudgetFooterTextFormatter](-budget-footer-text-formatter.html)(@[ApplicationScoped](../../se.tink.android.di.application/-application-scoped/index.html)resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)) |


## Functions


| Name | Summary |
|---|---|
| [formatEnded](format-ended.html) | [androidJvm]<br>fun [formatEnded](format-ended.html)(remainingAmount: Amount): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [formatNotStarted](format-not-started.html) | [androidJvm]<br>fun [formatNotStarted](format-not-started.html)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [formatOngoing](format-ongoing.html) | [androidJvm]<br>fun [formatOngoing](format-ongoing.html)(remainingAmount: Amount, timeUnit: [ChronoUnit](https://developer.android.com/reference/kotlin/java/time/temporal/ChronoUnit.html)?): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [formatOverall](format-overall.html) | [androidJvm]<br>fun [formatOverall](format-overall.html)(percentage: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |


## Properties


| Name | Summary |
|---|---|
| [resources](resources.html) | [androidJvm]<br>val [resources](resources.html): [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html) |

