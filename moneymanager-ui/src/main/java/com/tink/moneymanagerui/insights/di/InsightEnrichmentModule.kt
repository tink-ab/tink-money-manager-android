package com.tink.moneymanagerui.insights.di

import com.tink.moneymanagerui.insights.enrichment.*
import com.tink.moneymanagerui.insights.enrichment.BudgetCreateSuggestionEnricher
import com.tink.moneymanagerui.insights.enrichment.BudgetStateEnricher
import com.tink.moneymanagerui.insights.enrichment.BudgetSummaryEnricher
import com.tink.moneymanagerui.insights.enrichment.EnrichmentType
import com.tink.moneymanagerui.insights.enrichment.InsightsEnricher
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class InsightEnrichmentModule {

    @Binds
    @IntoMap
    @EnrichmentTypeKey(EnrichmentType.BUDGET_STATE)
    internal abstract fun budgetStateEnricher(budgetStateEnricher: BudgetStateEnricher): InsightsEnricher

    @Binds
    @IntoMap
    @EnrichmentTypeKey(EnrichmentType.BUDGET_SUMMARY)
    internal abstract fun budgetSummaryEnricher(budgetSummaryEnricher: BudgetSummaryEnricher): InsightsEnricher

    @Binds
    @IntoMap
    @EnrichmentTypeKey(EnrichmentType.BUDGET_CREATE_SUGGESTION)
    internal abstract fun budgetCreateSuggestionEnricher(budgetCreateSuggestionEnricher: BudgetCreateSuggestionEnricher): InsightsEnricher

    @Binds
    @IntoMap
    @EnrichmentTypeKey(EnrichmentType.TRANSACTION)
    internal abstract fun transactionEnricher(transactionEnricher: TransactionEnricher): InsightsEnricher

    @Binds
    @IntoMap
    @EnrichmentTypeKey(EnrichmentType.CATEGORY_TREE)
    internal abstract fun categoryTreeEnricher(categoryTreeEnricher: CategoryTreeEnricher): InsightsEnricher
}

@MapKey
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER)
internal annotation class EnrichmentTypeKey(val value: EnrichmentType)
