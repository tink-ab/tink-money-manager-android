package com.tink.moneymanagerui.insights.di

import com.tink.moneymanagerui.insights.viewproviders.BudgetCreateSuggestionViewProvider
import com.tink.moneymanagerui.insights.viewproviders.BudgetMonthlySummaryViewProvider
import com.tink.moneymanagerui.insights.viewproviders.BudgetStateViewProvider
import com.tink.moneymanagerui.insights.viewproviders.IconTextViewProvider
import com.tink.moneymanagerui.insights.viewproviders.ImageTextViewProvider
import com.tink.moneymanagerui.insights.viewproviders.InsightViewProvider
import com.tink.moneymanagerui.insights.viewproviders.TransactionsSummaryViewProvider
import com.tink.moneymanagerui.insights.viewproviders.ExpensesByCategoryViewProvider
import com.tink.moneymanagerui.insights.viewproviders.SingleExpenseUncategorizedViewProvider
import com.tink.moneymanagerui.insights.viewproviders.WeeklyExpensesByDayViewProvider
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
    abstract fun bindTransactionsSummaryViewProvider(viewProvider: TransactionsSummaryViewProvider): InsightViewProvider

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
