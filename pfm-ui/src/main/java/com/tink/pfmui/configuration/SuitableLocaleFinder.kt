package com.tink.pfmui.configuration

import android.content.Context
import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import com.tink.pfmui.buildConfig.BuildConfigurations.instance
import se.tink.android.di.application.ApplicationScoped
import java.util.ArrayList
import java.util.Locale
import javax.inject.Inject

internal class SuitableLocaleFinder @Inject constructor(
    @ApplicationScoped private val context: Context
) {

    private val resources: Resources = context.resources

    private val localesSupportedByApp: List<Locale>
        get() {
            val locales: MutableList<Locale> =
                ArrayList()
            for (languageTag in instance.supportedLangugaes) {
                val dashedLanguageTag =
                    convertLanguageTagToDashedLanguageTag(languageTag)
                locales.add(Locale.forLanguageTag(dashedLanguageTag))
            }
            return locales
        }

    private val localeOnDevice: Locale
        get() = ConfigurationCompat.getLocales(resources.configuration)[0]

    fun findLocaleString(): String = getLocaleAsUnderscoredLanguageTag(findLocale())

    fun findLocale(): Locale {
        val localesSupportedByApp = localesSupportedByApp
        val localesOnDevice = localeOnDevice
        return findMostSuitableLocale(localesSupportedByApp, localesOnDevice)
    }

    private fun convertLanguageTagToDashedLanguageTag(languageTag: String): String =
        languageTag.replace("_", "-")

    private fun findMostSuitableLocale(
        localesSupportedByApp: List<Locale>,
        localeOnDevice: Locale
    ): Locale {
        val localeWithSupportedLanguage = findLocaleWithLanguage(
            localesSupportedByApp,
            localeOnDevice.language
        )
        return localeWithSupportedLanguage ?: localesSupportedByApp[0]
    }

    private fun findLocaleWithLanguage(locales: List<Locale>, language: String): Locale? {
        for (locale in locales) {
            if (locale.language.equals(language, ignoreCase = true)) {
                return locale
            }
        }
        return null
    }

    private fun getLocaleAsUnderscoredLanguageTag(locale: Locale): String =
        locale.toLanguageTag().replace("-", "_")
}