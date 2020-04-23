package com.tink.pfmui.insights.di

import com.tink.pfmui.insights.viewproviders.BudgetCreateSuggestionViewProvider
import com.tink.pfmui.insights.viewproviders.BudgetMonthlySummaryViewProvider
import com.tink.pfmui.insights.viewproviders.BudgetStateViewProvider
import com.tink.pfmui.insights.viewproviders.IconTextViewProvider
import com.tink.pfmui.insights.viewproviders.ImageTextViewProvider
import com.tink.pfmui.insights.viewproviders.InsightViewProvider
import com.tink.pfmui.insights.viewproviders.SingleExpenseUncategorizedViewProvider
import com.tink.pfmui.insights.viewproviders.ExpensesByCategoryViewProvider
import com.tink.pfmui.insights.viewproviders.WeeklyExpensesByDayViewProvider
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
    abstract fun bindWeeklyExpensesByDayViewProvider(viewProvider: WeeklyExpensesByDayViewProvider): InsightViewProvider

    @Binds
    @IntoSet
    abstract fun bindImageTextViewProvider(viewProvider: ImageTextViewProvider): InsightViewProvider
}
