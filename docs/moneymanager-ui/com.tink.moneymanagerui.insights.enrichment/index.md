---
title: com.tink.moneymanagerui.insights.enrichment
---
//[moneymanager-ui](../../index.html)/[com.tink.moneymanagerui.insights.enrichment](index.html)



# Package com.tink.moneymanagerui.insights.enrichment



## Types


| Name | Summary |
|---|---|
| [BudgetCreateSuggestionViewDetails](-budget-create-suggestion-view-details/index.html) | [androidJvm]<br>class [BudgetCreateSuggestionViewDetails](-budget-create-suggestion-view-details/index.html)(val savePercentage: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val savePerYearAmount: [Amount](../com.tink.model.misc/-amount/index.html)) : [Insight.ViewDetails](../com.tink.model.insights/-insight/-view-details/index.html) |
| [BudgetState](-budget-state/index.html) | [androidJvm]<br>enum [BudgetState](-budget-state/index.html) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[BudgetState](-budget-state/index.html)&gt; , [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |
| [BudgetSummaryDetailItem](-budget-summary-detail-item/index.html) | [androidJvm]<br>class [BudgetSummaryDetailItem](-budget-summary-detail-item/index.html)(val budgetState: [BudgetState](-budget-state/index.html), val iconTypeViewDetails: [IconTypeViewDetails](-icon-type-view-details/index.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |
| [BudgetSummaryViewDetails](-budget-summary-view-details/index.html) | [androidJvm]<br>class [BudgetSummaryViewDetails](-budget-summary-view-details/index.html)(val items: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[BudgetSummaryDetailItem](-budget-summary-detail-item/index.html)&gt;, val targetAmount: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val spentAmount: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val progress: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)) : [Insight.ViewDetails](../com.tink.model.insights/-insight/-view-details/index.html) |
| [CategoryTreeEnricher](-category-tree-enricher/index.html) | [androidJvm]<br>class [CategoryTreeEnricher](-category-tree-enricher/index.html)@Injectconstructor(categoryRepository: [CategoryRepository](../se.tink.android.categories/-category-repository/index.html)) : InsightsEnricher |
| [CategoryTreeViewDetails](-category-tree-view-details/index.html) | [androidJvm]<br>data class [CategoryTreeViewDetails](-category-tree-view-details/index.html)(val categories: [CategoryTree](../com.tink.model.category/-category-tree/index.html)) : [Insight.ViewDetails](../com.tink.model.insights/-insight/-view-details/index.html) |
| [IconTypeViewDetails](-icon-type-view-details/index.html) | [androidJvm]<br>sealed class [IconTypeViewDetails](-icon-type-view-details/index.html) : [Insight.ViewDetails](../com.tink.model.insights/-insight/-view-details/index.html) |
| [TransactionEnricher](-transaction-enricher/index.html) | [androidJvm]<br>class [TransactionEnricher](-transaction-enricher/index.html)@Injectconstructor(transactionRepository: [TransactionRepository](../se.tink.android.repository.transaction/-transaction-repository/index.html)) : InsightsEnricher |

