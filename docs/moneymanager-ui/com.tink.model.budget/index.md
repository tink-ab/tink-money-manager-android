---
title: com.tink.model.budget
---
//[moneymanager-ui](../../index.html)/[com.tink.model.budget](index.html)



# Package com.tink.model.budget



## Types


| Name | Summary |
|---|---|
| [Budget](-budget/index.html) | [androidJvm]<br>class [Budget](-budget/index.html) |
| [BudgetCreateOrUpdateDescriptor](-budget-create-or-update-descriptor/index.html) | [androidJvm]<br>data class [BudgetCreateOrUpdateDescriptor](-budget-create-or-update-descriptor/index.html)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val targetAmount: [Amount](../com.tink.model.misc/-amount/index.html), val filter: [Budget.Specification.Filter](-budget/-specification/-filter/index.html), val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val periodicity: [Budget.Periodicity](-budget/-periodicity/index.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)<br>Descriptor for creating or updating a budget. If we have an id, it means we're updating an already existing budget. |
| [BudgetFilter](index.html#-2018963458%2FClasslikes%2F1000845458) | [androidJvm]<br>typealias [BudgetFilter](index.html#-2018963458%2FClasslikes%2F1000845458) = [Budget.Specification.Filter](-budget/-specification/-filter/index.html) |
| [BudgetPeriod](index.html#406477269%2FClasslikes%2F1000845458) | [androidJvm]<br>typealias [BudgetPeriod](index.html#406477269%2FClasslikes%2F1000845458) = [Budget.Period](-budget/-period/index.html) |
| [BudgetPeriodicity](index.html#-756637127%2FClasslikes%2F1000845458) | [androidJvm]<br>typealias [BudgetPeriodicity](index.html#-756637127%2FClasslikes%2F1000845458) = [Budget.Periodicity](-budget/-periodicity/index.html) |
| [BudgetSpecification](index.html#1357535401%2FClasslikes%2F1000845458) | [androidJvm]<br>typealias [BudgetSpecification](index.html#1357535401%2FClasslikes%2F1000845458) = [Budget.Specification](-budget/-specification/index.html) |
| [BudgetSummary](index.html#1968246694%2FClasslikes%2F1000845458) | [androidJvm]<br>typealias [BudgetSummary](index.html#1968246694%2FClasslikes%2F1000845458) = [Budget.Summary](-budget/-summary/index.html) |
| [BudgetTransaction](index.html#-1403204114%2FClasslikes%2F1000845458) | [androidJvm]<br>typealias [BudgetTransaction](index.html#-1403204114%2FClasslikes%2F1000845458) = [Budget.Transaction](-budget/-transaction/index.html) |
| [OneOffPeriodicity](index.html#668268061%2FClasslikes%2F1000845458) | [androidJvm]<br>typealias [OneOffPeriodicity](index.html#668268061%2FClasslikes%2F1000845458) = [Budget.Periodicity.OneOff](-budget/-periodicity/-one-off/index.html) |
| [RecurringPeriodicity](index.html#-420361691%2FClasslikes%2F1000845458) | [androidJvm]<br>typealias [RecurringPeriodicity](index.html#-420361691%2FClasslikes%2F1000845458) = [Budget.Periodicity.Recurring](-budget/-periodicity/-recurring/index.html) |

