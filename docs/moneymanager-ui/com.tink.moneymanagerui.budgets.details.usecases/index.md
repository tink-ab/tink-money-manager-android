---
title: com.tink.moneymanagerui.budgets.details.usecases
---
//[moneymanager-ui](../../index.html)/[com.tink.moneymanagerui.budgets.details.usecases](index.html)



# Package com.tink.moneymanagerui.budgets.details.usecases



## Types


| Name | Summary |
|---|---|
| [BudgetFooterTextFormatter](-budget-footer-text-formatter/index.html) | [androidJvm]<br>class [BudgetFooterTextFormatter](-budget-footer-text-formatter/index.html)@Injectconstructor(@[ApplicationScoped](../se.tink.android.di.application/-application-scoped/index.html)val resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)) |
| [BudgetFooterTextFormatterTest](-budget-footer-text-formatter-test/index.html) | [androidJvm]<br>class [BudgetFooterTextFormatterTest](-budget-footer-text-formatter-test/index.html) |
| [BudgetHeaderTextFormatter](-budget-header-text-formatter/index.html) | [androidJvm]<br>class [BudgetHeaderTextFormatter](-budget-header-text-formatter/index.html)@Injectconstructor(@[ApplicationScoped](../se.tink.android.di.application/-application-scoped/index.html)val resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)) |
| [BudgetHeaderTextFormatterTest](-budget-header-text-formatter-test/index.html) | [androidJvm]<br>class [BudgetHeaderTextFormatterTest](-budget-header-text-formatter-test/index.html) |
| [GetBudgetFooterTextUseCase](-get-budget-footer-text-use-case/index.html) | [androidJvm]<br>class [GetBudgetFooterTextUseCase](-get-budget-footer-text-use-case/index.html)(formatter: [BudgetFooterTextFormatter](-budget-footer-text-formatter/index.html), currentSelectedPeriod: Budget.Period, budget: Budget.Specification, budgetPeriodList: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;BudgetPeriod&gt;, showOverallStatusMessage: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), now: [LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html)) |
| [GetBudgetFooterTextUseCaseTest](-get-budget-footer-text-use-case-test/index.html) | [androidJvm]<br>class [GetBudgetFooterTextUseCaseTest](-get-budget-footer-text-use-case-test/index.html) |
| [GetBudgetHeaderTextUseCase](-get-budget-header-text-use-case/index.html) | [androidJvm]<br>class [GetBudgetHeaderTextUseCase](-get-budget-header-text-use-case/index.html)(formatter: [BudgetHeaderTextFormatter](-budget-header-text-formatter/index.html), successData: [BudgetSelectionData](../com.tink.moneymanagerui.budgets.details.model/-budget-selection-data/index.html), now: [LocalDateTime](https://developer.android.com/reference/kotlin/java/time/LocalDateTime.html)) |
| [GetBudgetHeaderTextUseCaseTest](-get-budget-header-text-use-case-test/index.html) | [androidJvm]<br>class [GetBudgetHeaderTextUseCaseTest](-get-budget-header-text-use-case-test/index.html) |

