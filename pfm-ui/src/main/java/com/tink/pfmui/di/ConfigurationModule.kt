package com.tink.pfmui.di

import android.content.Context
import com.tink.pfmui.ClientDataStorage
import com.tink.pfmui.Timezone
import com.tink.pfmui.TimezoneManager
import com.tink.pfmui.configuration.SuitableLocaleFinder
import dagger.Module
import dagger.Provides
import se.tink.android.di.application.ApplicationScoped
import se.tink.utils.DateUtils
import java.util.Locale
import javax.inject.Singleton

@Module
internal class ConfigurationModule {

    @Provides
    @Singleton
    fun provideClientStorage(@ApplicationScoped context: Context): ClientDataStorage =
        ClientDataStorage.sharedInstance(context)

    @Provides
    @Singleton
    fun provideSuitableLocaleFinder(@FragmentScoped context: Context): SuitableLocaleFinder =
        SuitableLocaleFinder(context)

    @Provides
    fun provideLocale(localeFinder: SuitableLocaleFinder): Locale {
        return localeFinder.findLocale()
    }

    @Provides
    fun provideTimezone(): Timezone = TimezoneManager.defaultTimezone

    @Provides
    fun provideDateUtils(locale: Locale, timezone: Timezone): DateUtils =
        DateUtils.getInstance(locale, timezone)
}
