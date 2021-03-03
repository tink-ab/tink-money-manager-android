package com.tink.moneymanagerui.di

import android.content.Context
import com.tink.moneymanagerui.theme.TinkDefaultSnackbarTheme
import com.tink.moneymanagerui.theme.TinkErrorSnackbarTheme
import com.tink.moneymanagerui.view.TinkSnackbar
import dagger.Module
import dagger.Provides
import se.tink.android.di.application.ApplicationScoped
import javax.inject.Named

@Module
internal class ThemingModule {

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
