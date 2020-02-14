package com.tink.pfmsdk.insights.di

import com.tink.pfmsdk.insights.enrichment.BudgetStateEnricher
import com.tink.pfmsdk.insights.enrichment.BudgetSummaryEnricher
import com.tink.pfmsdk.insights.enrichment.CategoryTreeEnricher
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import com.tink.pfmsdk.insights.enrichment.EnrichmentType
import com.tink.pfmsdk.insights.enrichment.InsightsEnricher
import com.tink.pfmsdk.insights.enrichment.TransactionEnricher

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
