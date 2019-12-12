package com.tink.pfmsdk.di

import android.content.Context
import com.tink.pfmsdk.overview.charts.TabExpensesBarChartFragment
import com.tink.pfmsdk.overview.charts.TabIncomeBarChartFragment
import com.tink.pfmsdk.overview.charts.TabLeftToSpendFragment
import com.tink.pfmsdk.theme.TinkDefaultSnackbarTheme
import com.tink.pfmsdk.theme.TinkErrorSnackbarTheme
import com.tink.pfmsdk.theme.TinkExpenseBarChartTabPageTheme
import com.tink.pfmsdk.theme.TinkIncomeBarChartTabPageTheme
import com.tink.pfmsdk.theme.TinkLeftToSpendTabPageTheme
import com.tink.pfmsdk.theme.TinkTransactionSimilarTheme
import com.tink.pfmsdk.theme.TinkTransactionsListTheme
import com.tink.pfmsdk.transaction.SimilarTransactionsFragment
import com.tink.pfmsdk.transaction.TransactionsListFragment
import com.tink.pfmsdk.view.TinkSnackbar
import dagger.Module
import dagger.Provides
import se.tink.android.di.application.ApplicationScoped
import javax.inject.Named

@Module
class ThemingModule {

    @Provides
    fun provideTabExpensesBarChartTheme(@ApplicationScoped context: Context): TabExpensesBarChartFragment.Theme {
        return TinkExpenseBarChartTabPageTheme(context)
    }

    @Provides
    fun provideTabLeftToSpendPageTheme(@ApplicationScoped context: Context): TabLeftToSpendFragment.Theme {
        return TinkLeftToSpendTabPageTheme(context)
    }

    @Provides
    fun provideTabeIncomeBarChartPageTheme(@ApplicationScoped context: Context): TabIncomeBarChartFragment.Theme {
        return TinkIncomeBarChartTabPageTheme(context)
    }

    @Provides
    fun provideSimilarTransactionsTheme(@ApplicationScoped context: Context): SimilarTransactionsFragment.Theme {
        return TinkTransactionSimilarTheme(context)
    }

    @Provides
    fun provideTransactionsListTheme(@ApplicationScoped context: Context): TransactionsListFragment.Theme {
        return TinkTransactionsListTheme(context)
    }

    @Provides
    @Named(TinkSnackbar.Theme.MESSAGE_THEME)
    fun messageSnackbarTheme(@ApplicationScoped context: Context): TinkSnackbar.Theme {
        return TinkDefaultSnackbarTheme(context)
    }

    @Provides
    @Named(TinkSnackbar.Theme.ERROR_THEME)
    fun errorSnackbarTheme(@ApplicationScoped context: Context): TinkSnackbar.Theme {
        return TinkErrorSnackbarTheme(context)
    }
}
