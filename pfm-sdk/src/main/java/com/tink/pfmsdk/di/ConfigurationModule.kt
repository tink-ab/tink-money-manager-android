package com.tink.pfmsdk.di

import android.content.Context
import com.tink.pfmsdk.ClientDataStorage
import com.tink.pfmsdk.Timezone
import com.tink.pfmsdk.TimezoneManager
import com.tink.pfmsdk.configuration.SuitableLocaleFinder
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
    fun provideLocale(localeFinder: SuitableLocaleFinder): Locale {
        return localeFinder.findLocale()
    }

    @Provides
    fun provideTimezone(): Timezone = TimezoneManager.defaultTimezone

    @Provides
    fun provideDateUtils(locale: Locale, timezone: Timezone): DateUtils =
        DateUtils.getInstance(locale, timezone)
}
