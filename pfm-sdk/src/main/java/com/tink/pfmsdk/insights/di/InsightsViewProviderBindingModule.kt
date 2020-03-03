package com.tink.pfmsdk.insights.di

import com.tink.pfmsdk.insights.viewproviders.BudgetCreateSuggestionViewProvider
import com.tink.pfmsdk.insights.viewproviders.BudgetMonthlySummaryViewProvider
import com.tink.pfmsdk.insights.viewproviders.BudgetStateViewProvider
import com.tink.pfmsdk.insights.viewproviders.IconTextViewProvider
import com.tink.pfmsdk.insights.viewproviders.ImageTextViewProvider
import com.tink.pfmsdk.insights.viewproviders.InsightViewProvider
import com.tink.pfmsdk.insights.viewproviders.SingleExpenseUncategorizedViewProvider
import com.tink.pfmsdk.insights.viewproviders.ExpensesByCategoryViewProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet

@Module
abstract class InsightsViewProviderBindingModule {

    @Binds
    @IntoSet
    abstract fun bindIconTextViewProvider(viewProvider: IconTextViewProvider): InsightViewProvider

    @Binds
    @IntoSet
    abstract fun bindBudgetStateViewProvider(viewProvider: BudgetStateViewProvider): InsightViewProvider

    @Binds
    @IntoSet
    abstract fun bindBudgetMonthlySummaryViewProvider(viewProvider: BudgetMonthlySummaryViewProvider): InsightViewProvider

    @Binds
    @IntoSet
    abstract fun bindBudgetCreateSuggestionViewProvider(viewProvider: BudgetCreateSuggestionViewProvider): InsightViewProvider

    @Binds
    @IntoSet
    abstract fun bindSingleExpenseUncategorizedViewProvider(viewProvider: SingleExpenseUncategorizedViewProvider): InsightViewProvider

    @Binds
    @IntoSet
    abstract fun bindExpensesByCategoryViewProvider(viewProvider: ExpensesByCategoryViewProvider): InsightViewProvider

    @Binds
    @IntoSet
    abstract fun bindImageTextViewProvider(viewProvider: ImageTextViewProvider): InsightViewProvider
}
