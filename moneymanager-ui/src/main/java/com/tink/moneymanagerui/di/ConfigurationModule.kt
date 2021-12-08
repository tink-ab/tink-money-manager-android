package com.tink.moneymanagerui.di

import android.content.Context
import com.tink.annotations.PfmScope
import com.tink.moneymanagerui.configuration.SuitableLocaleFinder
import dagger.Module
import dagger.Provides
import se.tink.utils.DateUtils
import java.util.Locale
import java.util.TimeZone

@Module
internal class ConfigurationModule {

    @Provides
    @PfmScope
    fun provideSuitableLocaleFinder(@FragmentScoped context: Context): SuitableLocaleFinder =
        SuitableLocaleFinder(context)

    @Provides
    fun provideLocale(localeFinder: SuitableLocaleFinder): Locale {
        return localeFinder.findLocale()
    }

    @Provides
    fun provideTimezone(): String = TimeZone.getDefault().id

    @Provides
    fun provideDateUtils(locale: Locale, timezone: String): DateUtils =
        DateUtils.getInstance(locale, timezone)
}
